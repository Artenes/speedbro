package io.github.artenes.speedbro.views;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.speedrun.com.models.SearchItem;

/**
 * Displays a list of search results
 */
public class SearchItemsAdapter extends RecyclerView.Adapter<SearchItemsAdapter.ResultsViewHolder> {

    private final OnSearchItemClickListener mOnSearchItemClickListener;
    private List<SearchItem> mSearchItems = new ArrayList<>(0);

    SearchItemsAdapter(OnSearchItemClickListener runClickListener) {
        mOnSearchItemClickListener = runClickListener;
    }

    /**
     * Sets the list of search items
     *
     * @param items the list of search items
     */
    public void setData(@NonNull List<SearchItem> items) {
        mSearchItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_line, parent, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {
        SearchItem item = mSearchItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mSearchItems.size();
    }

    /**
     * View holder for a latest run
     */
    public class ResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mResultText;

        ResultsViewHolder(View itemView) {
            super(itemView);
            mResultText = itemView.findViewById(R.id.search_text);
            mResultText.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        void bind(SearchItem searchItem) {
            if (searchItem.isSection()) {
                mResultText.setBackgroundColor(Color.WHITE);
                mResultText.setTextColor(Color.BLACK);
            } else {
                mResultText.setBackgroundColor(Color.TRANSPARENT);
                mResultText.setTextColor(Color.WHITE);
            }
            mResultText.setText(searchItem.getLabel());
        }

        @Override
        public void onClick(View view) {
            mOnSearchItemClickListener.onSearchItemClick(mSearchItems.get(getAdapterPosition()));
        }

    }

    public interface OnSearchItemClickListener {
        void onSearchItemClick(SearchItem searchItem);
    }

}