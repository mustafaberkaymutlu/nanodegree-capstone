package net.epictimes.reddit.data.model.vote;

public enum Vote {

    UP(1), DOWN(-1), NONE(0);

    private final int voteCode;

    Vote(int voteCode) {
        this.voteCode = voteCode;
    }

    public static Vote getByCode(int inputCode) {
        for (Vote e : values()) {
            if (e.voteCode == inputCode) return e;
        }

        throw new IllegalArgumentException();
    }

    public int getCode() {
        return voteCode;
    }

    public String getVoteAsString() {
        return String.valueOf(voteCode);
    }
}
