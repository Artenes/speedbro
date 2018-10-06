package io.github.artenes.speedbro.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Service used to generate remote view factory for widget
 */
public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}