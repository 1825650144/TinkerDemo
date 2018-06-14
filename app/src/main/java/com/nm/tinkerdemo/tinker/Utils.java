
package com.nm.tinkerdemo.tinker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.tencent.tinker.lib.util.TinkerLog;

import java.util.Calendar;

/**
 * Created by Dongdd on 2018/6/13 10:16.
 */
public class Utils {
    private static final String TAG = Utils.class.toString();

    private static boolean background = false;

    public static boolean isBackground() {
        return background;
    }

    public static void setBackground(boolean back) {
        background = back;
    }

    /**
     * App屏幕状态监听处理
     */
    public static class ScreenState {
        public interface IOnScreenOff {
            void onScreenOff();
        }

        public ScreenState(final Context context, final IOnScreenOff onScreenOffInterface) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);

            // 注册监听App屏幕关闭的广播接收器
            context.registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent in) {
                    String action = in == null ? "" : in.getAction();
                    TinkerLog.i(TAG, "ScreenReceiver action [%s] ", action);
                    if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                        if (onScreenOffInterface != null) {
                            onScreenOffInterface.onScreenOff();
                        }
                    }
                    context.unregisterReceiver(this);
                }
            }, filter);
        }
    }


    /**
     * 通过Service方式重新启动app
     *
     * @param context
     * @param delayed 延迟多少毫秒
     */
    public static void restartAppByService(Context context, long delayed) {
        /**开启一个新的服务，用来重启本APP*/
        Intent intent1 = new Intent(context, RestartAppService.class);
        intent1.putExtra("Delayed", (delayed * 1000));
        context.startService(intent1);
    }


    /**
     * 通过broadcast方式方式重新启动app
     *
     * @param context
     */
    public static void restartAppByReceiver(Context context, int delayed) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //Action指向我们的TestReciver
        Intent intent = new Intent("com.nm.tinkerdemo.WAKEUP");
        PendingIntent pi = PendingIntent.getBroadcast(context, 1, intent, 0);
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.SECOND, delayed);// 设置启动时间为delayed秒后
        am.set(AlarmManager.RTC_WAKEUP, cl.getTimeInMillis(), pi);
    }

    /**
     * 启动App
     *
     * @param context
     */
    public static void startApp(Context context) {
        Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        context.startActivity(LaunchIntent);
    }

}
