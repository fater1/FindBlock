package cn.bluemobi.baseframe.base;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> dataList;

    public MyBaseAdapter(Context context, List<T> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateData(List<T> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void updateData(int position, T data){
        if(dataList == null || position < 0 || position >= getCount()){
            return;
        }
        dataList.set(position, data);
        notifyDataSetChanged();
    }

    public void removeData(T data){
        if(dataList == null){
            return;
        }
        dataList.remove(data);
        notifyDataSetChanged();
    }

    public void removeData(int position){
        if(dataList == null || position < 0 || position >= getCount()){
            return;
        }
        dataList.remove(position);
        notifyDataSetChanged();
    }

    public void clear(){
        dataList = null;
        notifyDataSetChanged();
    }

    public void addData(List<T> dataList){
        if(dataList == null){
            return;
        }
        if(this.dataList == null){
            this.dataList = new ArrayList<T>();
        }
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void addData(T data){
        if(this.dataList == null){
            this.dataList = new ArrayList<T>();
        }
        this.dataList.add(data);
        notifyDataSetChanged();
    }

    protected int getColor(int colorId){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            return context.getResources().getColor(colorId, context.getTheme());
        }else{
            return context.getResources().getColor(colorId);
        }
    }
}
