package com.nm.tinkerdemo.tinker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 该接收器只用来让APP重启
 * Created by Dongdd on 2018/6/13 10:26.
 */
public class RestartAppReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.startApp(context.getApplicationContext());
    }

}
