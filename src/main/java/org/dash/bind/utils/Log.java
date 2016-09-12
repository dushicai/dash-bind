package org.dash.bind.utils;

import android.text.TextUtils;

/**
 * Created by shicai.du on 2016/9/12.
 */
public class Log {


    public static boolean isDebug = true;

    public static void i(String tag) {

        if (!TextUtils.isEmpty(tag) && isDebug)
            android.util.Log.i("annotation", tag);
    }
}
