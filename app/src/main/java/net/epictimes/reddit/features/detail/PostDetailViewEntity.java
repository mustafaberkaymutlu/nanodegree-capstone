package net.epictimes.reddit.features.detail;

import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.features.alert.AlertViewEntity;

interface PostDetailViewEntity {

    class Content implements PostDetailViewEntity {

        private final Post post;

        Content(Post post) {
            this.post = post;
        }

        public Post getPost() {
            return post;
        }
    }

    class Error implements PostDetailViewEntity {

        private final AlertViewEntity alertViewEntity;

        Error(AlertViewEntity alertViewEntity) {
            this.alertViewEntity = alertViewEntity;
        }

        public AlertViewEntity getAlertViewEntity() {
            return alertViewEntity;
        }
    }

}
