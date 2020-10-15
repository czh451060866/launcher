package com.example.launcher.adapter;

import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.launcher.R;

import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {
    private List<ResolveInfo> mAppList;
    private onItemClickListener mItemClickListener;

    public AppListAdapter(List<ResolveInfo> appList){
        mAppList = appList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ResolveInfo info = mAppList.get(position);
        holder.imageView.setImageDrawable(info.activityInfo.loadIcon(holder.itemView.getContext().getPackageManager()));
        holder.textView.setText(info.loadLabel(holder.itemView.getContext().getPackageManager()));
        if(mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.itemClick(position, v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    public void setItemClickListener(onItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_app_icon);
            textView = itemView.findViewById(R.id.tv_app_name);
        }
    }

    public interface onItemClickListener{
        void itemClick(int position, View view);
    }
}
