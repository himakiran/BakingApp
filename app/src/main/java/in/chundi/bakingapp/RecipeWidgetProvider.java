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

/**
 * Created by userhk on 12/07/17.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {


    public static final String SHOW_INGRED = "in.chundi.bakingapp.SHOW_INGRED";
    public static String TAG = RecipeWidgetProvider.class.getSimpleName();
    private static AppWidgetManager mgr;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Get current width to decide on single icon vs recipe grid view
        mgr = appWidgetManager;
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
            //Log.d(TAG, "Received views " + rv.toString());
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

        Log.d(TAG, "INSIDE show Recipe List Remote View");
        Intent intent = new Intent(context, ListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list);
        views.setRemoteAdapter(appWidgetId, R.id.recipe_List, intent);
        views.setEmptyView(R.id.recipe_List, R.id.empty_view);
        Intent intent1 = new Intent(context, RecipeWidgetProvider.class);
        intent1.setData(Uri.parse(intent1
                .toUri(Intent.URI_INTENT_SCHEME)));
        final PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context, 0, intent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.recipe_List,
                pendingIntent);


        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int a = 0; a < appWidgetIds.length; a++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[a]);
        }


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

    //    private RemoteViews updateWidgetListView(Context context, int appWidgetId, String recipeName) {
//
//        Log.d(TAG, "inside updtWdgtLstView");
//        // which layout to show on widget
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
//                R.layout.widget_list);
//        //context.startService(svcIntent);
//        return remoteViews;
//    }
    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);

        if (intent.getAction().equals(SHOW_INGRED)) {
            Log.d(TAG, "RECEIVED INTENT");
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            String recipeName = intent.getStringExtra("widget_recipe_name");
            Log.d(TAG, "appWIDGETid is " + appWidgetId);
            Log.d(TAG, " Recipe name is " + recipeName);
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.ingredient_list);
            Intent svcIntent = new Intent(context, RecipeViewIngredientsService.class);
            svcIntent.putExtra(context.getString(R.string.wdgtRecpName), recipeName);
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            svcIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            rv.setRemoteAdapter(R.id.ingredient_List, svcIntent);
            rv.setEmptyView(R.id.ingredient_List, R.id.empty_ing_view);
            AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, rv);

        }


    }
}

