package in.chundi.bakingapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by userhk on 02/07/17.
 */

public class Recipes {

    private String TAG = Recipes.class.getSimpleName();

    private int no_of_recipes;

    private JSONArray jsonArray;

    private Context mContext;

    private ArrayList<String> recipeNames;


    public Recipes(Context context) {
        mContext = context;
    }

    public int getNo_of_recipes() {
        return no_of_recipes;
    }

    public void setNo_of_recipes(int num) {
        no_of_recipes = num;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray ja) {
        jsonArray = ja;
    }

    public void setRecipenames() {
        try {
            for (int i = 0; i < no_of_recipes; i++) {
                recipeNames.add(i, jsonArray.getJSONObject(i).getString("name"));
            }
        } catch (JSONException j) {
            Log.e(TAG, j.toString());
        }
    }

}
