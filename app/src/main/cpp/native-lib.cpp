#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_bf_hzplayer_Fragment_LVideoListFragment_getBitmaps(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
