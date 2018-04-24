package net.rajit.rccccameraapp.utils;

import android.app.Activity;

import com.orhanobut.hawk.Hawk;

/**
 * Created by Nowfel Mashnoor on 3/25/2018.
 */

public class LoginUtils {
    private static final String KEY_TOKEN = "token";

    public static void setLoginToken(Activity activity, String token) {
        Hawk.init(activity).build();
        Hawk.put(KEY_TOKEN, token);
    }

    public static String getLoginToken(Activity activity) {
        Hawk.init(activity).build();
        return Hawk.get(KEY_TOKEN, null);
    }

}
