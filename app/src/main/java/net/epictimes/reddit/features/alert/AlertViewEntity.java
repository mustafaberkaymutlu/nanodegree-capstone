package net.epictimes.reddit.features.alert;

public class AlertViewEntity {

    private final String message;

    public AlertViewEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
