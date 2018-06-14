package com.nm.tinkerdemo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nm.tinkerdemo.R;
import com.nm.tinkerdemo.base.BaseApplication;
import com.nm.tinkerdemo.tinker.Utils;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import java.util.Timer;
import java.util.TimerTask;

public class TestActivity2 extends AppCompatActivity {

    private static final String TAG = TestActivity2.class.toString();


    private TextView tvTime;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        BaseApplication.getInstance().addActivity(this);
        tvTime = findViewById(R.id.tv_msg);
    }

    public void loadPatch(View view) {
        TinkerInstaller.onReceiveUpgradePatch(
                getApplicationContext(),
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed.apk");
    }

    public void mgs(View view) {
        Toast.makeText(this, "屏幕关闭5秒后重新启动", Toast.LENGTH_SHORT).show();
//        countDown();

//        new Utils.ScreenState(getApplicationContext(), new Utils.ScreenState.IOnScreenOff() {
//            @Override
//            public void onScreenOff() {
//                Log.e(TAG,"屏幕关闭屏幕关闭屏幕关闭屏幕关闭屏幕关闭");
//                Utils.restartAppByReceiver(getApplicationContext(), 2);
//            }
//        });


        Utils.restartAppByReceiver(getApplicationContext(), 2);

        BaseApplication.getInstance().exit(false); // 清除所有视图

        ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    private int second = 5;

    public void countDown() {
        tvTime.setText("距离结束\n" + formatTimeBySecond(second));
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
//                        tvTime.setText("距离结束\n" + formatTimeBySecond(second));
                        tvTime.setText("距离结束:" + second);
                        if (second <= 0) {
                            timer.cancel();
                        }
                        second--;
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    /**
     * me.tongleer.com
     * 通过秒格式化时间
     *
     * @param s
     * @return
     */
    public static String formatTimeBySecond(int s) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = s;
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = hour + "：" + minute + "：" + second;
        return strtime;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Utils.setBackground(false);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.setBackground(true);
    }


}
