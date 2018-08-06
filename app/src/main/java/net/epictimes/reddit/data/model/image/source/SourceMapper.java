package net.epictimes.reddit.data.model.image.source;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class SourceMapper implements Function<SourceRaw, Source> {

    @Inject
    SourceMapper() {
    }

    @Override
    public Source apply(SourceRaw sourceRaw) {
        validate(sourceRaw);

        return new Source(sourceRaw.getUrl());
    }

    private void validate(SourceRaw raw) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (StringUtils.isBlank(raw.getUrl())) {
            stringBuilder.append("url cannot be empty, ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
