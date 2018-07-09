package net.epictimes.reddit.features.feed;

import net.epictimes.reddit.features.alert.AlertViewEntity;

public interface FeedViewEntity {

    class UserNotLoggedIn implements FeedViewEntity {

    }

    class Loading implements FeedViewEntity {

        private final boolean isLoading;

        public Loading(boolean isLoading) {
            this.isLoading = isLoading;
        }

        public boolean isLoading() {
            return isLoading;
        }
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
