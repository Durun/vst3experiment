import kotlinx.cinterop.*
import platform.windows.*
import plugin.plugin_ExportedSymbols

actual class PluginScope(
	handle: HMODULE
) {
	private val plugin = resolveSymbol(handle, "plugin_symbols")!!
		.reinterpret<CFunction<()->plugin_ExportedSymbols>>()
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
	val handle: HMODULE = memScoped {
		LoadLibrary!!.invoke(path.wcstr.ptr)
			?: throw RuntimeException("Failed: load $path")
	}

	try {
		PluginScope(handle).body()
	} catch (ex: Throwable) {
		throw ex
	} finally {
		FreeLibrary(handle)
	}
}

private fun resolveSymbol(handle: HMODULE?, name: String): CPointer<*>? {
	val symbol = GetProcAddress(handle, name)
		?: throw IllegalStateException("Failed resolve $name")
	return symbol
}