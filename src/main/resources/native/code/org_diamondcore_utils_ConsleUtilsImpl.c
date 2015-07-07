/* DO NOT EDIT THIS FILE */
#include <jni.h>
#include <windows.h>
#include "org_diamondcore_utils_ConsoleUtils.h"
/* Implementation class org_diamondcore_utils_ConsoleUtils */

JNIEXPORT void JNICALL
	Java_org_diamondcore_utils_ConsoleUtils_internSetConsoleTitle
		(JNIEnv *env, jclass c, jstring r) {  
			const char *consoleTitle = (*env)->GetStringUTFChars(env, r, 0);
			(*env)->ReleaseStringUTFChars(env, r, consoleTitle);
			SetConsoleTitle(consoleTitle);
		}

