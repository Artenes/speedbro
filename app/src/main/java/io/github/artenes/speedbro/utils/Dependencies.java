package io.github.artenes.speedbro.utils;

/**
 * Common dependencies used across the application
 */
public class Dependencies {

    public static ImageLoader getImageLoader() {
        return new ImageLoader(new ImageLoader.PicassoLoader());
    }

}