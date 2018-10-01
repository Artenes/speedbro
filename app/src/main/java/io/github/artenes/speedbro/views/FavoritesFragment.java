package io.github.artenes.speedbro.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.State;

/**
 * Displays a list of favorite runs
 */
public class FavoritesFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites, container, false);
    }

    @Override
    public void render(State state) {
        //render nothing for now
    }

}