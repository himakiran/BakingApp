package in.chundi.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    String TAG = MainActivity.class.getSimpleName();
    JSONArray jsonResult;
    int no_of_recipes;
    BakingRecipeAsynctask ba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    /**
     * Called when the user taps the "Welcome to Baking Nirvana button button
     */
    public void showBakeRecipes(View view) {
        ba = new BakingRecipeAsynctask();
        ba.delegate = this;
        ba.execute(getString(R.string.json_url));

    }


    @Override
    public void processFinish(JSONArray result) {
        jsonResult = result;
        Bundle b = new Bundle();
        b.putString("JArray", jsonResult.toString());
        if (jsonResult != null) {
            no_of_recipes = jsonResult.length();
            Log.i(TAG, "No of json items are " + no_of_recipes);
//            Intent intent = new Intent(this, RecipeListActivity.class);
//            intent.putExtra("no_of_items", no_of_recipes);
//            intent.putExtras(b);
//
//            startActivity(intent);

            // create and display RecipeListFragment
            setContentView(R.layout.activity_recipe_list);
            RecipeListFragment recipeListFragment = new RecipeListFragment();
            recipeListFragment.setArguments(b);
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.container, recipeListFragment)
                    .commit();

        } else {
            Log.e(TAG, "No Json received");
            Toast.makeText(this, "No Recipe received ", Toast.LENGTH_LONG).show();
        }

    }
}





