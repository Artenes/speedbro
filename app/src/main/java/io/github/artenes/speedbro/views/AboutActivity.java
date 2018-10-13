package io.github.artenes.speedbro.views;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.utils.Utils;

/**
 * Activity to display about information
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //setup version name
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = info.versionName;
            ((TextView) findViewById(R.id.app_version)).setText(getString(R.string.app_version, versionName));
        } catch (PackageManager.NameNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void openWebsite(View view) {
        Utils.startViewIntent(getString(R.string.website_url), this);
    }

    public void openRepo(View view) {
        Utils.startViewIntent(getString(R.string.repo_url), this);
    }

}