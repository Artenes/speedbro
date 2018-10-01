package io.github.artenes.speedbro.views;

import android.os.Bundle;
import android.view.Menu;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.models.State;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
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

}