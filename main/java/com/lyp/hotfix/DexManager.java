package com.lyp.hotfix;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class DexManager  {
    private Context context;

    public DexManager(Context context) {
        this.context = context;
    }

    public void loadDex(File dex){
        try {
            DexFile dexFile = DexFile.loadDex(dex.getAbsolutePath(),
                    new File(context.getCacheDir(),"opt").getAbsolutePath(),Context.MODE_PRIVATE);


            Enumeration<String> entries = dexFile.entries();

            while (entries.hasMoreElements()){
                //得到全类名
                String className = entries.nextElement();
                //把修复好的class加载到内存
                Class aClass = dexFile.loadClass(className, context.getClassLoader());
                fixMethod(aClass);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("--------", "loadDex: "+ e.toString());
        }
    }

    private void fixMethod(Class fixclass) {
        //反射获取类中的所有方法
        Method[] methods = fixclass.getDeclaredMethods();
        //遍历含有replace的注解方法
        for (Method method: methods){
            //如果replace不为空 就进行修复
            Replace replace = method.getAnnotation(Replace.class);
            System.out.println(method.toString()+"PPPPPPPPP");
            if (replace== null){
                continue;
            }else{


                //重点来了
                try {
                    //需要找到需要修复的地方
                    String WrongClassName = replace.clasz();
                    String WrongMethodName = replace.method();

                    //得到需要修复的方法
                    Class wrongClass = Class.forName(WrongClassName);
                    // 参2 修复好的方法参数列表
                    Method wrongmethod = wrongClass.getMethod(WrongMethodName,method.getParameterTypes());

                    //结合jni做方法替换
                    fixNativeMethod(wrongmethod,method);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }

        }
    }



    static {
        System.loadLibrary("native-lib");

    }
    private static native void fixNativeMethod(Method wrongmethod, Method method);


}
