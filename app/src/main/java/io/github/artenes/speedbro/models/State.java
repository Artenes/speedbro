package io.github.artenes.speedbro.models;

import android.arch.lifecycle.MutableLiveData;

/**
 * A state of the application
 */
public class State extends MutableLiveData<State> {

    public void update() {
        setValue(this);
    }

}
