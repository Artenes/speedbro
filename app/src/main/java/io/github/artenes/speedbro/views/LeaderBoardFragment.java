package io.github.artenes.speedbro.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Fragment that displays a list of runs
 */
public class LeaderBoardFragment extends Fragment {

    private static final String KEY_CATEGORY_URL = "CATEGORY_URL";
    private static final String KEY_CATEGORY_NAME = "CATEGORY_NAME";

    public static LeaderBoardFragment newInstance(String categoryName, String categoryUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CATEGORY_URL, categoryUrl);
        bundle.putString(KEY_CATEGORY_NAME, categoryName);
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public String getTitle() {
        return getArgument(KEY_CATEGORY_NAME);
    }

    private String getArgument(String argument) {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return "";
        }
        return bundle.getString(argument, "");
    }

}