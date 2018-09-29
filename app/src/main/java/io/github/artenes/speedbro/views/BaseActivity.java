package io.github.artenes.speedbro.views;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.State;

/**
 * Base activity with helper methods
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressBar mProgressBar;
    protected LinearLayout mErrorMessage;
    protected TextView mEmptyView;
    private View mContainer;

    /**
     * Initialize the views to display content, loading and error
     * Should be called after setContentView if it will be used
     */
    protected void initializeBaseView() {
        mProgressBar = findViewById(R.id.progress_bar);
        mErrorMessage = findViewById(R.id.error_message);
        mContainer = findViewById(R.id.container);
        mEmptyView = findViewById(R.id.empty);
    }

    /**
     * Change the ui to loading state
     */
    protected void load() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        if (mErrorMessage != null) {
            mErrorMessage.setVisibility(View.GONE);
        }
        if (mContainer != null) {
            mContainer.setVisibility(View.GONE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    /**
     * Change the ui to error state
     */
    protected void showError() {
        if (mErrorMessage != null) {
            mErrorMessage.setVisibility(View.VISIBLE);
        }
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if (mContainer != null) {
            mContainer.setVisibility(View.GONE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    /**
     * Change the ui to empty state
     */
    protected void showEmpty() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if (mErrorMessage != null) {
            mErrorMessage.setVisibility(View.GONE);
        }
        if (mContainer != null) {
            mContainer.setVisibility(View.GONE);
        }
    }

    /**
     * Show the content of the ui
     */
    protected void showContent() {
        if (mContainer != null) {
            mContainer.setVisibility(View.VISIBLE);
        }
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if (mErrorMessage != null) {
            mErrorMessage.setVisibility(View.GONE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
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
        return mContainer != null && mErrorMessage != null && mProgressBar != null && mEmptyView != null;
    }

    protected void setupSearchAction(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        searchView.setIconifiedByDefault(false);
    }

}