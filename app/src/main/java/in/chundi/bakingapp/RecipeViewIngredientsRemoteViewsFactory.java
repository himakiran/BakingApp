package in.chundi.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by userhk on 14/07/17.
 */

public class RecipeViewIngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String EXTRA_ITEM = "in.chundi.bakingapp.EXTRA_ITEM";
    private Context mContext;
    private ArrayList<String> records;
    private String TAG = RecipeViewIngredientsRemoteViewsFactory.class.getSimpleName();
    private int mAppWidgetId;
    private Intent mIntent;
    private String recipeName;


    public RecipeViewIngredientsRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        mIntent = intent;
        Log.d(TAG, "Constructor fired");
        recipeName = intent.getStringExtra(mContext.getString(R.string.wdgtRecpName));
        records = intent.getStringArrayListExtra("RECORDS");

    }

    @Override
    public void onCreate() {
        Log.d(TAG, "INSIDE " + "OnCreate");

//        jsonUrlString = mContext.getString(R.string.json_url);
//
//        fillRecords();



    }

//    private void fillRecords() {
//        ba = new BakingRecipeAsynctask();
//
//        ba.delegate = this;
//        ConnectivityManager cm =
//                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//        if (isConnected) {
//            Log.d(TAG, "Internet available");
//            ba.execute(jsonUrlString);
//        } else {
//            Log.d(TAG, "No internet connectivity");
//        }
//    }

    @Override
    public void onDataSetChanged() {
        //Log.d(TAG, "INSIDE " + "onDataSetChanged");
//        fillRecords();

    }

//    @Override
//    public void processFinish(JSONArray result) {
//        jsonResult = result;
//        JSONObject j;
//        JSONObject ing;
//        JSONArray ingArray;
//        if (jsonResult != null) {
//            records = new ArrayList<String>(jsonResult.length());
//            try {
//                for (int i = 0; i < jsonResult.length(); i++) {
//                    j = result.getJSONObject(i);
//                    if (j.getString("name").equals(recipeName)) {
//
//                        ingArray = j.getJSONArray("ingredients");
//                        for (int k = 0; k < ingArray.length(); k++) {
//                            ing = ingArray.getJSONObject(k);
//                            Log.d(TAG, ing.getString("ingredient"));
//                            records.add(k, ing.getString("ingredient"));
//                        }
//                    }
//                }
//                //Log.d(TAG, records.toString());
//            } catch (JSONException je) {
//                Log.d(TAG, je.toString());
//            }
//        }
//    }

    @Override
    public void onDestroy() {
        if (!(records == null))
        records.clear();

    }

    @Override
    public int getCount() {
        if (!(records == null)) {
            Log.d(TAG, "Size of Records : " + records.size());
            return records.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Log.d(TAG, "getViewAt called");
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);
        String data = records.get(position);
        rv.setTextViewText(R.id.ingredient_wname, data);
        rv.setViewVisibility(R.id.ingredient_wname, View.VISIBLE);
        rv.setViewVisibility(R.id.empty_ing_view, View.INVISIBLE);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
