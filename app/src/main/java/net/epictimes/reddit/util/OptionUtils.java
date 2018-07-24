package net.epictimes.reddit.util;

import javax.annotation.Nonnull;

import polanski.option.Option;

public class OptionUtils {

    private OptionUtils() {
        throw new AssertionError("No instances for you!");
    }

    public static <T> T someOrThrow(@Nonnull Option<T> option) {
        return option.match(t -> t, () -> {
            throw new IllegalArgumentException("Invalid argument");
        });
    }
}
