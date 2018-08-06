package net.epictimes.reddit.data.model.image;

import net.epictimes.reddit.data.model.image.source.Source;
import net.epictimes.reddit.data.model.image.source.SourceMapper;
import net.epictimes.reddit.data.model.image.source.SourceRaw;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class ImageMapper implements Function<ImageRaw, Image> {

    private final SourceMapper sourceMapper;

    @Inject
    ImageMapper(SourceMapper sourceMapper) {
        this.sourceMapper = sourceMapper;
    }

    @Override
    public Image apply(ImageRaw imageRaw) {
        validate(imageRaw);

        final SourceRaw sourceRaw = imageRaw.getSourceRaw();
        final Source source = sourceMapper.apply(sourceRaw);

        return new Image(source);
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
