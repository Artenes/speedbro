package io.github.artenes.speedbro.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.State;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        ViewPager mSectionsViewPager = findViewById(R.id.sections_pager);
        TabLayout mSectionsTabs = findViewById(R.id.sections_tabs);
        MainSectionsAdapter mSectionsAdapter = new MainSectionsAdapter(getSupportFragmentManager());

        //set up the tabs for the main screen
        mSectionsAdapter.add(getString(R.string.latest_runs), new RunsListWithMapFragment());
        mSectionsAdapter.add(getString(R.string.favorites), new FavoritesFragment());

        mSectionsViewPager.setAdapter(mSectionsAdapter);
        mSectionsTabs.setupWithViewPager(mSectionsViewPager);
    }

    /**
     * Receives a state and render it on the view
     *
     * @param state the state to render
     */
    @Override
    public void render(State state) {
        //renders nothing
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        setupSearchAction(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}