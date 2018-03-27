package cn.bluemobi.baseframe.http;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bluemobi.baseframe.base.BaseApplication;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Http请求工具类
 */

public class HttpUtil {

    public static final int REQUEST_LOGIN = 2048;

    private static boolean DEBUG = true;

    private static Context appContext;
    private static HttpBase httpBase;
    private static Handler handler;

    private static Set<String> httpUrls;
    static {
        handler = new Handler();
        httpUrls = new HashSet<>();
    }

    public static void init(Context c, HttpBase httpBaseImpl) {
        appContext = c;
        httpBase = httpBaseImpl;
    }

    public static void post(String url, HttpCallBack callBack, String[]... params) {
        post(url, callBack, 0, params);
    }

    public static void post(final String url, final HttpCallBack callBack, final int configKey, final String[]... params) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("channel", BaseApplication.getChannelName());
        builder.add("version", String.valueOf(BaseApplication.getVersion()));
        builder.add("device", Build.MODEL);
        builder.add("client", "0");

        StringBuffer paramSb = new StringBuffer(url);
        paramSb.append("?");
            for (String[] param : params) {
                if (param != null && param.length == 2) {
                    if (param[0] != null && param[1] != null) {
                        builder.add(param[0], param[1]);

                        paramSb.append(param[0]);
                        paramSb.append("=");
                        paramSb.append(param[1]);
                        paramSb.append("&");
                    }
                }
        }
        if (DEBUG) {
            Log.i("httpPost2", httpBase.getRootUrl()+url+".json"+paramSb.toString());
        }
        final Request request = new Request.Builder()
                .url(httpBase.getRootUrl() + url + ".json")
                .post(builder.build())
                .build();
        final String urlMark = url + System.currentTimeMillis();
        checkNet(urlMark);
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                synchronized (httpUrls){
                    httpUrls.remove(urlMark);
                }
                final String errorMsg = e == null ? "null" : (e.getMessage() == null ? "" : e.getMessage());
                if (DEBUG) {
                    Log.d("httpResult", errorMsg);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(errorMsg, url, configKey);
//                        Toast.makeText(appContext, "当前网速较差，请稍后尝试", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                synchronized (httpUrls){
                    httpUrls.remove(urlMark);
                }
                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    if (DEBUG) {
                        Log.d("httpResult", json);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(json, url, configKey);
                        }
                    });
                    callBack.onSuccessBackground(json, url, configKey);
                } else {
                    if (DEBUG) {
                        Log.d("httpResult", "errorCode = " + response.code());
                        Log.d("httpResult", response.message());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(response.message(), url, configKey);
                        }
                    });
                }
            }
        });
    }


    /**
     * 带图片post
     *
     * @param url       接口路径
     * @param callBack  回调
     * @param configKey 控制参数
     * @param imageKey  图片key
     * @param picList   图片路径(list)
     * @param params    其他参数(String[]{key, value})
     */
    public static void postWithImage(final String url, final HttpCallBack callBack, final int configKey, String imageKey, List<String> picList, final String[]... params) {
        //参数类型
        MediaType MEDIA_TYPE_PNG = MediaType.parse("file");
        //创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("channel", BaseApplication.getChannelName());
        builder.addFormDataPart("version", String.valueOf(BaseApplication.getVersion()));
        builder.addFormDataPart("device", Build.MODEL);
        builder.addFormDataPart("client", "0");

        if (params != null) {
            for (String[] param : params) {
                if (param != null && param.length == 2) {
                    if (param[0] != null && param[1] != null) {
                        builder.addFormDataPart(param[0], param[1]);
                    }
                }
            }
        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        if (picList != null) {
            for (String path : picList) {
                if (!TextUtils.isEmpty(path)) {
                    builder.addFormDataPart(imageKey, path, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
                }
            }
        }

        //构建请求体
        RequestBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(httpBase.getRootUrl() + url + ".json")//地址
                .post(requestBody)//添加请求体
                .build();

        final String urlMark = url + System.currentTimeMillis();
        checkNet(urlMark);
        //发送异步请求，同步会报错，Android4.0以后禁止在主线程中进行耗时操作
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                synchronized (httpUrls){
                    httpUrls.remove(urlMark);
                }
                final String errorMsg = e == null ? "null" : (e.getMessage() == null ? "" : e.getMessage());
                if (DEBUG) {
                    Log.d("httpResult", errorMsg);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(errorMsg, url, configKey);
                        Toast.makeText(appContext, "当前网速较差，请稍后尝试", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                synchronized (httpUrls){
                    httpUrls.remove(urlMark);
                }
                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    if (DEBUG) {
                        Log.d("httpResult", json);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(json, url, configKey);
                        }
                    });
                    callBack.onSuccessBackground(json, url, configKey);
                } else {
                    if (DEBUG) {
                        Log.d("httpResult", "errorCode = " + response.code());
                        Log.d("httpResult", response.message());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(response.message(), url, configKey);
                        }
                    });
                }
            }
        });
    }

    /**
     * 修改个人信息请求接口(根目录不一样)
     *
     * @param url
     * @param callBack
     * @param configKey
     * @param imageKey
     * @param params
     */
    public static void postWithImageForUpdate(final String url, final HttpCallBack callBack, final int configKey, String imageKey, String picPath, final String[]... params) {
        //参数类型
        MediaType MEDIA_TYPE_PNG = MediaType.parse("file");
        //创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("uniquecode", "0000");
        builder.addFormDataPart("channel", "0");
        builder.addFormDataPart("version", String.valueOf(BaseApplication.getVersion()));
        builder.addFormDataPart("device", Build.MODEL);
        builder.addFormDataPart("client", "0");

        if (params != null) {
            for (String[] param : params) {
                if (param != null && param.length == 2) {
                    if (param[0] != null && !TextUtils.isEmpty(param[1])) {
                        builder.addFormDataPart(param[0], param[1]);
                    }
                }
            }
        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        if (!TextUtils.isEmpty(picPath)) {
            builder.addFormDataPart(imageKey, picPath, RequestBody.create(MEDIA_TYPE_PNG, new File(picPath)));
        }

        //构建请求体
        RequestBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url("http://tserver.iyuwan.com/" + url + ".json")//地址
                .post(requestBody)//添加请求体
                .build();

        final String urlMark = url + System.currentTimeMillis();
        checkNet(urlMark);
        //发送异步请求，同步会报错，Android4.0以后禁止在主线程中进行耗时操作
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                synchronized (httpUrls){
                    httpUrls.remove(urlMark);
                }
                final String errorMsg = e == null ? "null" : (e.getMessage() == null ? "" : e.getMessage());
                if (DEBUG) {
                    Log.d("httpResult", errorMsg);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(errorMsg, url, configKey);
                        Toast.makeText(appContext, "当前网速较差，请稍后尝试", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                synchronized (httpUrls){
                    httpUrls.remove(urlMark);
                }
                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    if (DEBUG) {
                        Log.d("httpResult", json);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(json, url, configKey);
                        }
                    });
                    callBack.onSuccessBackground(json, url, configKey);
                } else {
                    if (DEBUG) {
                        Log.d("httpResult", "errorCode = " + response.code());
                        Log.d("httpResult", response.message());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(response.message(), url, configKey);
                        }
                    });
                }
            }
        });
    }

    /**
     * 福利管家
     * @param url 接口地址
     * @param callBack 回调
     * @param params 参数
     */
    public static void postWelfare(String url, HttpCallBack callBack,Map<String,String> params) {
        postWelfare(url, callBack, 0, params);
    }

    public static void postWelfare(final String url, final HttpCallBack callBack, final int configKey, Map<String,String> params) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("channel", BaseApplication.getChannelName());
        builder.add("version", String.valueOf(BaseApplication.getVersion()));
        builder.add("device", Build.MODEL);
        builder.add("client", "0");
        StringBuffer paramSb = new StringBuffer(url);
        paramSb.append("?");
        if (params!=null&&params.size()>0){
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey()+"",entry.getValue()+"");
            }
        }
