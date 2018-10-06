package io.github.artenes.speedbro.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import io.github.artenes.speedbro.R;
import io.github.artenes.speedbro.views.RunActivity;

/**
 * Implementation of App Widget functionality.
 */
public class LatestRunsWidgetProvider extends AppWidgetProvider {

    public static final String OPEN_RUN_ACTION = "io.github.artenes.speedbro.OPEN_RUN_ACTION";
    public static final String EXTRA_GAME_ID = "io.github.artenes.speedbro.EXTRA_GAME_ID";
    public static final String EXTRA_RUN_ID = "io.github.artenes.speedbro.EXTRA_RUN_ID";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Here we setup the intent which points to the StackViewService which will
        // provide the views for this collection.
        Intent intent = new Intent(context, StackWidgetService.class);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.latest_runs_widget);
        rv.setRemoteAdapter(R.id.stack_view, intent);

        // The empty view is displayed when the collection has no items. It should be a sibling
        // of the collection view.
        rv.setEmptyView(R.id.stack_view, R.id.empty_view);

        // Here we setup the a pending intent template. Individuals items of a collection
        // cannot setup their own pending intents, instead, the collection as a whole can
        // setup a pending intent template, and the individual items can set a fillInIntent
        // to create unique before on an item to item basis.
        Intent toastIntent = new Intent(context, LatestRunsWidgetProvider.class);
        toastIntent.setAction(LatestRunsWidgetProvider.OPEN_RUN_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(OPEN_RUN_ACTION)) {
            String gameId = intent.getStringExtra(EXTRA_GAME_ID);
            String runId = intent.getStringExtra(EXTRA_RUN_ID);
            RunActivity.start(context, gameId, runId);
        }
        super.onReceive(context, intent);
    }

}