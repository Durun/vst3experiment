expect class PluginScope {
	fun func1(arg: Int): Int
	fun func2(arg: Int): Int
}

expect fun usePlugin(file: String, body: PluginScope.() -> Unit)