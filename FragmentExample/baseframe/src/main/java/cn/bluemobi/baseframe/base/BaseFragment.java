package cn.bluemobi.baseframe.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Map;

import cn.bluemobi.baseframe.R;
import cn.bluemobi.baseframe.http.HttpUtil;
import cn.bluemobi.baseframe.util.JsonUtil;


public class BaseFragment extends Fragment implements HttpUtil.HttpCallBack {

    protected Handler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    /**
     * @param cls    跳转目标
     * @param params 参数
     * @author wangxl
     * @date 2016-6-6  上午10:19:40
     * @class BaseActivity.java
     * @description Activity跳转
     */
    public void goTo(Class<? extends Activity> cls, Params... params) {
        goToForResult(cls, -1, params);
    }

    public void goTo(Class<? extends Activity> cls, Bundle options, Params... params) {
        goToForResult(cls, -1, options, params);
    }

    public void goToForResult(Class<? extends Activity> cls, int requestCode, Params... params) {
        goToForResult(cls, requestCode, null, params);
    }

    public void goToForResult(Class<? extends Activity> cls, int requestCode, Bundle options, Params... params) {
        Intent intent = new Intent(getActivity(), cls);
        if (params != null) {
            for (Params param : params) {
                intent.putExtra(param.key, param.value);
            }
        }
        if (Build.VERSION.SDK_INT >= 16) {
            startActivityForResult(intent, requestCode, options);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * @author wangxl
     * @date 2016-6-6  上午10:20:05
     * @package_name com.bm.guorui.po.activity
     * @description Activity跳转参数
     */
    protected static class Params {
        String key;
        Serializable value;

        public Params(String key, Serializable value) {
            this.key = key;
            this.value = value;
        }
    }

    public BaseFragment addArgument(String key, Serializable value) {
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putSerializable(key, value);
        setArguments(arguments);
        return this;
    }

    protected Serializable getArgument(String key) {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return null;
        }
        return arguments.getSerializable(key);
    }

    public BaseFragment setFlag(int flag){
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putInt("flag", flag);
        setArguments(arguments);
        return this;
    }

    protected int getFlag(){
        return getFlag(-1);
    }
    protected int getFlag(int defaultValue) {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return defaultValue;
        }
        return arguments.getInt("flag", defaultValue);
    }

    public BaseFragment setFlag_String(String flag){
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putString("flag", flag);
        setArguments(arguments);
        return this;
    }

    protected String getFlag_String(){
        Bundle arguments = getArguments();
        return arguments.getString("flag", null);
    }
    public BaseFragment setData_Str(String data){
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putString("data", data);
        setArguments(arguments);
        return this;
    }

    protected String getData_Str(){
        Bundle arguments = getArguments();
        return arguments.getString("data", null);
    }


    protected int getColor(int colorId){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            return getResources().getColor(colorId, getActivity().getTheme());
        }else{
            return getResources().getColor(colorId);
        }
    }

    Toast toast;

    protected void toast(int msgId) {
        toast(getText(msgId));
    }

    protected void toast(CharSequence msg) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public void post(final String url, String[]... params){
        HttpUtil.post(url, this, params);
    }
    public void post(final String url, final int configKey, String[]... params){
        HttpUtil.post(url, this, configKey, params);
    }
    public void postWelfare(final String url, Map<String,String> params){
        HttpUtil.postWelfare(url,this, params);
    }

    @Override
    public void onSuccess(String json, String url, int configKey) {

    }

    @Override
    public void onFail(String json, String url, int configKey) {
        String errorMessage = JsonUtil.GetStringByLevel(json, "msg");
        if(!TextUtils.isEmpty(errorMessage)) {
            toast(errorMessage);
        }
    }

    @Override
    public void onError(String errorMsg, String url, int configKey) {

    }

    @Override
    public void onSuccessBackground(String json, String url, int configKey) {

    }

    public void onFail(String json){
        onFail(json, "", 0);
    }


    /**
     * 利用反射获取状态栏高度
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getActivity().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getActivity().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
