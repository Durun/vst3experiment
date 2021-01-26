interface Path {
	fun resolve(other: String): Path
	override fun toString(): String
	val isAbsolute: Boolean
	companion object {
		fun of(path: String): Path = when(DEFAULT_FILESYSTEM) {
			FileSystem.Unix -> UnixPath.of(path)
			FileSystem.Windows -> WindowsPath.of(path)
		}
	}
}

expect val DEFAULT_FILESYSTEM: FileSystem

enum class FileSystem {
	Unix, Windows
}

class UnixPath
private constructor(
	override val isAbsolute: Boolean,
	private val elements: List<String>
) : Path {
	companion object {
		val fileSystem: FileSystem = FileSystem.Unix
		fun of(path: String): UnixPath {
			return UnixPath(
				isAbsolute = path.startsWith("/"),
				elements = path.splitToElements()
			)
		}

		private fun String.splitToElements() = this.split("/").filterNot { it.isEmpty() }
	}

	override fun resolve(other: String): Path {
		return of("$this/$other")
	}

	override fun toString(): String {
		return (if (isAbsolute) "/" else "") + elements.joinToString("/")
	}
}

class WindowsPath
private constructor(
	private val drive: Char?,
	private val elements: List<String>
) : Path {
	companion object {
		val fileSystem: FileSystem = FileSystem.Windows
		fun of(path: String): WindowsPath {
			val prefix = path.substring(0..2).toUpperCase().let {
				it.takeIf { it.matches(rootPattern) }
			}
			val body = prefix?.let {
				path.drop(it.length)
			} ?: path
			return WindowsPath(
				drive = prefix?.first(),
				elements = body.splitToElements()
			)
		}

		private val rootPattern = Regex("[A-Z]:\\\\")
		private fun String.splitToElements() = this.split("\\").filterNot { it.isEmpty() }
	}

	override val isAbsolute: Boolean
		get() = drive != null

	override fun resolve(other: String): Path {
		return of("$this\\$other")
	}

	override fun toString(): String {
		val prefix = drive?.let { "$it:\\" }.orEmpty()
		return prefix + elements.joinToString("\\")
	}
}