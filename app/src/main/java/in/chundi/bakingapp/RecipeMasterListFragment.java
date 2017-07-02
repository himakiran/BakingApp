package in.chundi.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static in.chundi.bakingapp.R.id.recipes;


/**
 * Created by userhk on 02/07/17.
 */

public class RecipeMasterListFragment extends Fragment {

    private RecyclerView recyclerView;
    private JSONArray j;
    private Bundle bundle;
    private GetRecipes gr;

    public RecipeMasterListFragment() {

    }

    // Inflates the GridView of all Recipe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bundle = getArguments();
        try {
            j = new JSONArray(bundle.getString("JArray"));
        } catch (JSONException je) {
            Log.d(TAG, je.toString());
        }
        // use GetRecipes method to get recipes array list
        gr = new GetRecipes(this.getContext(), j);
        ArrayList<Recipe> arrayList = gr.getRecipeArrayList();

        final View rootView = inflater.inflate(R.layout.fragment_recipe_master_list, container, false);

        // Get a reference to the GridView in the fragment_recipe_master_list xml layout file
        recyclerView = (RecyclerView) rootView.findViewById(recipes);

        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the recipe image resources to display


        // Here we r displaying the recycler view in a linear layout fashion
        LinearLayoutManager LLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(LLayoutManager);
        recyclerView.setHasFixedSize(true);

        RecipeListAdapter rAdapter = new RecipeListAdapter(this.getContext(), arrayList);


        recyclerView.setAdapter(rAdapter);

        // Now we shall set a onClickListener on the imageview as well as textView so that when a user
        // clicks on a recipe image or text the RecipeDetailFragment is launched


        // Return the root view
        return rootView;
    }
}
