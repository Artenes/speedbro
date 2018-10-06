package io.github.artenes.speedbro.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;

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
     * @param uri         the uri for the image
     * @param placeholder the id of the placeholder
     * @param imageView   the ImageView to load the image into
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
     * @param uri       the uri for the image
     * @param imageView the ImageView to load the image into
     */
    public void load(@NonNull String uri, @NonNull ImageView imageView) {
        load(uri, NO_IMAGE, imageView);
    }

    /**
     * Load an image into a remote view
     *
     * @param uri the uri of the image
     * @param placeholder the placeholder for the image
     * @param viewId the id of the ImageView
     * @param remoteViews the remote view instance
     */
    public void load(@NonNull String uri, int placeholder, int viewId, @NonNull RemoteViews remoteViews) {
        Bitmap bitmap = null;
        try {
            bitmap = loader.get(uri);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        if (bitmap == null) {
            remoteViews.setImageViewResource(viewId, placeholder);
        } else {
            remoteViews.setImageViewBitmap(viewId, bitmap);
        }
    }

    /**
     * Get an image from the given uri
     *
     * @param uri the uri to the file
     * @return the bitmap of the image, null if uri is empty
     * @throws IOException in case of reading error
     */
    @Nullable
    public Bitmap get(@NonNull String uri) throws IOException {
        return loader.get(uri);
    }

    /**
     * This interface is to isolate the actual loading logic
     * so we can test or swap the library the do this more easily
     */
    public interface Loader {
        int NO_IMAGE = -1;

        void load(@NonNull String uri, int placeholder, @NonNull ImageView imageView);

        Bitmap get(@NonNull String uri) throws IOException;
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
            } else if (placeholder != NO_IMAGE) {
                requestCreator = Picasso.get().load(placeholder);
            } else {
                return;
            }
            if (placeholder != NO_IMAGE) {
                requestCreator.placeholder(placeholder);
            }
            requestCreator.into(imageView);
        }

        @Override
        public Bitmap get(@NonNull String uri) throws IOException {
            if (uri.isEmpty()) {
                return null;
            }
            return Picasso.get().load(uri).get();
        }
    }

}