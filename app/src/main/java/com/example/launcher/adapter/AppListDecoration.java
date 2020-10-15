package com.example.launcher.adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppListDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public AppListDecoration(int space){
        this.space = space;
    }
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = space;
    }
}
