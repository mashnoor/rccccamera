package net.rajit.rccccameraapp.utils;

import net.rajit.rccccameraapp.activities.StreamActivity;

/**
 * Created by Nowfel Mashnoor on 3/25/2018.
 */

public class AppUrls {
    //private static final String BASE_URL = "http://202.5.52.137/cameraapi/";
    //private static final String BASE_URL = IPHelper.getBaseUrl();

    private static final String BASE_URL = "http://10.10.10.179:8090/cameraapi/";
    //private static final String BASE_URL = "http://180.211.183.206:8090/cameraapi/";
    public static final String GET_TOKEN = BASE_URL + "token";

    public static final String GET_DEPARTMENTS = BASE_URL + "api/department";

    public static String getCameraFile(int camid) {
        return BASE_URL + "api/live/" + camid;
    }

    public static final String STREAM_URL = "http://202.5.52.137/cameraapi/api/live/Bearer%20kd-TYYLMRFSYVZO7dJ-_-6AGAQN3g3kA9QKavXavm1yd2gZhK4LiazCaBS7G0OEIlTFjpRnlQcQ6lrg9VXMy1HsAI5jAr_EbmObFnUcu8g4ksgOcqbiCtEn0I8L_sO7qRBuircEalsPo1rpziehI2bKj-Ce_LNUryg3sdTs4ptEn-61JXsh8kLJ5wJ8V3aYTLPFyXLyrRgUItSLWMUPtstCfy7h2QSiwudX3TxP3brA/1";

    public static String getClassroomUrl(int deptId) {
        return BASE_URL + "api/class/" + deptId;
    }
    public static String getClassCameras(int deptId, int classId)
    {
        return BASE_URL + "api/cameradept/" + deptId + "/" + classId;
    }
}
