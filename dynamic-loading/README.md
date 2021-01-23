# Dynamic loading demo on Linux

## Requirement
- C compiler such as clang, gcc

## Demo
- call `func1` in plugin.c from loader.c
	```shell
	CC=clang make run1
	```
	expected:
	```
	./loader ./libplugin.so func1
	(loader) Success: open ./libplugin.so
	(plugin) Hello, I'm plugin.
	(loader) Success: call func1(): return value = 0
	(loader) Success: Closed handle ./libplugin.so
	```

- call `func2` in plugin.c from loader.c
	```shell
	CC=clang make run2
	```
	expected:
	```
	./loader ./libplugin.so func2
	(loader) Success: open ./libplugin.so
	(plugin) Hi, I will return 2.
	(loader) Success: call func2(): return value = 2
	(loader) Success: Closed handle ./libplugin.so
	```

## References(JP)
- [dlopen - ライブラリコールの説明 - Linux コマンド集 一覧表](https://kazmax.zpp.jp/cmd/d/dlopen.3.html)
- [Linux の共有ライブラリを作るとき PIC でコンパイルするのはなぜか - bkブログ](http://0xcc.net/blog/archives/000107.html)
