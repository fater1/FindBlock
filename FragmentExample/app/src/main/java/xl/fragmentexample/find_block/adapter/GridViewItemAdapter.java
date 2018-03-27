package xl.fragmentexample.find_block.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bluemobi.cn.esport.R;
import bluemobi.cn.esport.activity.HttpUrlWebViewActivity;
import bluemobi.cn.esport.activity.TypeListActivity;
import bluemobi.cn.esport.bean.Find_Two_Bean;
import bluemobi.cn.esport.bean.SortInfo;
import bluemobi.cn.esport.util.UiUtils;
import cn.bluemobi.baseframe.base.MyBaseAdapter;
import cn.bluemobi.baseframe.util.ImageUtil;

/**
 * Created by li.mr on 2017/4/22
 */
public class GridViewItemAdapter extends MyBaseAdapter<SortInfo> {
    private Find_Two_Bean find_data;
    private int selectorPosition = -1;
    private FTGirditem_Adapter ftgirditem_adapter;

    public GridViewItemAdapter(Context context, List<SortInfo> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_ft_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final SortInfo data = getItem(position);

        if(position==1||position==2&&find_data!=null){
            holder.xuanchu_image.setVisibility(View.VISIBLE);
            if(position==1){
                ImageUtil.displayImage(find_data.getGameBanner().get(0).getPic(),holder.xuanchu_image);

            }else{
                ImageUtil.displayImage(find_data.getSingleBanner().get(0).getPic(),holder.xuanchu_image);
            }
            holder.xuanchu_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String gameurl = "";
                    if(position==1){
                        gameurl = find_data.getGameBanner().get(0).getGameUrl();
                    }else{
                        gameurl = find_data.getSingleBanner().get(0).getGameUrl();
                    }
                    if(TextUtils.isEmpty(find_data.getGameBanner().get(0).getGameUrl())){
                        TypeListActivity.start(context,find_data.getGameBanner().get(0).getTypeId(),find_data.getGameBanner().get(0).getName());
                    }else{
                        HttpUrlWebViewActivity.startUrl(context, gameurl);
                        UiUtils.postJL(find_data.getGameBanner().get(0).getTypeId(),"2");
                    }
                }
            });
        }else{
            holder.xuanchu_image.setVisibility(View.GONE);
        }
        holder.lable_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UiUtils.isFastClick()) {
                    return;
                }
                TypeListActivity.start(context,data.getTypeId(),data.getName());
            }
        });

        holder.typename.setText(data.getName());
        ftgirditem_adapter = new FTGirditem_Adapter(context,data.getList());
        holder.gridView_data.setAdapter(ftgirditem_adapter);

        return convertView;
    }


    public static class ViewHolder {
        public TextView typename;
        public ImageView xuanchu_image;
        public GridView gridView_data;
        public LinearLayout lable_back;

        public ViewHolder(View rootView) {
            this.lable_back = (LinearLayout) rootView.findViewById(R.id.lable_back);
            this.typename = (TextView) rootView.findViewById(R.id.typename);
            this.xuanchu_image = (ImageView) rootView.findViewById(R.id.xuanchu_image);
            this.gridView_data = (GridView) rootView.findViewById(R.id.gridView_data);
        }
    }
    public void setDatas(Find_Two_Bean fd) {
        find_data = fd;
    }
    public void StartsetPositin(int position){
        selectorPosition = position;
    }
}
