package in.chundi.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    String TAG = MainActivity.class.getSimpleName();
    JSONArray jsonResult;
    int no_of_recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /**
     * Called when the user taps the "Welcome to Baking Nirvana button button
     */
    public void showBakeRecipes(View view) {
        BakingRecipeAsynctask ba = new BakingRecipeAsynctask();
        ba.delegate = this;
        ba.execute(getString(R.string.json_url));
        Intent intent = new Intent(this, RecipeListActivity.class);
        if (jsonResult != null && no_of_recipes > 0) {
            intent.putExtra("no_of_items", no_of_recipes);
            startActivity(intent);
        } else
            Toast.makeText(this, "No Recipes received ", Toast.LENGTH_LONG).show();
    }


    @Override
    public void processFinish(JSONArray result) {
        jsonResult = result;
        if (result != null) {
            no_of_recipes = result.length();
            Log.i(TAG, "No of json items are " + no_of_recipes);
        } else
            Log.e(TAG, "No Json received");

    }
}





