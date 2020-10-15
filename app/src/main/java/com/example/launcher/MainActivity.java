package com.example.launcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;

import com.example.launcher.adapter.AppListAdapter;
import com.example.launcher.adapter.AppListDecoration;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mAppListRecycleView;
    List<ResolveInfo> mApps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadApps();
        initRecycle();
    }

    private void loadApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        mApps = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
    }

    private void initRecycle() {
        mAppListRecycleView = findViewById(R.id.rv_app_list);
        mAppListRecycleView.setLayoutManager(new GridLayoutManager(this, 4));
        AppListAdapter appListAdapter = new AppListAdapter(mApps);
        appListAdapter.setItemClickListener(new AppListAdapter.onItemClickListener() {
            @Override
            public void itemClick(int position, View view) {
                ResolveInfo info = mApps.get(position);
                Intent intent = new Intent();
                intent.setPackage(info.activityInfo.packageName);
                intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                startActivity(intent);
            }
        });
        mAppListRecycleView.setAdapter(appListAdapter);
        mAppListRecycleView.addItemDecoration(new AppListDecoration(10));
    }
}