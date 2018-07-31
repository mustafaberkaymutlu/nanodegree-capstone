package net.epictimes.reddit.data.model.image;

import net.epictimes.reddit.data.model.image.source.SourceMapper;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class ImageMapper implements ObservableTransformer<ImageRaw, Image> {

    private final SourceMapper sourceMapper;

    @Inject
    ImageMapper(SourceMapper sourceMapper) {
        this.sourceMapper = sourceMapper;
    }

    @Override
    public ObservableSource<Image> apply(Observable<ImageRaw> upstream) {
        return upstream
                .doOnNext(this::validate)
                .flatMap(imageRaw ->
                        Observable
                                .just(imageRaw.getSourceRaw())
                                .compose(sourceMapper)
                                .map(Image::new));
    }

    private void validate(ImageRaw raw) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (raw.getSourceRaw() == null) {
            stringBuilder.append("source cannot be empty, ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
