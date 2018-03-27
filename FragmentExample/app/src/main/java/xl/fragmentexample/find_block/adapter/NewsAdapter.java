package xl.fragmentexample.find_block.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import bluemobi.cn.esport.R;
import cn.bluemobi.baseframe.base.MyBaseAdapter;

/**
 * 作者： li.mr
 * 时间： 2017/12/22
 */

public class NewsAdapter extends MyBaseAdapter<String> implements View.OnClickListener{
    private int clickPosition=-1;
//    Context mContext;

    public NewsAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsViewHolder viewHolder=null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.news_item, null);
            viewHolder=new NewsViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
                viewHolder= (NewsViewHolder) convertView.getTag();
        }

        if (position==0){
            viewHolder.news_item_close.setTag(position);
            viewHolder.news_item_type1.setVisibility(View.VISIBLE);
            viewHolder.news_item_type2.setVisibility(View.GONE);
            viewHolder.news_item_type3.setVisibility(View.GONE);
        }else if(position==1){
            viewHolder.news_item_close1.setTag(position);
            viewHolder.news_item_type1.setVisibility(View.GONE);
            viewHolder.news_item_type2.setVisibility(View.VISIBLE);
            viewHolder.news_item_type3.setVisibility(View.GONE);
        }else if(position==2){
            viewHolder.news_item_close2.setTag(position);
            viewHolder.news_item_type1.setVisibility(View.GONE);
            viewHolder.news_item_type2.setVisibility(View.GONE);
            viewHolder.news_item_type3.setVisibility(View.VISIBLE);
        }else{
            viewHolder.news_item_close.setTag(position);
            viewHolder.news_item_type1.setVisibility(View.VISIBLE);
            viewHolder.news_item_type2.setVisibility(View.GONE);
            viewHolder.news_item_type3.setVisibility(View.GONE);
        }

        viewHolder.news_item_close.setOnClickListener(this);
        viewHolder.news_item_close1.setOnClickListener(this);
        viewHolder.news_item_close2.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position= (int) v.getTag();
    }

    public static class NewsViewHolder{
        RelativeLayout news_item_type1;
        ImageView news_item_one_img;
        TextView news_item_one_title;
        TextView news_item_one_author;
        RelativeLayout news_item_close;

        RelativeLayout news_item_type2;
        ImageView news_item_two_img1;
        ImageView news_item_two_img2;
        ImageView news_item_two_img3;
        TextView news_item_two_title;
        TextView news_item_two_author;
        RelativeLayout news_item_close1;

        RelativeLayout news_item_type3;
        ImageView news_item_three_img;
        TextView news_item_three_title;
        TextView news_item_three_author;
        RelativeLayout news_item_close2;

        public NewsViewHolder(View view) {
            this.news_item_type1 = (RelativeLayout) view.findViewById(R.id.news_item_type1);
            this.news_item_one_title= (TextView) view.findViewById(R.id.news_item_one_title);
            this.news_item_one_img= (ImageView) view.findViewById(R.id.news_item_one_img);
            this.news_item_one_author= (TextView) view.findViewById(R.id.news_item_one_author);
            this.news_item_close= (RelativeLayout) view.findViewById(R.id.news_item_close);

            this.news_item_type2 = (RelativeLayout) view.findViewById(R.id.news_item_type2);
            this.news_item_two_img1 = (ImageView) view.findViewById(R.id.news_item_two_img1);
            this.news_item_two_img2 = (ImageView) view.findViewById(R.id.news_item_two_img2);
            this.news_item_two_img3 = (ImageView) view.findViewById(R.id.news_item_two_img3);
            this.news_item_two_title = (TextView) view.findViewById(R.id.news_item_two_title);
            this.news_item_two_author = (TextView) view.findViewById(R.id.news_item_two_author);
            this.news_item_close1 = (RelativeLayout) view.findViewById(R.id.news_item_close1);

            this.news_item_type3 = (RelativeLayout) view.findViewById(R.id.news_item_type3);
            this.news_item_three_title= (TextView) view.findViewById(R.id.news_item_one_title);
            this.news_item_three_img= (ImageView) view.findViewById(R.id.news_item_three_img1);
            this.news_item_three_author= (TextView) view.findViewById(R.id.news_item_one_author);
            this.news_item_close2= (RelativeLayout) view.findViewById(R.id.news_item_close2);
        }
    }

}
