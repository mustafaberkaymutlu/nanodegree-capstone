package net.epictimes.reddit.data.model.login;

import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private double expiresIn;

    @SerializedName("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public double getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return scope;
    }
}
