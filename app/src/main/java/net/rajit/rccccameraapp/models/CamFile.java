package net.rajit.rccccameraapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nowfel Mashnoor on 3/27/2018.
 */

public class CamFile {

    @SerializedName("cameraID")
    private int cameraId;

    @SerializedName("cameraName")
    private String cameraName;

    @SerializedName("filename")
    private String filename;

    public String getFilename() {
        return filename;
    }

    public int getCameraId() {
        return cameraId;
    }

    public String getCameraName() {
        return cameraName;
    }
}
