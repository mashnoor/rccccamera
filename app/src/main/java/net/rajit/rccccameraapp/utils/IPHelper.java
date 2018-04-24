package net.rajit.rccccameraapp.utils;

import android.app.Activity;

import com.orhanobut.hawk.Hawk;

public class IPHelper {
    private static final String IP_KEY = "ipaddress";
    private static final String IP_PORT = "ipport";
    public static void saveIpandPort(Activity activity, String ip, String port)
    {
        Hawk.init(activity).build();
        Hawk.put(IP_KEY, ip);
        Hawk.put(IP_PORT, port);
    }
    public static String getBaseUrl()
    {
        String ip = Hawk.get(IP_KEY);
        String port = Hawk.get(IP_PORT);

        return "http://" + ip + ":" + port + "/cameraapi/";
    }
    public static boolean isAnyIp(Activity activity)
    {
        Hawk.init(activity).build();
        return Hawk.get(IP_KEY, null) != null;
    }
}
