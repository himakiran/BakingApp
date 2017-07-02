package in.chundi.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by userhk on 01/07/17.
 */

public class RecipeListActivity extends AppCompatActivity {

    private int num_of_items;
    private String TAG = RecipeListActivity.class.getSimpleName();
    private RecipeListAdapter recipeListAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //receive the intent from MainActivity and get the no of recipes
        Intent intent = getIntent();
        intent.getIntExtra("no_of_items", num_of_items);
        // set the view and attach the adapter to the recycler view
        setContentView(R.layout.activity_recipe_list);
        recyclerView = (RecyclerView) findViewById(R.id.recipes);
        // Here we r displaying the recycler view in a 2 column grid
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        /*
            * Use this setting to improve performance if you know that changes in content do not
            * change the child layout size in the RecyclerView
         */
        recyclerView.setHasFixedSize(true);

        recipeListAdapter = new RecipeListAdapter(num_of_items);

        recyclerView.setAdapter(recipeListAdapter);


    }
}
