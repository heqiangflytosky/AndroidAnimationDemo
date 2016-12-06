package com.android.hq.androidanimationdemo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heqiang on 16-10-27.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DataBean> mList = new ArrayList<>();
    private Activity mActivity;

    public ListAdapter(Activity activity){
        mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ContentViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ContentViewHolder contentViewHolder = (ContentViewHolder) viewHolder;
        final DataBean item = mList.get(position);
        contentViewHolder.mTitle.setText(item.title);
        contentViewHolder.mDesc.setText(item.desc);
        contentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.startAnimActivity(mActivity, item.type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateData(List<DataBean> list){
        mList = list;
        notifyDataSetChanged();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;
        public TextView mDesc;

        public ContentViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_content, parent,false));
            mTitle = (TextView) itemView.findViewById(R.id.content_title);
            mDesc = (TextView) itemView.findViewById(R.id.content_desc);
        }
    }

    public static class DataBean{
        public String title;
        public String desc;
        public int type;
        public DataBean(String title, String desc, int type){
            this.title = title;
            this.desc = desc;
            this.type = type;
        }
    }
}
