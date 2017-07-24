package in.chundi.bakingapp;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by userhk on 12/07/17.
 */

public class RecipeWidgetProvider extends AppWidgetProvider implements AsyncResponse {


    public static final String SHOW_INGRED = "in.chundi.bakingapp.SHOW_INGRED";
    public static String TAG = RecipeWidgetProvider.class.getSimpleName();
    private static AppWidgetManager mgr;
    private static Context mContext;
    private static ArrayList<String> records_recipes;
    private static ArrayList<String> records_ingredients;
    private static String jsonUrlString;
    private static JSONArray jsonResult;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Get current width to decide on single icon vs recipe grid view
        mgr = appWidgetManager;
        mContext = context;
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int height = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        RemoteViews rv = null;
        if ((width <= 145) && (height <= 291)) {
            Log.d(TAG, "WIDTH IS : " + width);
            rv = showSimpleIconRemoteView(context);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_List);

        } else {
            Log.d(TAG, "WIDTH IS : " + width);
            Log.d(TAG, "app widget id is " + appWidgetId);

            rv = showRecipeListRemoteView(context, appWidgetId, records_recipes);
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

    private static RemoteViews showRecipeListRemoteView(Context context, int appWidgetId, ArrayList<String> records) {

        Log.d(TAG, "INSIDE show Recipe List Remote View");
        Intent intent = new Intent(context, ListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        Log.d(TAG, "RECORDS_RECIPES IS " + records_recipes);
        intent.putStringArrayListExtra("RECORDS", records_recipes);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list);
        views.setRemoteAdapter(appWidgetId, R.id.recipe_List, intent);
        views.setEmptyView(R.id.recipe_List, R.id.empty_view);
//        Intent intent1 = new Intent(context, RecipeWidgetProvider.class);
//        intent1.setData(Uri.parse(intent1
//                .toUri(Intent.URI_INTENT_SCHEME)));
//        final PendingIntent pendingIntent = PendingIntent
//                .getBroadcast(context, 0, intent1,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setPendingIntentTemplate(R.id.recipe_List,
//                pendingIntent);
        Intent clickIntentTemplate = new Intent(context, RecipeWidgetProvider.class);
        PendingIntent clickPendingIntentTemplate = PendingIntent
                .getBroadcast(context, 0, clickIntentTemplate,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.recipe_List, clickPendingIntentTemplate);

        return views;
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context,
                                          AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        Log.d(TAG, "Changed dimensions");

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId,
                newOptions);

        // Obtain appropriate widget and update it.
        updateAppWidget(context, appWidgetManager, appWidgetId);


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


        BakingRecipeAsynctask ba = new BakingRecipeAsynctask();
        ba.delegate = this;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        jsonUrlString = context.getString(R.string.json_url);
        if (isConnected) {
            Log.d(TAG, "Internet available");
            ba.execute(jsonUrlString);
        } else {
            Log.d(TAG, "No internet connectivity");
        }

    }


    @Override
    public void processFinish(JSONArray result) {
        jsonResult = result;
        JSONObject j;
        if (jsonResult != null) {
            records_recipes = new ArrayList<String>(jsonResult.length());
            try {
                for (int i = 0; i < jsonResult.length(); i++) {
                    j = result.getJSONObject(i);
                    records_recipes.add(i, j.getString(mContext.getString(R.string.name)));
                }
                Log.d(TAG, records_recipes.toString());
            } catch (JSONException je) {
                Log.d(TAG, je.toString());
            }


        }
    }





    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }




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
            onEnabled(context);
            try {
                JSONObject j, ing;
                JSONArray ingArray;
                for (int i = 0; i < jsonResult.length(); i++) {
                    j = jsonResult.getJSONObject(i);
                    if (j.getString("name").equals(recipeName)) {

                        ingArray = j.getJSONArray("ingredients");
                        records_ingredients = new ArrayList<String>(ingArray.length());
                        for (int k = 0; k < ingArray.length(); k++) {
                            ing = ingArray.getJSONObject(k);
                            Log.d(TAG, ing.getString("ingredient"));
                            records_ingredients.add(k, ing.getString("ingredient"));
                        }
                    }
                }
                //Log.d(TAG, records.toString());
            } catch (JSONException je) {
                Log.d(TAG, je.toString());
            }
            svcIntent.putStringArrayListExtra("RECORDS", records_ingredients);
            svcIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            rv.setRemoteAdapter(R.id.ingredient_List, svcIntent);
            rv.setEmptyView(R.id.ingredient_List, R.id.empty_ing_view);
            AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, rv);

        }


    }
}

