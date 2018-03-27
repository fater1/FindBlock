package cn.bluemobi.baseframe.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


public class BaseApplication extends Application {
    /**
     * 测试标识，正式打包设为false
     */
    public final static boolean DEBUG = true;

    private static Application instance;

    private static int version;
    private static String device;

    private static String channelName;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        version = 1;
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        }catch (Exception e){
        }
        try{
            TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(TELEPHONY_SERVICE);
            device = tm.getDeviceId();
        }catch (Exception e){
            device = "No_Permission_For_Device";
        }
        ApplicationInfo ai = null;
        try {
            ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Bundle bundle = ai.metaData;
        channelName = bundle.getString("CHANNEL_NAME");
        Log.i("channelName", channelName);
    }

    public static Application getInstance(){
        return instance;
    }

    public static int getVersion() {
        return version;
    }

    public static String getDevice() {
        return device;
    }

    public static int getId(String name, String type){
        return instance.getResources().getIdentifier(name, type, instance.getPackageName());
    }

    public int getMark(){
        return 0;
    }

    public static String getChannelName(){
        return channelName;
    }
}
