package net.epictimes.reddit.data.model.login;

import com.google.gson.annotations.SerializedName;

public class AccessTokenRequest {

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("code")
    private String code;

    @SerializedName("redirect_uri")
    private String redirectUri;

    public AccessTokenRequest(String grantType, String code, String redirectUri) {
        this.grantType = grantType;
        this.code = code;
        this.redirectUri = redirectUri;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getCode() {
        return code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
