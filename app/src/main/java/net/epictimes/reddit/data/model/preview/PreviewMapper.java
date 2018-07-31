package net.epictimes.reddit.data.model.preview;

import net.epictimes.reddit.data.model.image.ImageMapper;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class PreviewMapper implements ObservableTransformer<PreviewRaw, Preview> {

    private final ImageMapper imageMapper;

    @Inject
    PreviewMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    @Override
    public ObservableSource<Preview> apply(Observable<PreviewRaw> upstream) {
        return upstream
                .doOnNext(this::validate)
                .flatMap(previewRaw ->
                        Observable
                                .just(previewRaw.getImages())
                                .flatMap(Observable::fromIterable)
                                .compose(imageMapper)
                                .toList()
                                .toObservable()
                                .map(Preview::new));
    }

    private void validate(PreviewRaw raw) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (raw.getImages() == null) {
            stringBuilder.append("images cannot be empty, ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
