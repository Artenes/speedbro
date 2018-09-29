package io.github.artenes.speedbro.views;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import java.util.List;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.SearchState;
import io.github.artenes.speedbro.models.SearchViewModel;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.speedrun.com.models.SearchItem;

/**
 * Activity to search for games and users
 */
public class SearchActivity extends BaseActivity implements SearchItemsAdapter.OnSearchItemClickListener {

    private SearchItemsAdapter mAdapter;
    private SearchViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String query = getQuery(getIntent());
        if (query.isEmpty()) {
            finish();
        }

        mAdapter = new SearchItemsAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        initializeBaseView();

        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mViewModel.getState().observe(this, this::render);
        mViewModel.searchIfEmpty(query);

        setTitle("");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //this activity is singleTop
        //every time a new query is fired
        //this method is called
        mViewModel.search(getQuery(intent));
    }

    @Override
    public void render(State state) {

        //noinspection unchecked
        SearchState searchState = (SearchState) state;

        setTitle(getString(R.string.searching_by, searchState.getQuery()));

        if (searchState.isLoading()) {
            load();
            return;
        }

        if (searchState.hasError()) {
            showError();
            return;
        }

        List<SearchItem> items = searchState.getData();

        if (items.isEmpty()) {
            showEmpty();
            return;
        }

        mAdapter.setData(items);
        showContent();
    }

    private String getQuery(Intent intent) {
        if (!Intent.ACTION_SEARCH.equals(intent.getAction())) {
            return "";
        }
        String query = intent.getStringExtra(SearchManager.QUERY);
        if (query == null) {
            return "";
        }
        return query;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        setupSearchAction(menu);
        return true;
    }

    @Override
    public void onSearchItemClick(SearchItem searchItem) {
        if (searchItem.isGame()) {
            GameActivity.start(this, searchItem.getUrl());
            return;
        }
        if (searchItem.isUser()) {
            RunnerActivity.start(this, searchItem.getId());
        }
    }

    @Override
    protected void onTryAgain() {
        mViewModel.load();
    }

}