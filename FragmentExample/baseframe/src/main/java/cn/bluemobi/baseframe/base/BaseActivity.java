package cn.bluemobi.baseframe.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Map;

import cn.bluemobi.baseframe.R;
import cn.bluemobi.baseframe.http.HttpUtil;
import cn.bluemobi.baseframe.util.JsonUtil;


public class BaseActivity extends AppCompatActivity implements HttpUtil.HttpCallBack {

    protected Toolbar toolbar;
    protected TextView toolbar_title;
    protected TextView cancel_button;
    protected ImageView back_button;

    /**
     * 通用的requestCode
     */
    public final int REQUEST_CODE_COMMON = 1024;
    /**
     * 通用的setResult时data的key
     */
    public final String RESULT_KEY = "result";

    protected FrameLayout content_view;

    protected Handler handler;

    private final boolean NOT_PAY = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fullScreen(this);
        super.setContentView(R.layout.my_base_layout);
//        addStatusViewWithColor(this,Color.parseColor("#ff182e"));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        content_view = (FrameLayout) findViewById(R.id.content_view);
        fullScreen(this);
        if (toolbar!=null){
            toolbar.setPadding(0,getStatusBarHeight(),0,0);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,getStatusBarHeight()+ dip2px(this,40));
            toolbar.setLayoutParams(layoutParams);
        }
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        cancel_button = (TextView) toolbar.findViewById(R.id.cancel_button);
        back_button = (ImageView) toolbar.findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        handler = new Handler();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        content_view.addView(view, params);
        if(NOT_PAY){
            ImageView mark = new ImageView(this);
            mark.setImageResource(((BaseApplication)BaseApplication.getInstance()).getMark());
            mark.setAlpha(0.618f);
            FrameLayout.LayoutParams markParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            markParams.gravity = Gravity.CENTER;
            content_view.addView(mark, markParams);
        }
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        content_view.addView(view, params);
    }

    protected void setTitleText(int titleRes) {
        setTitleText(getText(titleRes).toString());
    }

    protected void setTitleText(String titleText) {
        toolbar_title.setText(titleText);
        toolbar.setVisibility(TextUtils.isEmpty(titleText) ? View.GONE : View.VISIBLE);
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
        Intent intent = new Intent(this, cls);
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

    Toast toast;

    protected void toast(int msgId) {
        toast(getText(msgId));
    }

    protected void toast(CharSequence msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 自动收键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideInput();
//                v.clearFocus();
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * @param v
     * @param event
     * @return
     * @author wangxl
     * @date 2016-6-7  上午11:14:39
     * @class BaseActivity.java
     * @description 判断是否该收键盘
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    protected InputMethodManager inputMethodManager;

    public void hideInput() {
        if (inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        }
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showInput(View view) {
        view.requestFocus();
        if (inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        }
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public void post(final String url, String[]... params) {
        HttpUtil.post(url, this, params);
    }

    public void post(final String url, final int configKey, String[]... params) {
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
        String errorMessage = JsonUtil.GetStringByLevel(json, "errormessage");
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
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 利用反射获取状态栏高度
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    /**
     * 添加状态栏占位视图
     *
     * @param activity
     */
    private void addStatusViewWithColor(Activity activity, int color) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight());
        statusBarView.setBackgroundColor(color);
        contentView.addView(statusBarView, lp);
    }
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
