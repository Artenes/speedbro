package io.github.artenes.speedbro.utils;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Loads images into ImageViews
 */
public class ImageLoader {

    private static final int NO_IMAGE = -1;
    private final Loader loader;

    public ImageLoader(@NonNull Loader loader) {
        this.loader = loader;
    }

    /**
     * Load an image with a placeholder
     *
     * @param uri the uri for the image
     * @param placeholder the id of the placeholder
     * @param imageView the ImageView to load the image into
     */
    public void load(@NonNull String uri, int placeholder, @NonNull ImageView imageView) {
        if (uri.isEmpty() && placeholder == NO_IMAGE) {
            imageView.setVisibility(View.GONE);
            return;
        }
        loader.load(uri, placeholder, imageView);
    }

    /**
     * Load an image into an ImageView
     *
     * @param uri the uri for the image
     * @param imageView the ImageView to load the image into
     */
    public void load(@NonNull String uri, @NonNull ImageView imageView) {
        load(uri, NO_IMAGE, imageView);
    }

    /**
     * This interface is to isolate the actual loading logic
     * so we can test or swap the library the do this more easily
     */
    public interface Loader {
        int NO_IMAGE = -1;
        void load(@NonNull String uri, int placeholder, @NonNull ImageView imageView);
    }

    /**
     * This loader uses Picasso to load images
     */
    public static class PicassoLoader implements Loader {
        @Override
        public void load(@NonNull String uri, int placeholder, @NonNull ImageView imageView) {
            RequestCreator requestCreator;
            if (!uri.isEmpty()) {
                requestCreator = Picasso.get().load(uri);
            } else if (placeholder != NO_IMAGE){
                requestCreator = Picasso.get().load(placeholder);
            } else {
                return;
            }
            if (placeholder != NO_IMAGE) {
                requestCreator.placeholder(placeholder);
            }
            requestCreator.into(imageView);
        }
    }

}