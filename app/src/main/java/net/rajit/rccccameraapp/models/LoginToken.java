package net.rajit.rccccameraapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nowfel Mashnoor on 3/25/2018.
 */

public class LoginToken {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private Integer expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }


}
