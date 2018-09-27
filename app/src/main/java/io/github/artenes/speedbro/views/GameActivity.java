package io.github.artenes.speedbro.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.DataState;
import io.github.artenes.speedbro.models.GameViewModel;
import io.github.artenes.speedbro.models.GameViewModelFactory;
import io.github.artenes.speedbro.models.State;
import io.github.artenes.speedbro.speedrun.com.models.Category;
import io.github.artenes.speedbro.speedrun.com.models.Game;
import io.github.artenes.speedbro.utils.Dependencies;
import io.github.artenes.speedbro.utils.ImageLoader;

/**
 * Activity to display details of a game
 */
public class GameActivity extends BaseActivity {

    private static final String EXTRA_GAME_ID = "GAME_ID";

    public static void start(@NonNull Context context, @NonNull String gameId) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(EXTRA_GAME_ID, gameId);
        context.startActivity(intent);
    }

    private ImageView mGameCover;
    private TextView mGameTitle;
    private TextView mGameYear;
    private TextView mGamePlatforms;
    private GameViewModel mViewModel;
    private ImageLoader mImageLoader;
    private CategoriesAdapter mCategoriesAdapter;
    private ViewPager mCategoriesPager;
    private TabLayout mCategoriesTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String gameId = (String) getExtra(EXTRA_GAME_ID, "");
        if (gameId.isEmpty()) {
            finish();
        }

        mGameCover = findViewById(R.id.game_cover);
        mGameTitle = findViewById(R.id.game_title);
        mGameYear = findViewById(R.id.game_year);
        mGamePlatforms = findViewById(R.id.game_platforms);
        mCategoriesPager = findViewById(R.id.categories_pager);
        mCategoriesTabs = findViewById(R.id.categories_tabs);
        initializeBaseView();

        GameViewModelFactory factory = new GameViewModelFactory(gameId);
        mViewModel = ViewModelProviders.of(this, factory).get(GameViewModel.class);
        mImageLoader = Dependencies.getImageLoader();

        mCategoriesAdapter = new CategoriesAdapter(getSupportFragmentManager());

        mViewModel.getState().observe(this, this::render);
        mViewModel.load();
    }

    @Override
    public void render(State state) {

        //noinspection unchecked
        DataState<Game> gameState = (DataState<Game>) state;

        if (gameState.isLoading()) {
            load();
            return;
        }

        if (gameState.hasError()) {
            showError();
            return;
        }

        Game game = gameState.getData();

        mImageLoader.load(game.getCover(), R.drawable.placeholder, mGameCover);
        mGameTitle.setText(game.getTitle());
        mGameYear.setText(game.getYear());
        mGamePlatforms.setText(game.getPlatforms());

        for (Category category : game.getCategories()) {
            mCategoriesAdapter.add(LeaderBoardFragment.newInstance(category.getName(), category.getUrl()));
        }
        mCategoriesPager.setAdapter(mCategoriesAdapter);
        mCategoriesTabs.setupWithViewPager(mCategoriesPager);

        showContent();
    }

    @Override
    protected void onTryAgain() {
        mViewModel.load();
    }

}