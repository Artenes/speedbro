package io.github.artenes.speedbro.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.State;

/**
 * Base fragment with helper methods
 */
public abstract class BaseFragment extends Fragment {

    private ProgressBar mProgressBar;
    private LinearLayout mErrorMessage;
    private TextView mEmptyView;
    private View mContainer;

    /**
     * Initialize the views to display content, loading and error
     * Should be called after setContentView if it will be used
     */
    void initializeBaseView(View view) {
        mProgressBar = view.findViewById(R.id.progress_bar);
        mErrorMessage = view.findViewById(R.id.error_message);
        mContainer = view.findViewById(R.id.container);
        mEmptyView = view.findViewById(R.id.empty);

        View tryAgainButton = view.findViewById(R.id.try_again_button);
        if (tryAgainButton != null) {
            tryAgainButton.setOnClickListener(this::onTryAgainAfterError);
        }
    }

    /**
     * Change the ui to loading state
     */
    void load() {
        if (!areViewsInitialized()) {
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.GONE);
        mContainer.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
    }

    /**
     * Change the ui to error state
     */
    void showError() {
        if (!areViewsInitialized()) {
            return;
        }
        mErrorMessage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mContainer.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
    }

    /**
     * Show the content of the ui
     */
    void showContent() {
        if (!areViewsInitialized()) {
            return;
        }
        mContainer.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
    }

    /**
     * Change the ui to empty state
     */
    void showEmpty() {
        if (!areViewsInitialized()) {
            return;
        }
        mEmptyView.setVisibility(View.VISIBLE);
        mContainer.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Method called when the button "Try Again"
     * os pressed in the error view
     */
    void onTryAgain() {
        //expect the child to override this
    }

    /**
     * Receives a state and render it on the view
     *
     * @param state the state to render
     */
    public abstract void render(State state);

    String getArgument(String argument) {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return "";
        }
        return bundle.getString(argument, "");
    }

    private void onTryAgainAfterError(View view) {
        onTryAgain();
    }

    private boolean areViewsInitialized() {
        return mContainer != null && mErrorMessage != null && mProgressBar != null && mEmptyView != null;
    }

}