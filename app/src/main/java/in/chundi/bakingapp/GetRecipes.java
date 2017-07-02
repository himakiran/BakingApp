package in.chundi.bakingapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by userhk on 02/07/17.
 * This class implements a json parser to parse a received jsonarray and return an arraylist of Recipes
 */

public class GetRecipes {
    private JSONArray ja;
    private Context context;
    private ArrayList<Recipe> recipeArrayList;
    private String TAG = GetRecipes.class.getSimpleName();

    public GetRecipes(Context mcontext, JSONArray jsonArray) {
        context = mcontext;
        ja = jsonArray;

    }

    public ArrayList<Recipe> getRecipeArrayList() {
        int num = ja.length();
        Recipe r = new Recipe(context);
        recipeArrayList = new ArrayList<Recipe>(num);
        JSONObject j;
        try {
            for (int i = 0; i < num; i++) {
                j = ja.getJSONObject(i);
                r.setRecipeId(j.getInt("id"));
                r.setRecipeName(j.getString("name"));
                r.setRecipeIngredients(j.getJSONArray("ingredients"));
                r.setRecipeNoOfSteps(j.getJSONArray("steps").length());
                r.setRecipeSteps(j.getJSONArray("steps"));
                r.setRecipeImg(j.getString("image"));
                recipeArrayList.add(i, r);
            }
        } catch (JSONException je) {
            Log.d(TAG, je.toString());
        }
        return recipeArrayList;
    }


}
