#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_yuan_waterjni_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" {
extern int mydiff(int argc, char *argv[]);
extern int mypatch(int argc, char *argv[]);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_yuan_waterjni_MainActivity_diff(JNIEnv *env, jobject instance, jstring oldpath_,
                                         jstring newpath_, jstring patch_) {
    const char *oldpath = env->GetStringUTFChars(oldpath_, 0);
    const char *newpath = env->GetStringUTFChars(newpath_, 0);
    const char *patch = env->GetStringUTFChars(patch_, 0);
    // TODO
    char *argv[] = {
            "bsdiff", const_cast<char *>(oldpath), const_cast<char *>(newpath),
            const_cast<char *>(patch)
    };
    //该函数用于生成差分包
    mydiff(4, argv);

    env->ReleaseStringUTFChars(oldpath_, oldpath);
    env->ReleaseStringUTFChars(newpath_, newpath);
    env->ReleaseStringUTFChars(patch_, patch);

//    const char *argv[4];
//    argv[0] = "bsdiff";
//    argv[1] = (env)->GetStringUTFChars(oldpath_, 0);
//    argv[2] = (env)->GetStringUTFChars(newpath_, 0);
//    argv[3] = (env)->GetStringUTFChars(patch_, 0);
//    //该函数用于生成差分包
//    mydiff(4, const_cast<char **>(argv));
//    (env)->ReleaseStringUTFChars(oldpath_, argv[1]);
//    (env)->ReleaseStringUTFChars(newpath_, argv[2]);
//    (env)->ReleaseStringUTFChars(patch_, argv[3]);
//    free((void *) argv[0]);
//    free(argv);
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_yuan_waterjni_MainActivity_patch(JNIEnv *env, jobject instance, jstring oldpath_,
                                          jstring newpath_, jstring patch_) {
    const char *oldpath = env->GetStringUTFChars(oldpath_, 0);
    const char *newpath = env->GetStringUTFChars(newpath_, 0);
    const char *patch = env->GetStringUTFChars(patch_, 0);
    // TODO
    char *argv[] = {
            "bsdiff", const_cast<char *>(oldpath), const_cast<char *>(newpath),
            const_cast<char *>(patch)
    };
    //该函数用于合并差分包
    mypatch(4, argv);

    env->ReleaseStringUTFChars(oldpath_, oldpath);
    env->ReleaseStringUTFChars(newpath_, newpath);
    env->ReleaseStringUTFChars(patch_, patch);

//    const char *argv[4];
//    argv[0] = "bspatch";
//    argv[1] = env->GetStringUTFChars(oldpath_, 0);
//    argv[2] = env->GetStringUTFChars(newpath_, 0);
//    argv[3] = env->GetStringUTFChars(patch_, 0);
//    //该函数用于合并差分包
//    mypatch(4, const_cast<char **>(argv));
//    env->ReleaseStringUTFChars(oldpath_, argv[1]);
//    env->ReleaseStringUTFChars(newpath_, argv[2]);
//    env->ReleaseStringUTFChars(patch_, argv[3]);
//    free(argv);
    return 0;
}