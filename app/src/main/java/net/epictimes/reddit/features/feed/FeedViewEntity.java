package net.epictimes.reddit.features.feed;

import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.features.alert.AlertViewEntity;

interface FeedViewEntity {

    class Content implements FeedViewEntity {

        private final Listing listing;
        private final boolean paginated;

        Content(Listing listing, boolean paginated) {
            this.listing = listing;
            this.paginated = paginated;
        }

        public Listing getListing() {
            return listing;
        }

        public boolean isPaginated() {
            return paginated;
        }
    }

    class Error implements FeedViewEntity {

        private final AlertViewEntity alertViewEntity;

        Error(AlertViewEntity alertViewEntity) {
            this.alertViewEntity = alertViewEntity;
        }

        public AlertViewEntity getAlertViewEntity() {
            return alertViewEntity;
        }
    }

}
