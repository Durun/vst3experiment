#CC=clang	# Compilers such as clang, gcc will be OK.
PROGRAM=loader libplugin.so

.PHONY: all run clean

all: $(PROGRAM)

run1: $(PROGRAM)
	./loader ./libplugin.so func1
run2: $(PROGRAM)
	./loader ./libplugin.so func2

# dlfcn.h requires -ldl option.
loader: loader.c
	$(CC) $^ -ldl -o $@

# shared library should be compiled with -shared oprion.
# -fPIC option improvements performance on relocation.
libplugin.so: plugin.c
	$(CC) $^ -shared -fPIC -o $@

clean:
	rm -f $(PROGRAM)
