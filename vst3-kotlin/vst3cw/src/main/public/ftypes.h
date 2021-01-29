#pragma once

#include <pluginterfaces/base/fplatform.h>
#include "bool.h"

#ifdef __cplusplus
extern "C" {
#endif


typedef char int8;
typedef unsigned char uint8;
typedef unsigned char uchar;

typedef short int16;
typedef unsigned short uint16;

#if SMTG_OS_WINDOWS && !defined(__GNUC__)
	typedef long int32;
	typedef unsigned long uint32;
#else
	typedef int int32;
	typedef unsigned int uint32;
#endif

#if SMTG_OS_WINDOWS && !defined(__GNUC__)
	typedef __int64 int64;
	typedef unsigned __int64 uint64;
#else
	typedef long long int64;
	typedef unsigned long long uint64;
#endif

typedef int64 TSize;
typedef int32 tresult;

#if SMTG_PLATFORM_64
	typedef uint64 TPtrInt;
#else
	typedef uint32 TPtrInt;
#endif

typedef uint8 TBool;

typedef char char8;
#ifdef _NATIVE_WCHAR_T_DEFINED
	typedef __wchar_t char16;
#elif defined(__MINGW32__)
	typedef wchar_t char16;
#elif SMTG_CPP11
	typedef char16_t char16;
#else
	typedef int16 char16;
#endif

#ifdef UNICODE
	typedef char16 tchar;
#else
	typedef char8 tchar;
#endif

typedef const char8* CStringA;
typedef const char16* CStringW;
typedef const tchar* CString;
typedef const char8* FIDString;

typedef int32 UCoord;


#ifdef __cplusplus
}
#endif
