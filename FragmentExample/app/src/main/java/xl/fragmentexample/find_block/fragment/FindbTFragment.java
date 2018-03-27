package xl.fragmentexample.find_block.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.bluemobi.baseframe.base.BaseFragment;
import cn.bluemobi.baseframe.util.ImageUtil;
import cn.bluemobi.baseframe.util.JsonUtil;
import cn.bluemobi.baseframe.view.MyListView;
import cn.bluemobi.baseframe.view.imagecycleview.ADInfo;
import cn.bluemobi.baseframe.view.imagecycleview.ImageCycleView;
import xl.fragmentexample.R;
import xl.fragmentexample.find_block.adapter.FindTRecyclerAdapter;
import xl.fragmentexample.find_block.adapter.GridViewItemAdapter;
import xl.fragmentexample.find_block.adapter.NewsAdapter;


public class FindbTFragment extends BaseFragment implements ImageCycleView.ImageCycleViewListener,
        PullToRefreshBase.OnRefreshListener2,
        View.OnClickListener {

    private PullToRefreshScrollView scrollView;
    private MyListView mListView;
    private MyListView redian_listview;
    private List<String> mDatas;
    private TextView jingcai;
    private ImageCycleView ic_cycle;
    private RecyclerView rv_hot;
    private RelativeLayout gg_xf;
    private TextView seach_id;
    private ImageView gg_xf_image;
    private ImageView quanxiao_image;
    private String type_gg;
    private RelativeLayout toolbar;
    private RelativeLayout zhubo_tuijian;
    private RelativeLayout zixun_tuijian;
    private ArrayAdapter<String> mAdapter;
    private final static int REFRESH_COMPLETE = 0;
    private final static int LOAD_COMPLETE = 1;
    private FindTRecyclerAdapter findtrecycleradapter; //横向滑动数据列表
    private GridViewItemAdapter gridviewitemadapter; //每一模块的列表
    private View title_content;
    private NewsAdapter newsAdapter; //新闻列表

    private View tuijian_view1;
    private View tuijian_view2;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    scrollView.onRefreshComplete();
                    gridviewitemadapter.notifyDataSetChanged();
                    break;
                case LOAD_COMPLETE:
                    scrollView.onRefreshComplete();
                    gridviewitemadapter.notifyDataSetChanged();
                    break;
            }
        }

        ;
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find_two_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        post(HttpUrl.FIND_TWO);
        post(HttpUrl.GUANGGAO, new String[]{"location", "2"});
    }

    private void init(View view) {

        scrollView= (PullToRefreshScrollView) view.findViewById(R.id.findbtscrollview);
        scrollView.getLoadingLayoutProxy().setOnLyImg(true);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
//        scrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
//                "上次刷新时间" );
//        scrollView.getLoadingLayoutProxy().setRefreshingLabel("");
//        scrollView.getLoadingLayoutProxy()
//                .setPullLabel( "下拉刷新");
//        //sc_scrollview.getLoadingLayoutProxy().setRefreshingLabel( "refreshingLabel");
//        scrollView.getLoadingLayoutProxy().setReleaseLabel("松开即可刷新" );

        mListView = (MyListView) view.findViewById(R.id.listview);
        List<String> list=new ArrayList<>();

        newsAdapter =new NewsAdapter(getActivity(),list);
        redian_listview = (MyListView) view.findViewById(R.id.redian_listview);
        gg_xf = (RelativeLayout) view.findViewById(R.id.gg_xf);
        gg_xf_image = (ImageView) view.findViewById(R.id.gg_xf_image);
        quanxiao_image = (ImageView) view.findViewById(R.id.quanxiao_image);
        gridviewitemadapter = new GridViewItemAdapter(getActivity(), null);
        mListView.setAdapter(gridviewitemadapter);
//        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_direct_child, null, false);
        ic_cycle = (ImageCycleView) view.findViewById(R.id.ic_cycle);
        rv_hot = (RecyclerView) view.findViewById(R.id.rv_hot);
        jingcai = (TextView) view.findViewById(R.id.jingcai);
        seach_id = (TextView) view.findViewById(R.id.seach_id);
        zhubo_tuijian= (RelativeLayout) view.findViewById(R.id.zhubo_tuijian);
        zixun_tuijian= (RelativeLayout) view.findViewById(R.id.zixun_tuijian);
        tuijian_view1=view.findViewById(R.id.tuijian_view1);
        tuijian_view2=view.findViewById(R.id.tuijian_view2);

        title_content =view.findViewById(R.id.title_content);
        //设置横向滑动的属性
        rv_hot.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        findtrecycleradapter = new FindTRecyclerAdapter(getActivity(), null);
        rv_hot.setAdapter(findtrecycleradapter);
//        mListView.addHeaderView(headView);
        scrollView.setOnRefreshListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "position=" + position, Toast.LENGTH_SHORT).show();
            }
        });
        zhubo_tuijian.setVisibility(View.VISIBLE);
        zhubo_tuijian.setOnClickListener(this);
        zixun_tuijian.setOnClickListener(this);

        seach_id.setOnClickListener(this);
        jingcai.setOnClickListener(this);
        quanxiao_image.setOnClickListener(this);
        toolbar= (RelativeLayout) view.findViewById(R.id.toolbar);
        toolbar.setPadding(0,getStatusBarHeight(),0,0);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,getStatusBarHeight()+ DensityUtils.dip2px(getActivity(),40));
        toolbar.setLayoutParams(layoutParams);
        redian_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),NewsDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhubo_tuijian:
                tuijian_view1.setVisibility(View.VISIBLE);
                tuijian_view2.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                TranslateAnimation translateAnimationins = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1f,
                        Animation.RELATIVE_TO_SELF,0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0f);
                translateAnimationins.setDuration(500);
                translateAnimationins.setFillAfter(true);
                mListView.startAnimation(translateAnimationins);
                translateAnimationins.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        redian_listview.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

                TranslateAnimation translateAnimationouts = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.0f);
                translateAnimationouts.setDuration(500);
                translateAnimationouts.setFillAfter(true);
                redian_listview.startAnimation(translateAnimationouts);
                break;
            case R.id.zixun_tuijian:
                redian_listview.setAdapter(newsAdapter);
                mListView.setVisibility(View.GONE);
                tuijian_view2.setVisibility(View.VISIBLE);
                tuijian_view1.setVisibility(View.GONE);
                TranslateAnimation translateAnimationout = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.0f);
                TranslateAnimation translateAnimationin = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f,
                        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0f);
                translateAnimationout.setDuration(500);
                translateAnimationout.setFillAfter(true);

                translateAnimationin.setDuration(500);
                translateAnimationin.setFillAfter(true);
                mListView.startAnimation(translateAnimationout);
                redian_listview.startAnimation(translateAnimationin);
                translateAnimationin.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        redian_listview.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                break;
            case R.id.seach_id:
                if (UiUtils.isFastClick()) {
                    return;
                }
                goTo(NewSearchTwoActivity.class, new Params("sear_type", "1"));

                break;
            case R.id.jingcai:
                JingcaiActivity.start(getActivity());

                break;
            case R.id.quanxiao_image:
                gg_xf.setVisibility(View.GONE);

                break;
        }
    }

    @Override
    public void onSuccess(String json, String url, int configKey) {
        super.onSuccess(json, url, configKey);
        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
        jingcai.setVisibility(View.VISIBLE);
        if (HttpUrl.FIND_TWO.equals(url)) {
            if (JsonUtil.isSuccess(json)) {
                Find_Two_Bean findtdata = JsonUtil.jsonToBean(json, Find_Two_Bean.class);
                if (findtdata != null) {
                    title_content.setVisibility(View.VISIBLE);
                    ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
                    for (Find_Two_Bean.Banner bannerInfo : findtdata.getBanner()) {
                        ADInfo info = new ADInfo();
                        info.setImageUrl(bannerInfo.getPic());
                        info.setGameUrl(bannerInfo.getGameUrl());
                        info.setId(bannerInfo.getTypeId());
                        info.setFlag(bannerInfo.getFlag());
                        info.setName(bannerInfo.getName());
                        infos.add(info);
                    }
                    if (infos.isEmpty()) {
                        ADInfo info = new ADInfo();
                        info.setImageUrl("");
                        infos.add(info);
                    }
                    ic_cycle.setImageResources(infos, this);
                    findtrecycleradapter.updateData(findtdata.getSort());
                    gridviewitemadapter.updateData(findtdata.getSortInfo());
                    gridviewitemadapter.setDatas(findtdata);
                }
            }
        }
        if (HttpUrl.GUANGGAO.equals(url)) {
            if (JsonUtil.isSuccess(json)) {
                final SortInfo sort = JsonUtil.jsonToBean(json, SortInfo.class);
                if (sort.getTotal() < 1) {
                    gg_xf.setVisibility(View.GONE);
                } else {
                    gg_xf.setVisibility(View.VISIBLE);
                    type_gg = sort.list.get(0).getType();
                    ImageUtil.displayImage(sort.list.get(0).getRemark(), gg_xf_image);

                    gg_xf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(TextUtils.isEmpty(type_gg)){
                                return;
                            }
                            if(type_gg.equals("1")){
                                HttpUrlWebViewActivity.startUrl(getActivity(), sort.list.get(0).getUrl());
                            }
                            if(type_gg.equals("2")){
                                DirectInfo directInfo = new DirectInfo(sort.list.get(0).getAnchorId(),sort.list.get(0).getUrl(),"");
                                AnchorHttpActivity.startObj(getActivity(), directInfo);
                            }
                            post(HttpUrl.GUANGGAO_TJ, new String[]{"bannerId", sort.list.get(0).getId()}, new String[]{"location", "2"});
                        }
                    });
                }
            }
        }
    }

