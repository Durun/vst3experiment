import kotlinx.cinterop.*
import platform.posix.*

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
		symbol.takeIf { error == null }?.reinterpret<CFunction<()->Int>>()
			?: throw IllegalStateException("$error").also {
				println("(loader) Failed: resolve $funcName: $error")
			}
	}

	val v = func()
	println("(loader) Success: call $funcName(): return value = $v")

	dlclose(handle)
	println("(loader) Success: Closed handle $file")
}