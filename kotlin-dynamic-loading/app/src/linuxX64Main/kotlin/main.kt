import kotlinx.cinterop.*
import platform.posix.*
import plugin.libplugin_ExportedSymbols

fun main(args: Array<String>) {
	println("Hello, world!")
	check(args.size == 2) {
		println("Usage: ./loader plugin func")
	}
	val (file, funcName) = args

	val handle = runCatching {
		dlopen(file, RTLD_LAZY)
	}.onFailure {
		println("(loader) Failed: open $file: $it")
	}.getOrThrow()
	println("(loader) Success: open $file")

	val func = run {
		dlerror()
		val symbol = dlsym(handle, funcName)
		val error = dlerror()
		symbol.takeIf { error == null }?.reinterpret<CFunction<() -> CPointer<libplugin_ExportedSymbols>>>()
			?: throw IllegalStateException("$error").also {
				println("(loader) Failed: resolve $funcName: $error")
				dlclose(handle)
			}
	}

	//val v = func()
	//println("(loader) Success: call $funcName(): return value = $v")

	val lib = func().pointed
	println("(loader) Success: returned libplugin_ExportedSymbols: $lib")

	val func1 = lib.kotlin.root.plugin.func1
		?: throw IllegalStateException("cannot resolve func1")
	println("(loader) Success: func1 = ${func1.pointed} at $func1")
	val func2 = lib.kotlin.root.plugin.func2
		?: throw IllegalStateException("cannot resolve func2")
	println("(loader) Success: func2 = ${func2.pointed} at $func2")

	val v1 = func1(0)
	println("(loader) Success: call func1: return value = $v1")
	val v2 = func2(0)
	println("(loader) Success: call func2: return value = $v2")

	dlclose(handle)
	println("(loader) Success: Closed handle $file")
}