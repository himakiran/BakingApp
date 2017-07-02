package in.chundi.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static in.chundi.bakingapp.R.id.recipes;

/**
 * Created by userhk on 01/07/17.
 */

public class RecipeListActivity extends AppCompatActivity {

    private int num_of_items;
    private String TAG = RecipeListActivity.class.getSimpleName();
    private RecipeListAdapter recipeListAdapter;
    private RecyclerView recyclerView;
    private Bundle b;
    private JSONArray j;
    private GetRecipes gr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //receive the intent from MainActivity and get the no of recipes
        Intent intent = getIntent();
        num_of_items = intent.getIntExtra("no_of_items", 0);
        Log.d(TAG, "No of Items received : " + num_of_items);
        // get the recipes list by getting the bundle and extracting the string and converting it
        // back into JsonArray
        b = intent.getExtras();
        try {
            j = new JSONArray(b.getString("JArray"));
        } catch (JSONException je) {
            Log.d(TAG, je.toString());
        }
        // use GetRecipes method to get recipes array list
        gr = new GetRecipes(this, j);
        ArrayList<Recipe> arrayList = gr.getRecipeArrayList();

        // set the view and attach the adapter to the recycler view
        setContentView(R.layout.activity_recipe_list);
        recyclerView = (RecyclerView) findViewById(recipes);
        // Here we r displaying the recycler view in a 2 column grid
        LinearLayoutManager LLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LLayoutManager);
        /*
            * Use this setting to improve performance if you know that changes in content do not
            * change the child layout size in the RecyclerView
         */
        recyclerView.setHasFixedSize(true);

        recipeListAdapter = new RecipeListAdapter(this, arrayList);

        recyclerView.setAdapter(recipeListAdapter);


    }
}
