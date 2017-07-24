package in.chundi.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    Bundle b;
    Bundle sa;
    RecipeMasterListFragment recipeListFragment;
    private boolean mTwoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.recipe_Grid) != null)
            mTwoPane = true;
        if (mTwoPane) {
            View v = findViewById(R.id.recipe_Grid);
            showBakeRecipes(v);
        }





    }

    /**
     * Called when the user taps the "Welcome to Baking Nirvana button button
     */
    public void showBakeRecipes(View view) {
        ba = new BakingRecipeAsynctask();
        ba.delegate = this;
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            ba.execute(getString(R.string.json_url));
        } else {
            Toast t = new Toast(this);
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void processFinish(JSONArray result) {
        jsonResult = result;
        b = new Bundle();
        b.putString("JArray", jsonResult.toString());
        b.putBoolean("isTablet", mTwoPane);
        b.putBoolean("sidePane", false); //

        if (jsonResult != null) {
            no_of_recipes = jsonResult.length();
            Log.i(TAG, "No of json items are " + no_of_recipes);


            // create and display RecipeListFragment
            //setContentView(R.layout.fragment_recipe_master_list);
            setContentView(R.layout.fragment_container);
            recipeListFragment = new RecipeMasterListFragment();
            recipeListFragment.setArguments(b);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, recipeListFragment, TAG)
                    .commit();


        } else {
            Log.e(TAG, "No Json received");
            Toast.makeText(this, R.string.NoRecpRecv, Toast.LENGTH_LONG).show();
        }

        }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            Log.d(TAG, "back pressed if");
            getFragmentManager().popBackStack();
        } else {
            Log.d(TAG, "Back pressed else");
            super.onBackPressed();
        }
    }



}





