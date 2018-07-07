package net.epictimes.reddit.features.feed;

import net.epictimes.reddit.features.alert.AlertViewEntity;

public interface FeedViewEntity {

    class UserNotLoggedIn implements FeedViewEntity {

    }

    class Error implements FeedViewEntity {

        private final AlertViewEntity alertViewEntity;

        public Error(AlertViewEntity alertViewEntity) {
            this.alertViewEntity = alertViewEntity;
        }

        public AlertViewEntity getAlertViewEntity() {
            return alertViewEntity;
        }
    }

}