//    @Override
//    public void onRefresh() {
//        post(HttpUrl.FIND_TWO);
//    }
//
//    @Override
//    public void onLoadMore() {
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(100);
//                    mHandler.sendEmptyMessage(LOAD_COMPLETE);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    @Override
    public void displayImage(String imageURL, ImageView imageView) {
        ImageUtil.displayCycleImage(imageURL, imageView);
    }

    @Override
    public void onImageClick(ADInfo info, int position, View imageView) {
        if (UiUtils.isFastClick()) {
            return;
        }
        if (TextUtils.isEmpty(info.getGameUrl())) {
            TypeListActivity.start(getActivity(), info.getId(), info.getName());
        } else {
            DirectInfo directInfo = new DirectInfo();
            directInfo.setUrl(info.getGameUrl());
            directInfo.setX(false);
            directInfo.setFlag(info.getFlag());
            directInfo.setAnchorName(info.getName());
            if (info.getFlag().equals("4")) {
                if (!UserSp.isLogin()) {
                    toast("登录之后才可以参加活动");
                    return;
                }
            }
            HttpUrlWebViewActivity.startObj(getActivity(), directInfo);
            UiUtils.postJL(info.getId(), "2");
        }
    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        post(HttpUrl.FIND_TWO);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    mHandler.sendEmptyMessage(LOAD_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onResume() {
//        scrollView.setFocusableInTouchMode(true);
        super.onResume();
    }
}
