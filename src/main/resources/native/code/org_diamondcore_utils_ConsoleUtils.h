#include <jni.h>

#ifndef _Included_org_diamondcore_utils_ConsoleUtils
#define _Included_org_diamondcore_utils_ConsoleUtils
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_diamondcore_utils_ConsoleUtils
 * Method:    internSetConsoleTitle
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_diamondcore_utils_ConsoleUtils_internSetConsoleTitle
  (JNIEnv *, jclass, jstring);

#ifdef __cplusplus
}
#endif
#endif
