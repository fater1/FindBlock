package xl.fragmentexample.find_block.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.bluemobi.baseframe.base.MyRecyclerAdapter;
import cn.bluemobi.baseframe.util.ImageUtil;
import xl.fragmentexample.find_block.bean.Find_Two_Bean;

/**
 * Created by wangxl on 2016/8/17.
 */
public class FindTRecyclerAdapter extends MyRecyclerAdapter<Find_Two_Bean.Sort, FindTRecyclerAdapter.ViewHolder> {
    public FindTRecyclerAdapter(Context context, List<Find_Two_Bean.Sort> dataList) {
        super(context, dataList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_recycle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Find_Two_Bean.Sort data = getItem(position);
        holder.tv_name.setText(data.getName());
        ImageUtil.displayRoundCornerImage(data.getPic(), holder.iv_icon);

        holder.card_view_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UiUtils.isFastClick()) {
                    return;
                }

                if(data.getType().equals("2")){
                    SpecialActivity.start(context,data.getTypeId(),data.getName());
                }else{
                    TwoTypeActivity.start(context,data.getTypeId(),data.getName());
                }


            }
        });
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
        public CardView card_view_id;

        public ViewHolder(View itemView) {
            super(itemView);
            this.iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.card_view_id = (CardView) itemView.findViewById(R.id.card_view_id);
        }

    }
}
