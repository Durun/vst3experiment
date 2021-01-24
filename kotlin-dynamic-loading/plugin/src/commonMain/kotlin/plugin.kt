package plugin

fun func1(arg: Int): Int {
	println("(plugin) Hello, I'm plugin.")
	return 0
}

fun func2(arg: Int): Int {
	println("(plugin) Hi, I will return 2.")
	return 2
}