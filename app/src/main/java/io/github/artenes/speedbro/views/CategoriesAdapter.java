package io.github.artenes.speedbro.views;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display list of categories
 */
class CategoriesAdapter extends FragmentPagerAdapter {

    private final List<LeaderBoardFragment> fragments = new ArrayList<>();

    CategoriesAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(LeaderBoardFragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

}