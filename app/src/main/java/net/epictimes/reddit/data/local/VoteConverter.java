package net.epictimes.reddit.data.local;

import android.arch.persistence.room.TypeConverter;

import net.epictimes.reddit.data.model.vote.Vote;

public class VoteConverter {

    @TypeConverter
    public static Vote toStatus(int voteCode) {
        return Vote.getByCode(voteCode);
    }

    @TypeConverter
    public static int toOrdinal(Vote vote) {
        return vote.getCode();
    }

}
