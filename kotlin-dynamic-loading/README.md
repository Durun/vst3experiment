# Kotlin dynamic loading demo

## Requirement
- x86_64 architecture
- Linux or MacOS or Windows

## Demo
- on Linux
    ```shell
    ./gradlew :app:runReleaseExecutableLinuxX64
    ```
- on MacOS
    ```shell
    ./gradlew :app:runReleaseExecutableMacosX64
    ```
- on Windows
    ```shell
    .\gradlew :app:runReleaseExecutableWindowsX64
    ```

## Project overview
- **[:app](app/build.gradle.kts)**
  builds *native executable (`app/build/bin/`)*
  from [Kotlin source](app/src),
  [library definition](app/src/nativeInterop/cinterop),
  *C header(`plugin/build/bin/OS/releaseShared/*.h`)*,
  *native library (`plugin/build/bin/OS/releaseShared/*.{so,dylib,dll}`)*
- **[:plugin](plugin/build.gradle.kts)**
  builds *native library (`plugin/build/bin/`)*
  from [Kotlin source](plugin/src)