package com.nm.tinkerdemo.tinker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.nm.tinkerdemo.base.BaseApplication;
import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.TinkerServiceInternals;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import java.io.File;

/**
 * 补丁打完后监听处理
 * Created by Dongdd on 2018/6/12 17:23.
 */
public class PatchResultService extends DefaultTinkerResultService {

    private static final String TAG = PatchResultService.class.toString();


    @SuppressLint("LongLogTag")
    @Override
    public void onPatchResult(final PatchResult result) {
        if (result == null) {
            TinkerLog.e(TAG, "SampleResultService received null result!!!!");
            return;
        }
        TinkerLog.i(TAG, "SampleResultService receive result: %s", result.toString());

        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (result.isSuccess) {
                    Toast.makeText(getApplicationContext(), "修复已完成，请重新启动App", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "修复失败，请稍后重试", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (result.isSuccess) {
            deleteRawPatchFile(new File(result.rawPatchFilePath));

            if (checkIfNeedKill(result)) {
                restartProcess(); // 立即执行

//                if (Utils.isBackground()) {
//                    TinkerLog.i(TAG, "it is in background, just restart process");
//                    restartProcess();
//                } else {
//                    TinkerLog.i(TAG, "tinker wait screen to restart process");
//                    new Utils.ScreenState(getApplicationContext(), new Utils.ScreenState.IOnScreenOff() {
//                        @Override
//                        public void onScreenOff() {
//                            restartProcess();
//                        }
//                    });
//                }
            } else {
                TinkerLog.i(TAG, "I have already install the newly patch version!");
            }
        }
    }

    private void restartProcess() {
        TinkerLog.i(TAG, "app is background now, i can kill quietly");
        //you can send service or broadcast intent to restart your process
        // service or broadcast两种任选其一，此处使用broadcast
        Utils.restartAppByReceiver(getApplicationContext(), 2);
//        Utils.restartAppByService(getApplicationContext(), 2);

        BaseApplication.getInstance().exit(false); // 清除所有视图

        // 关闭App
        ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}
