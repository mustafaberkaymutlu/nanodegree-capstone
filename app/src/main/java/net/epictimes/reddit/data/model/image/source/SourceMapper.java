package net.epictimes.reddit.data.model.image.source;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class SourceMapper implements ObservableTransformer<SourceRaw, Source> {

    @Inject
    SourceMapper() {
    }

    @Override
    public ObservableSource<Source> apply(Observable<SourceRaw> upstream) {
        return upstream
                .doOnNext(this::validate)
                .map(sourceRaw -> new Source(sourceRaw.getUrl()));
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
