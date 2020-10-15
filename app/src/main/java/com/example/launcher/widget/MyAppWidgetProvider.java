package com.example.launcher.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.example.launcher.MainActivity;
import com.example.launcher.R;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidgetProvider extends AppWidgetProvider {

    public static final String CHANGE_IMAGE = "com.example.joy.action.CHANGE_IMAGE";
    private ComponentName mComponentName;

    private int[] imgs = new int[]{
            R.drawable.example_appwidget_preview,
            R.drawable.example_appwidget_preview,
            R.drawable.example_appwidget_preview,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for(int widgetId : appWidgetIds){
            RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.my_app_widget_provider);
            mRemoteViews.setImageViewResource(R.id.iv_test, R.mipmap.ic_launcher);
            mRemoteViews.setTextViewText(R.id.btn_test, "点击跳转到Activity");
            Intent skipIntent = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context, 200, skipIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            mRemoteViews.setOnClickPendingIntent(R.id.btn_test, pi);

            // 设置 ListView 的adapter。
            // (01) intent: 对应启动 ListViewService(RemoteViewsService) 的intent
            // (02) setRemoteAdapter: 设置 ListView 的适配器
            // 通过setRemoteAdapter将 ListView 和ListViewService关联起来，
            // 以达到通过 GridWidgetService 更新 gridview 的目的
            Intent lvIntent = new Intent(context, ListViewService.class);
            mRemoteViews.setRemoteAdapter(widgetId, R.id.lv_test, lvIntent);
            mRemoteViews.setEmptyView(R.id.lv_test,android.R.id.empty);

            // 设置响应 ListView 的intent模板
            // 说明：“集合控件(如GridView、ListView、StackView等)”中包含很多子元素，如GridView包含很多格子。
            // 它们不能像普通的按钮一样通过 setOnClickPendingIntent 设置点击事件，必须先通过两步。
            // (01) 通过 setPendingIntentTemplate 设置 “intent模板”，这是比不可少的！
            // (02) 然后在处理该“集合控件”的RemoteViewsFactory类的getViewAt()接口中 通过 setOnClickFillInIntent 设置“集合控件的某一项的数据”

            /*
             * setPendingIntentTemplate 设置pendingIntent 模板
             * setOnClickFillInIntent 可以将fillInIntent 添加到pendingIntent中
             */
            Intent toIntent = new Intent();
            toIntent.setAction(CHANGE_IMAGE);
            toIntent.setComponent(new ComponentName(context,MyAppWidgetProvider.class));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, toIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mRemoteViews.setPendingIntentTemplate(R.id.lv_test, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, mRemoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(TextUtils.equals(CHANGE_IMAGE, intent.getAction())){
            Bundle extras = intent.getExtras();
            int position = extras.getInt(ListViewService.INITENT_DATA);
            RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.my_app_widget_provider);
            mRemoteViews.setImageViewResource(R.id.iv_test, imgs[position]);
            mComponentName = new ComponentName(context, MyAppWidgetProvider.class);
            AppWidgetManager.getInstance(context).updateAppWidget(mComponentName, mRemoteViews);
        }
    }
}

