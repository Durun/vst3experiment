import de.undercouch.gradle.tasks.download.Download
import de.undercouch.gradle.tasks.download.Verify

plugins {
	id("de.undercouch.download") version "4.1.1"
	id("net.freudasoft.gradle-cmake-plugin") version "0.0.2"
}

val sdkDir = buildDir.resolve("VST_SDK")
val vst3sdkDir = sdkDir.resolve("VST3_SDK")
val sdkBuildDir = buildDir.resolve("vst3sdk")
ext["includeDir"] = vst3sdkDir.absolutePath
ext["libraryDir"] = sdkBuildDir.resolve("lib/Release").absolutePath

cmake {
	workingFolder.set(sdkBuildDir)
	sourceFolder.set(vst3sdkDir)
	buildTarget.set("install")
	buildConfig.set("Release")
	def.put("CMAKE_BUILD_TYPE", "Release")
}

tasks {
	val downloadVst3sdk by creating(Download::class) {
		src("https://www.steinberg.net/vst3sdk")
		dest(File(buildDir, "vst3sdk.zip"))
		overwrite(false)
	}
	val verifyVst3sdk by creating(Verify::class) {
		dependsOn(downloadVst3sdk)
		src(downloadVst3sdk.dest)
		algorithm("MD5")
		checksum("3dc2a3bf97b399d4b0952c7690052bc3")
	}
	val unzipVst3sdk by creating(Copy::class) {
		dependsOn(verifyVst3sdk)
		from(zipTree(downloadVst3sdk.dest))
		into(sdkDir.parent)
	}
	getByName("cmakeConfigure").dependsOn(unzipVst3sdk)
}

val build by tasks.creating {
	dependsOn(tasks["cmakeBuild"])
}
val clean by tasks.creating {
	dependsOn(tasks["cmakeClean"])
	doLast {
		delete(buildDir)
	}
}

tasks.withType<Wrapper> {
	gradleVersion = "6.8.1"
	distributionType = Wrapper.DistributionType.ALL
}