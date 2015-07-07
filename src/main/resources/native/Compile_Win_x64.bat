gcc -Wall -D__int64=int64_t -D_JNI_IMPLEMENTATION_ -Wl,--kill-at -D __int64=int64_t -I "%JAVA_INCLUDE%" -I "%JAVA_INCLUDE_WIN32%" -shared JNIImpl.c -o JNI.dll

pause