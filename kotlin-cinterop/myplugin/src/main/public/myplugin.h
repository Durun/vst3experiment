
#ifndef MYPLUGIN_H
#define MYPLUGIN_H

#ifdef _WIN32
#define MYPLUGIN_EXPORT_FUNC __declspec(dllexport)
#else
#define MYPLUGIN_EXPORT_FUNC
#endif

#ifdef __cplusplus
extern "C" {
#endif

int func1();
int func2();

#ifdef __cplusplus
}
#endif

#endif
