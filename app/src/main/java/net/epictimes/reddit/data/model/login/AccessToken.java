package net.epictimes.reddit.data.model.login;

public class AccessToken {

    private String accessToken;

    private String tokenType;

    private double expiresIn;

    public AccessToken(String accessToken, String tokenType, double expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public double getExpiresIn() {
        return expiresIn;
    }
}
