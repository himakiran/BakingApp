package in.chundi.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.json.JSONArray;

import java.util.ArrayList;

import static in.chundi.bakingapp.RecipeWidgetProvider.SHOW_INGRED;

/**
 * Created by userhk on 14/07/17.
 */

public class ListViewWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private Context mContext;
    private ArrayList<String> records;
    private String jsonUrlString;
    private BakingRecipeAsynctask ba;
    private JSONArray jsonResult;
    private String TAG = ListViewWidgetRemoteViewsFactory.class.getSimpleName();
    private int mAppWidgetId;
    private Intent mIntent;

    public ListViewWidgetRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        mIntent = intent;
        records = intent.getStringArrayListExtra("RECORDS");

        Log.d(TAG, "Records received " + records.toString());
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
//            //Log.d(TAG, "Internet available");
//            ba.execute(jsonUrlString);
//        } else {
//            Log.d(TAG, "No internet connectivity");
//        }
//    }

    @Override
    public void onDataSetChanged() {
        //Log.d(TAG, "INSIDE " + "onDataSetChanged");


    }



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
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        String data = records.get(position);
        rv.setTextViewText(R.id.recipe_wname, data);
        rv.setViewVisibility(R.id.recipe_wname, View.VISIBLE);
//        Intent fillInIntent = new Intent(mContext, RecipeViewIngredientsService.class);
//        fillInIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                mAppWidgetId);
//        fillInIntent.putExtra(mContext.getString(R.string.wdgtRecpName), data);
//        fillInIntent.setAction(SHOW_INGRED);
//        rv.setOnClickFillInIntent(R.id.recipe_wname, fillInIntent);
        Intent fillInIntent = new Intent(mContext, RecipeWidgetProvider.class);
        fillInIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                mAppWidgetId);
        fillInIntent.putExtra(mContext.getString(R.string.wdgtRecpName), data);
        fillInIntent.setAction(SHOW_INGRED);
        rv.setOnClickFillInIntent(R.id.widget_item_layout, fillInIntent);

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
