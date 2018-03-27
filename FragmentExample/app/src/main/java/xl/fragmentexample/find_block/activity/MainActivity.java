package xl.fragmentexample.find_block.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.bluemobi.baseframe.base.BaseActivity;
import cn.bluemobi.baseframe.base.BaseFragment;
import xl.fragmentexample.R;
import xl.fragmentexample.find_block.fragment.FindbTFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    List<BaseFragment> fragmentList;
    List<ImageView> imageViewList;
    private ImageView iv_sport;
    private RippleView rip_sport;
    private ImageView iv_direct;
    private RippleView rip_direct;
    private ImageView iv_guess;
    private RippleView rip_guess;
    private ImageView iv_my;
    private RippleView rip_my;
    private RippleView welfare_manager;
    private ImageView welfare_iv;

    private int oldIndex;

    private long backTime;
    private final long FINISH_TIME = 1000 * 2;
    private TextView ids, ids1, ids2, ids3,welfare_tv;

    String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        fragmentList = new ArrayList<BaseFragment>();
//        fragmentList.add(new DirectFragment());
//        fragmentList.add(new FindFragment());
        fragmentList.add(new FindbTFragment());

        imageViewList = new ArrayList<>();

        imageViewList.add(iv_sport);
        imageViewList.add(iv_direct);
        imageViewList.add(iv_guess);
        imageViewList.add(iv_my);
        imageViewList.add(welfare_iv);

        oldIndex = -1;
        backTime = 0;
        select(0);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void select(int index) {
        if (index < 0 || index >= fragmentList.size() || oldIndex == index) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = fragmentList.get(index);

        if (f.isAdded()) {
            ft.show(f);
        } else {
            ft.add(R.id.contain, f);
        }
        if (oldIndex >= 0) {
            ft.hide(fragmentList.get(oldIndex));
        }
        oldIndex = index;
        ft.commitAllowingStateLoss();

        for (int i = 0; i < imageViewList.size(); i++) {
            imageViewList.get(i).setSelected(i == index);
        }
    }

    private void initView() {
        iv_sport = (ImageView) findViewById(R.id.iv_sport);
        rip_sport = (RippleView) findViewById(R.id.rip_sport);
        welfare_manager=(RippleView) findViewById(R.id.welfare_manager);
        iv_direct = (ImageView) findViewById(R.id.iv_direct);
        rip_direct = (RippleView) findViewById(R.id.rip_direct);
        iv_guess = (ImageView) findViewById(R.id.iv_guess);
        rip_guess = (RippleView) findViewById(R.id.rip_guess);
        iv_my = (ImageView) findViewById(R.id.iv_my);
        rip_my = (RippleView) findViewById(R.id.rip_my);
        welfare_iv = (ImageView) findViewById(R.id.welfare_iv);
        ids = (TextView) findViewById(R.id.ids);
        ids1 = (TextView) findViewById(R.id.ids1);
        ids2 = (TextView) findViewById(R.id.ids2);
        ids3 = (TextView) findViewById(R.id.ids3);
        welfare_tv = (TextView) findViewById(R.id.welfare_tv);
        rip_sport.setOnClickListener(this);
        rip_direct.setOnClickListener(this);
        rip_guess.setOnClickListener(this);
        rip_my.setOnClickListener(this);
        welfare_manager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rip_sport:
                select(0);
                changeViewColor(ids);
                break;
            case R.id.rip_direct:
                select(1);
                changeViewColor(ids1);
                break;
            case R.id.rip_guess:
                select(2);
                changeViewColor(ids2);
                break;
            case R.id.rip_my:
                select(3);
                changeViewColor(ids3);
                break;
            case R.id.welfare_manager:
                select(4);
                changeViewColor(welfare_tv);
                break;
        }
    }
    public void changeViewColor(TextView textview){
        textview.setTextColor(Color.parseColor("#ff182e"));
        if(textview.equals(ids)){
            ids1.setTextColor(Color.parseColor("#cccccc"));
            ids2.setTextColor(Color.parseColor("#cccccc"));
            ids3.setTextColor(Color.parseColor("#cccccc"));
            welfare_tv.setTextColor(Color.parseColor("#cccccc"));
        }else if(textview.equals(ids1)){
            ids.setTextColor(Color.parseColor("#cccccc"));
            ids2.setTextColor(Color.parseColor("#cccccc"));
            ids3.setTextColor(Color.parseColor("#cccccc"));
            welfare_tv.setTextColor(Color.parseColor("#cccccc"));
        }else if(textview.equals(ids2)){
            ids.setTextColor(Color.parseColor("#cccccc"));
            ids1.setTextColor(Color.parseColor("#cccccc"));
            ids3.setTextColor(Color.parseColor("#cccccc"));
            welfare_tv.setTextColor(Color.parseColor("#cccccc"));
        }else if(textview.equals(ids3)){
            ids1.setTextColor(Color.parseColor("#cccccc"));
            ids2.setTextColor(Color.parseColor("#cccccc"));
            ids.setTextColor(Color.parseColor("#cccccc"));
            welfare_tv.setTextColor(Color.parseColor("#cccccc"));
        }else if(textview.equals(welfare_tv)){
            ids.setTextColor(Color.parseColor("#cccccc"));
            ids1.setTextColor(Color.parseColor("#cccccc"));
            ids2.setTextColor(Color.parseColor("#cccccc"));
            ids3.setTextColor(Color.parseColor("#cccccc"));

        }


    }
    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - backTime > FINISH_TIME) {
            toast("再次返回退出");
            backTime = time;
        } else {
            finish();
//            if(UiUtils.tijiaochenggong==true){
//                UiUtils.tijiaochenggong = false;
//                finish();
//            }
        }
    }

//    @Subscribe
//    public void onEvent(MainPageChangeNotification notification) {
//        switch (notification.getPageIndex()) {
//            case MainPageChangeNotification.PAGE_INDEX_SPORT:
//                rip_sport.performClick();
//                break;
//            case MainPageChangeNotification.PAGE_INDEX_DIRECT:
//                rip_direct.performClick();
//                break;
//            case MainPageChangeNotification.PAGE_INDEX_GUESS:
//                rip_guess.performClick();
//                break;
//            case MainPageChangeNotification.PAGE_INDEX_MINE:
//                rip_my.performClick();
//                break;
//        }
//    }
}
