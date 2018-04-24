package net.rajit.rccccameraapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nowfel Mashnoor on 3/25/2018.
 */

public class LoginError {
    @SerializedName("error")
    private String error;

    @SerializedName("error_description")
    private String errorDescription;

    public String getError() {
        return error;
    }


    public String getErrorDescription() {
        return errorDescription;
    }

}
