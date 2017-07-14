package in.chundi.bakingapp;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by userhk on 12/07/17.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_ITEM = "in.chundi.bakingapp.EXTRA_ITEM";
    public static final String SHOW_INGRED = "in.chundi.bakingapp.SHOW_INGRED";
    public static String TAG = RecipeWidgetProvider.class.getSimpleName();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Get current width to decide on single icon vs recipe grid view
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews rv;
        if (width < 100) {
            Log.d(TAG, "WIDTH IS : " + width);
            rv = showSimpleIconRemoteView(context);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_List);

        } else {
            Log.d(TAG, "WIDTH IS : " + width);
            Log.d(TAG, "Show Recipe List Remote View");
            rv = showRecipeListRemoteView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_List);
            Log.d(TAG, "Received views " + rv.toString());
        }



    }

    private static RemoteViews showSimpleIconRemoteView(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setOnClickPendingIntent(R.id.widget_recipe_image, pendingIntent);
        return views;

    }

    private static RemoteViews showRecipeListRemoteView(Context context, int appWidgetId) {

        Log.d(TAG, "INSIDE show Recipe Grid Remote View");
        Intent intent = new Intent(context, ListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list);
        views.setRemoteAdapter(appWidgetId, R.id.recipe_List, intent);
        views.setEmptyView(R.id.recipe_List, R.id.empty_view);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int a = 0; a < appWidgetIds.length; a++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[a]);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated

    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context,
                                          AppWidgetManager appWidgetManager,
                                          int appWidgetId,
                                          Bundle newOptions) {
        updateAppWidget(context, appWidgetManager, appWidgetId);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(SHOW_INGRED)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            String recipe_name = intent.getStringExtra("widget_recipe_name");
            Toast.makeText(context, "Touched view " + recipe_name + "@ pos : " + viewIndex, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
}
