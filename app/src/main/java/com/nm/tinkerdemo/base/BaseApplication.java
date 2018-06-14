package com.nm.tinkerdemo.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dongdd on 2018/6/11 11:18.
 */
public class BaseApplication {
    public static Application application = null;
    public static Context context = null;

    private List<Activity> activitys = new ArrayList<>();
    private static BaseApplication instance;

    /**
     * 单例模式中获取唯一的MyApplication实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        if (null == instance) {
            instance = new BaseApplication();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activitys != null && !activitys.contains(activity)) {
            activitys.add(activity);
        }
    }

    // 遍历所有Activity并finish
    public void exit(boolean isExit) {
        Iterator<Activity> iterator = activitys.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null) {
                activity.finish();
                iterator.remove();
            }
        }
        if (isExit) {
            System.exit(0);
        }
    }
}
