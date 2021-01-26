fun main(args: Array<String>) {
	println("Hello, world!")
	check(args.size == 1) {
		println("Usage: loader pluginFile")
	}
	val file= args.first()

	runCatching {
		/**
		 * Call func1 and func2 in the dynamic library file.
		 * @see PluginScope
		 */
		usePlugin(file) {
			println("(loader) Success: open $file")
			val v1 = func1(0)
			println("(loader) Success: call func1: return value = $v1")
			val v2 = func2(0)
			println("(loader) Success: call func2: return value = $v2")
		}
	}.onFailure {
		println("(loader) ${it.message}")
	}.getOrThrow()

	println("(loader) Success: Closed handle $file")
}