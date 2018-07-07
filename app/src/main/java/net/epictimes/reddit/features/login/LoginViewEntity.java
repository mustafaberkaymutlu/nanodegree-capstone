package net.epictimes.reddit.features.login;

import android.support.annotation.NonNull;

import net.epictimes.reddit.features.alert.AlertViewEntity;

public interface LoginViewEntity {

    class Loading implements LoginViewEntity {

    }

    class Error implements LoginViewEntity {

        @NonNull
        private AlertViewEntity alertViewEntity;

        public Error(@NonNull AlertViewEntity alertViewEntity) {
            this.alertViewEntity = alertViewEntity;
        }

        @NonNull
        public AlertViewEntity getAlertViewEntity() {
            return alertViewEntity;
        }
    }

    class LoginComplete implements LoginViewEntity {

    }

}
