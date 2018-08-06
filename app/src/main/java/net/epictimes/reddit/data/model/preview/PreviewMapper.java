package net.epictimes.reddit.data.model.preview;

import net.epictimes.reddit.data.model.image.Image;
import net.epictimes.reddit.data.model.image.ImageMapper;
import net.epictimes.reddit.data.model.image.ImageRaw;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class PreviewMapper implements Function<PreviewRaw, Preview> {

    private final ImageMapper imageMapper;

    @Inject
    PreviewMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    @Override
    public Preview apply(PreviewRaw previewRaw) {
        validate(previewRaw);

        final List<ImageRaw> imagesRaw = previewRaw.getImages();

        final List<Image> images = new ArrayList<>();
        for (ImageRaw imageRaw : imagesRaw) {
            images.add(imageMapper.apply(imageRaw));
        }

        return new Preview(images);
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
