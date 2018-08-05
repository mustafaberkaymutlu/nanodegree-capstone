package net.epictimes.reddit.data.model.subreddit;

public class SubscribeRequest {

    private final Action action;

    private final boolean skipInitialDefaults;

    private final String subredditName;

    public SubscribeRequest(Action action, boolean skipInitialDefaults, String subredditName) {
        this.action = action;
        this.skipInitialDefaults = skipInitialDefaults;
        this.subredditName = subredditName;
    }

    public Action getAction() {
        return action;
    }

    public boolean isSkipInitialDefaults() {
        return skipInitialDefaults;
    }

    public String getSubredditName() {
        return subredditName;
    }

    public enum Action {
        SUBSCRIBE("sub"), UNSUBSCRIBE("unsub");

        private final String apiString;

        Action(String apiString) {
            this.apiString = apiString;
        }

        public String getApiString() {
            return apiString;
        }

        public String toApiString(){
            return apiString;
        }
    }

}
