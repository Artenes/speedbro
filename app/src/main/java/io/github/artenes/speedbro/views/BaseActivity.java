package io.github.artenes.speedbro.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.artenes.speedbro.models.State;

/**
 * Base activity with helper methods
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Receives a state and render it on the view
     *
     * @param state the state to render
     */
    public abstract void render(State state);

    /**
     * Get an extra object from the intent
     *
     * @param key the key to retrieve
     * @param defaultValue the default value if nothing is found
     * @return the value from the intent
     */
    protected Object getExtra(String key, Object defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return defaultValue;
        }

        return bundle.get(key);
    }

}