import kotlinx.cinterop.*
import platform.posix.*
import plugin.anonymousStruct3
import plugin.libplugin_ExportedSymbols

actual class PluginScope(
	handle: CPointer<*>
) {
	/**
	 * Name of symbol in libplugin_api.h
	 * [libplugin_ExportedSymbols]* libplugin_symbols(void);
	 */
	val funcName = "libplugin_symbols"

	/**
	 * struct in libplugin_api.h
	 */
	private val plugin: anonymousStruct3
	init {
		/**
		 * Get the struct [plugin] symbol with handle and funcName.
		 */
		dlerror() // clean error message
		val symbol = dlsym(handle, funcName)	// get the symbol [libplugin_ExportedSymbols]* libplugin_symbols(void);
		dlerror()?.let {
			throw IllegalStateException("Failed resolve $funcName: ${it.toKString()}")
		}
		checkNotNull(symbol)
		plugin = symbol.reinterpret<CFunction<()->libplugin_ExportedSymbols>>()
			.invoke()	// call libplugin_symbols() to get [libplugin_ExportedSymbols]
			.kotlin.root.plugin	// libplugin_symbols()->kotlin.root.plugin
	}

	actual fun func1(arg: Int): Int {
		return plugin.func1?.invoke(arg)
			?: throw IllegalStateException("Failed: get func1")
	}

	actual fun func2(arg: Int): Int {
		return plugin.func2?.invoke(arg)
			?: throw IllegalStateException("Failed: get func2")
	}
}

actual fun usePlugin(file: String, body: PluginScope.() -> Unit) {
	val path = Path.of(file).let {
		if (it.isAbsolute) it else Path.of(".").resolve(it.toString())
	}.toString()

	/**
	 * Open plugin file with [RTLD_LAZY] mode.
	 */
	val handle = dlopen(path, RTLD_LAZY)
		?: throw RuntimeException("Failed: load $path")

	try {
		/**
		 * Call the plugin with the handle
		 * @see PluginScope
		 */
		PluginScope(handle).body()
	} catch (ex: Throwable) {
		throw ex
	} finally {
		/**
		 * Close the plugin
		 */
		dlclose(handle)
	}
}