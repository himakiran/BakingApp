package in.chundi.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static in.chundi.bakingapp.R.id.recipes;


/**
 * Created by userhk on 02/07/17.
 * code used and modified from https://guides.codepath.com/android/using-the-recyclerview
 */

public class RecipeMasterListFragment extends Fragment {

    public String TAG = RecipeMasterListFragment.class.getSimpleName();
    LinearLayoutManager LLayoutManager;
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
        LLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(LLayoutManager);
        recyclerView.setHasFixedSize(true);

        RecipeListAdapter rAdapter = new RecipeListAdapter(this.getContext(), arrayList);


        recyclerView.setAdapter(rAdapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        try {
                            Log.d(TAG, "POSITION  IS " + position);
                            JSONObject jsonObject = j.getJSONObject(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("jsonObject", jsonObject.toString());
                            getActivity().setContentView(R.layout.fragment_recipe_detail_list);
                            RecipeDetailListFragment recipeDetailListFragment = new RecipeDetailListFragment();
                            recipeDetailListFragment.setArguments(bundle);
                            FragmentManager fg = getActivity().getSupportFragmentManager();
                            fg.beginTransaction()
                                    .add(R.id.recipe_container, recipeDetailListFragment)
                                    .commit();

                        } catch (JSONException je) {
                            Log.d(TAG, je.toString());
                        }

                    }
                }

        );

        Log.d(TAG, getActivity().toString());


        // Return the root view
        return rootView;


    }
}
