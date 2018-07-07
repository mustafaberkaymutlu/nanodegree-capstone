package net.epictimes.reddit.domain.login;

public class AccessTokenEntity {

    private String code;

    public AccessTokenEntity(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
