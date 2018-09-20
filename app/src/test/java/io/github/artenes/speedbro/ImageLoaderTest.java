package io.github.artenes.speedbro;

import android.view.View;
import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import io.github.artenes.speedbro.utils.ImageLoader;

@RunWith(MockitoJUnitRunner.class)
public class ImageLoaderTest {

    @Test
    public void hideViewIfUriIsEmptyAndNoPlaceholderWasProvided() {
        ImageLoader.Loader loader = Mockito.mock(ImageLoader.Loader.class);
        ImageView imageView = Mockito.mock(ImageView.class);

        ImageLoader imageLoader = new ImageLoader(loader);

        imageLoader.load("", imageView);

        Mockito.verify(imageView).setVisibility(View.GONE);
    }

}