package net.epictimes.reddit.data.model.login;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class AccessToken {

    @PrimaryKey
    private int id = 0;

    private String accessToken;

    private String tokenType;

    private double expiresIn;

    public AccessToken(String accessToken, String tokenType, double expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public double getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(double expiresIn) {
        this.expiresIn = expiresIn;
    }
}
