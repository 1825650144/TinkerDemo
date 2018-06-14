package com.nm.tinkerdemo.tinker;

import com.nm.tinkerdemo.BuildConfig;

/**
 * we use BuildInfo instead of {@link BuildInfo} to make less change
 * Created by Dongdd on 2018/6/14 13:38.
 */
public class BuildInfo {
    /**
     * they are not final, so they won't change with the BuildConfig values!
     */
    public static boolean DEBUG        = BuildConfig.DEBUG;
    public static String  VERSION_NAME = BuildConfig.VERSION_NAME;
    public static int     VERSION_CODE = BuildConfig.VERSION_CODE;

    public static String TINKER_ID     = BuildConfig.TINKER_ID;
}
