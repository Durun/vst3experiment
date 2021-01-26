import platform.posix.printf

internal actual fun println(message: Any?) {
	printf("$message\n")
}
