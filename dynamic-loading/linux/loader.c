#include <stdio.h>
#include <dlfcn.h>

/**
 * Type of function of [func1], [func2] in plugin.c
 */
typedef int IntSupplier(void);


int main(int argc, char *argv[]) {
	if (argc < 3) {
		printf("Usage: %s plugin func\n", argv[0]);
		return 1;
	}
	const char *plugin = argv[1];					// plugin name
	const char *funcName = argv[2];					// function name in the plugin

	/*
	 * Open [plugin] with RTLD_LAZY mode.
	 * If [plugin] contains "/", [plugin] is interpreted as filepath.
	 * see also: man dlopen(3)
	 */
	void *handle = dlopen(plugin, RTLD_LAZY);		// not const
	if (handle == NULL) {
		const char *message = dlerror();
		printf("(loader) Failed: open %s: %s\n", plugin, message);
		return 1;
	}
	printf("(loader) Success: open %s\n", plugin);

	/*
	 * Get the function symbol with plugin [handle] and [funcName].
	 * First, clean error message by calling dlerror()
	 * because dlsym() returns error message via dlerror().
	 * see also: man dlopen(3)
	 */
	dlerror();	// clean error message
	IntSupplier *func = dlsym(handle, funcName);	// not const
	{
		const char *message = dlerror();
		if (message){
			printf("(loader) Failed: get %s: %s\n", funcName, message);
		}
	}

	/**
	 * Call the function.
	 */
	const int ret = func();
	printf("(loader) Success: call %s(): return value = %d\n", funcName, ret);

	/**
	 * Close the plugin.
	 */
	dlclose(handle);
	printf("(loader) Success: Closed handle %s\n", plugin);

	return 0;
}
