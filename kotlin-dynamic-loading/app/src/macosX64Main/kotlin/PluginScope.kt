import kotlinx.cinterop.*
import platform.posix.*
import plugin.libplugin_ExportedSymbols

actual class PluginScope(
	handle: CPointer<*>
) {
	private val plugin = resolveSymbol(handle, "libplugin_symbols")!!
		.reinterpret<CFunction<()->libplugin_ExportedSymbols>>()
		.invoke().kotlin.root.plugin

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
	val handle = dlopen(path, RTLD_LAZY)
		?: throw RuntimeException("Failed: load $path")

	try {
		PluginScope(handle).body()
	} catch (ex: Throwable) {
		throw ex
	} finally {
		dlclose(handle)
	}
}

private fun resolveSymbol(handle: CPointer<*>, name: String): CPointer<*>? {
	dlerror()
	val symbol = dlsym(handle, name)
	dlerror()?.let {
		throw IllegalStateException("Failed resolve $name: ${it.toKString()}")
	}
	return symbol
}