package in.chundi.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by userhk on 15/07/17.
 */

public class WidgetShowRecipeListActivity extends AppCompatActivity {

    public static final String SHOW_RECIPE_LIST = "in.chundi.bakingapp.action.SHOW_RECIPE_LIST";
    public String TAG = WidgetShowRecipeListActivity.class.getSimpleName();

    public static void ShowRecipeList(Context context) {
        Intent intent = new Intent(context, ListViewWidgetService.class);
        intent.setAction(SHOW_RECIPE_LIST);
        context.startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Log.d(TAG, " in OnCreate");
        ShowRecipeList(this);

    }


}





