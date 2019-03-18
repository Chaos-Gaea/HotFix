#include <jni.h>
#include "string.h"
#include "art_method.h"

extern "C"
{
JNIEXPORT void JNICALL
Java_com_lyp_hotfix_DexManager_fixNativeMethod(
        JNIEnv* env,
        jclass type,
        jobject wrongMethod,
        jobject rightMethod) {

   //C反射 获得artMethod 结构体
   art::mirror::ArtMethod* wrong = (art::mirror::ArtMethod *) env->FromReflectedMethod(wrongMethod);
   art::mirror::ArtMethod* right = (art::mirror::ArtMethod *) env->FromReflectedMethod(rightMethod);
   //正确指针赋值给错误指针
   wrong->declaring_class_=right->declaring_class_;
   //方法的偏移量
   wrong->dex_code_item_offset_=right->dex_code_item_offset_;
   //方法表里的索引
   wrong->method_index_=right->method_index_;
   //在dex中方法的索引
   wrong->dex_method_index_= right->dex_method_index_;
}
}
