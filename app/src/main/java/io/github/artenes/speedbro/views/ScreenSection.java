package io.github.artenes.speedbro.views;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * A section of a screen.
 * It is intended to be used with a RecyclerView adapter
 * where each item of this adapter represents a section of a screen
 * this is useful to better separate each part of a complex screen
 * this helps with code maintainability
 */
public interface ScreenSection {

    RecyclerView.ViewHolder makeViewHolder(ViewGroup parent);

    void bind(RecyclerView.ViewHolder viewHolder, Object data);

}