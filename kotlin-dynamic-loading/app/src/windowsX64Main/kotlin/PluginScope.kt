import kotlinx.cinterop.*
import platform.windows.*
import plugin.anonymousStruct3
import plugin.plugin_ExportedSymbols

actual class PluginScope(
	handle: HMODULE
) {
	/**
	 * Name of symbol in plugin_api.h
	 * [plugin_ExportedSymbols]* plugin_symbols(void);
	 */
	val funcName = "plugin_symbols"

	/**
	 * struct in plugin_api.h
	 */
	private val plugin: anonymousStruct3
	init {
		/**
		 * Get the struct [plugin] symbol with handle and funcName.
		 */
		val symbol = GetProcAddress(handle, funcName)
			?: throw IllegalStateException("Failed resolve $funcName")

		plugin = symbol.reinterpret<CFunction<()->plugin_ExportedSymbols>>()
			.invoke()			// call plugin_symbols() to get [plugin_ExportedSymbols]
			.kotlin.root.plugin	// plugin_symbols()->kotlin.root.plugin
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
	 * Open plugin file.
	 */
	val handle: HMODULE = memScoped {
		/**
		 * Get the file path as [LPCWSTR] using memory
		 * ( = CPointer<WCHARVar> )
		 * ( = CPointer<UShortVarOf<UShort>> )
		 */
		val name: LPCWSTR = path.wcstr.ptr
		LoadLibrary!!.invoke(name)	// function [LoadLibrary] is nullable
			?: throw RuntimeException("Failed: load $path")
	}

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
		FreeLibrary(handle)
	}
}