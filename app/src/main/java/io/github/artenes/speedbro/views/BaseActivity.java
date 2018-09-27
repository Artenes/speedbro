package io.github.artenes.speedbro.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.State;

/**
 * Base activity with helper methods
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressBar mProgressBar;
    protected LinearLayout mErrorMessage;
    private View mContainer;

    /**
     * Initialize the views to display content, loading and error
     * Should be called after setContentView if it will be used
     */
    protected void initializeBaseView() {
        mProgressBar = findViewById(R.id.progress_bar);
        mErrorMessage = findViewById(R.id.error_message);
        mContainer = findViewById(R.id.container);
    }

    /**
     * Change the ui to loading state
     */
    protected void load() {
        if (!areViewsInitialized()) {
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.GONE);
        mContainer.setVisibility(View.GONE);
    }

    /**
     * Change the ui to error state
     */
    protected void showError() {
        if (!areViewsInitialized()) {
            return;
        }
        mErrorMessage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mContainer.setVisibility(View.GONE);
    }

    /**
     * Show the content of the ui
     */
    protected void showContent() {
        if (!areViewsInitialized()) {
            return;
        }
        mContainer.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Method called when the button "Try Again"
     * os pressed in the error view
     */
    protected void onTryAgain() {
        //expect the child to override this
    }

    /**
     * Receives a state and render it on the view
     *
     * @param state the state to render
     */
    public abstract void render(State state);

    /**
     * Get an extra object from the intent
     *
     * @param key          the key to retrieve
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

    public void onTryAgainAfterError(View view) {
        onTryAgain();
    }

    /**
     * Method that can be available through the view
     * that is called when a fake up navigation button is pressed
     *
     * @param view the view that is pressed
     */
    public void onUpNavigationPressed(View view) {
        finish();
    }

    private boolean areViewsInitialized() {
        return mContainer != null && mErrorMessage != null && mProgressBar != null;
    }

}