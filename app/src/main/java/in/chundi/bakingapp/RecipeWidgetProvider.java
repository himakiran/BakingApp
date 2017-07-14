package in.chundi.bakingapp;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by userhk on 12/07/17.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_ITEM = "in.chundi.bakingapp.EXTRA_ITEM";
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
        } else {
            Log.d(TAG, "WIDTH IS : " + width);
            Log.d(TAG, "Show Recipe List Remote View");
            rv = showRecipeListRemoteView(context);

        }
        appWidgetManager.updateAppWidget(appWidgetId, rv);


    }

    private static RemoteViews showSimpleIconRemoteView(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setOnClickPendingIntent(R.id.widget_recipe_image, pendingIntent);
        return views;

    }

    private static RemoteViews showRecipeListRemoteView(Context context) {

        Log.d(TAG, "INSIDE show Recipe Grid Remote View");
        Intent intent = new Intent(context, ListViewWidgetService.class);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list);
        views.setRemoteAdapter(R.id.recipe_List, intent);
        Intent startActivityIntent = new Intent(context, WidgetShowRecipeListActivity.class);
        PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.recipe_List, startActivityPendingIntent);
        views.setEmptyView(R.id.recipe_List, R.id.empty_view);

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int a = 0; a < appWidgetIds.length; a++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[a]);
        }
        ;

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
}