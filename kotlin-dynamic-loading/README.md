# Kotlin dynamic loading demo

## Requirement
- x86_64 architecture
- Linux or MacOS or Windows

## Demo
- on Linux
    ```shell
    ./gradlew :app:runReleaseExecutableLinuxX64
    ```
  or
    ```shell
    app/build/bin/linuxX64/releaseExecutable/app.kexe plugin/build/bin/linuxX64/releaseShared/libplugin.so
    ```
  <details>
  <summary>expect:</summary>
    <pre><code>
    Hello, world!
    (loader) Success: open plugin/build/bin/linuxX64/releaseShared/libplugin.so
    (plugin) Hello, I'm plugin.
    (loader) Success: call func1: return value = 0
    (plugin) Hi, I will return 2.
    (loader) Success: call func2: return value = 2
    (loader) Success: Closed handle plugin/build/bin/linuxX64/releaseShared/libplugin.so
    </code></pre>
  </details>
- on MacOS
    ```shell
    ./gradlew :app:runReleaseExecutableMacosX64
    ```
  or
    ```shell
    app/build/bin/macosX64/releaseExecutable/app.kexe plugin/build/bin/macosX64/releaseShared/libplugin.dylib
    ```
  <details>
  <summary>expect:</summary>
    <pre><code>
    ```
    Hello, world!
    (loader) Success: open plugin/build/bin/macosX64/releaseShared/libplugin.dylib
    (plugin) Hello, I'm plugin.
    (loader) Success: call func1: return value = 0
    (plugin) Hi, I will return 2.
    (loader) Success: call func2: return value = 2
    (loader) Success: Closed handle plugin/build/bin/macosX64/releaseShared/libplugin.dylib
    </code></pre>
  </details>
- on Windows
    ```shell
    gradlew :app:runReleaseExecutableWindowsX64
    ```
  or
    ```shell
    app/build/bin/windowsX64/releaseExecutable/app.kexe plugin/build/bin/windowsX64/releaseShared/plugin.dll
    ```
  <details>
  <summary>expect:</summary>
    <pre><code>
    Hello, world!
    (loader) Success: open plugin\build\bin\windowsX64\releaseShared\plugin.dll
    (plugin) Hello, I'm plugin.
    (loader) Success: call func1: return value = 0
    (plugin) Hi, I will return 2.
    (loader) Success: call func2: return value = 2
    (loader) Success: Closed handle plugin\build\bin\windowsX64\releaseShared\plugin.dll
    </code></pre>
  </details>

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
  
## References
- [Kotlin Native plugin loading mechanism - Native - Kotlin Discussions](https://discuss.kotlinlang.org/t/kotlin-native-plugin-loading-mechanism/15645)
- [Kotlin/Native as a Dynamic Library - Kotlin Programming Language](https://kotlinlang.org/docs/tutorials/native/dynamic-libraries.html)
### C Interop
- [CFunction - Kotlin Programming Language](https://kotlinlang.org/api/latest/jvm/stdlib/kotlinx.cinterop/-c-function/)
### Windows API
- [Using Run-Time Dynamic Linking - Win32 apps | Microsoft Docs](https://docs.microsoft.com/en-us/windows/win32/dlls/using-run-time-dynamic-linking)
- [DLLを動的リンクで呼び出す](http://yamatyuu.net/computer/program/sdk/base/dynamic_dll/index.html)