//        for (String[] param : params) {
//            if (param != null && param.length == 2) {
//                if (param[0] != null && param[1] != null) {
//                    builder.add(param[0], param[1]);
//
//                    paramSb.append(param[0]);
//                    paramSb.append("=");
//                    paramSb.append(param[1]);
//                    paramSb.append("&");
//                }
//            }
//        }
        if (DEBUG) {
            Log.i("httpPost2", httpBase.getWelfareUrl()+url+".json"+paramSb.toString());
        }
        final Request request = new Request.Builder()
                .url(httpBase.getWelfareUrl() + url + ".json")
                .post(builder.build())
                .build();
        final String urlMark = url + System.currentTimeMillis();
        checkNet(urlMark);
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                synchronized (httpUrls){
                    httpUrls.remove(urlMark);
                }
                final String errorMsg = e == null ? "null" : (e.getMessage() == null ? "" : e.getMessage());
                if (DEBUG) {
                    Log.d("httpResult", errorMsg);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(errorMsg, url, configKey);
//                        Toast.makeText(appContext, "当前网速较差，请稍后尝试", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                synchronized (httpUrls){
                    httpUrls.remove(urlMark);
                }
                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    if (DEBUG) {
                        Log.d("httpResult", json);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(json, url, configKey);
                        }
                    });
                    callBack.onSuccessBackground(json, url, configKey);
                } else {
                    if (DEBUG) {
                        Log.d("httpResult", "errorCode = " + response.code());
                        Log.d("httpResult", response.message());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(response.message(), url, configKey);
                        }
                    });
                }
            }
        });
    }





    private static void checkNet(final String url){
        synchronized (httpUrls){
            httpUrls.add(url);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(httpUrls.contains(url)){
                    Toast.makeText(appContext, "当前网速较差，请稍等", Toast.LENGTH_SHORT).show();
                    httpUrls.remove(url);
                }
            }
        }, 1000 * 3);
    }

    public interface HttpCallBack extends Serializable {
        /**
         * 网络请求成功回调，主线程
         *
         * @param json      接口返回json
         * @param url       接口请求路径
         * @param configKey 接口标识
         */
        void onSuccess(String json, String url, int configKey);

        /**
         * 网络请求处理失败，主线程
         *
         * @param json      接口返回json
         * @param url       接口请求路径
         * @param configKey 接口标识
         */
        void onFail(String json, String url, int configKey);

        /**
         * 网络请求失败回调，主线程
         *
         * @param errorMsg  错误信息
         * @param url       接口请求路径
         * @param configKey 接口标识
         */
        void onError(String errorMsg, String url, int configKey);

        /**
         * 网络请求成功回调，后台线程
         *
         * @param json      接口返回json
         * @param url       接口请求路径
         * @param configKey 接口标识
         */
        void onSuccessBackground(String json, String url, int configKey);
    }
}
