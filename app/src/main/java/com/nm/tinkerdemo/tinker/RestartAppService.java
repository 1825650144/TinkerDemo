package com.nm.tinkerdemo.tinker;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 该服务只用来让APP重启，生命周期也仅仅是只是重启APP。重启完即自我杀死
 * Created by Dongdd on 2018/6/13 10:16.
 */
public class RestartAppService extends Service {

    private Handler handler;

    public RestartAppService() {
        handler = new Handler();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        // 关闭应用后多久重新启动
        long stopDelayed = intent.getLongExtra("Delayed", 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.startApp(getApplicationContext());
                RestartAppService.this.stopSelf();
            }
        }, stopDelayed);
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
