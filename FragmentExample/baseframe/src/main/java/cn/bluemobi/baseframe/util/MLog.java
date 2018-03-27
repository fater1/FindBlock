package cn.bluemobi.baseframe.util;

import android.util.Log;

import cn.bluemobi.baseframe.base.BaseApplication;


public class MLog {
    private static final String TAG = "ESPORT";

    public static void v(String msg){
        v(TAG, msg);
    }
    public static void v(String tag, String msg){
        if(BaseApplication.DEBUG && msg != null){
            Log.v(tag, msg);
        }
    }
    public static void i(String msg){
        i(TAG, msg);
    }
    public static void i(String tag, String msg){
        if(BaseApplication.DEBUG && msg != null){
            Log.i(tag, msg);
        }
    }
    public static void d(String msg){
        d(TAG, msg);
    }
    public static void d(String tag, String msg){
        if(BaseApplication.DEBUG && msg != null){
            Log.d(tag, msg);
        }
    }
    public static void w(String msg){
        w(TAG, msg);
    }
    public static void w(String tag, String msg){
        if(BaseApplication.DEBUG && msg != null){
            Log.w(tag, msg);
        }
    }
    public static void e(String msg){
        e(TAG, msg);
    }
    public static void e(String tag, String msg){
        if(BaseApplication.DEBUG && msg != null){
            Log.e(tag, msg);
        }
    }
}
