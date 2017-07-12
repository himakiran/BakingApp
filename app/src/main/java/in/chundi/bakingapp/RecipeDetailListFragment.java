package in.chundi.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static in.chundi.bakingapp.R.id.recipe_ingredients_list;
import static in.chundi.bakingapp.R.id.recipe_servings;
import static in.chundi.bakingapp.R.id.recipe_steps;
import static in.chundi.bakingapp.R.id.recipe_title;

/**
 * Created by userhk on 03/07/17.
 */

public class RecipeDetailListFragment extends Fragment {
    TextView textView;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> childList;
    HashMap<String, List<String>> listDataChild;
    private Bundle bundle;
    private JSONObject j;
    private String TAG = RecipeDetailListFragment.class.getSimpleName();
    private Boolean mTwoPane;
    private Boolean sidePane;
    public RecipeDetailListFragment() {

    }

    // Inflates the details layout of all Recipe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        bundle = getArguments();


        String jsonString = bundle.getString("jsonObject");
        mTwoPane = bundle.getBoolean("isTablet");
        sidePane = bundle.getBoolean("sidePane");


        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail_list_item, container, false);


        try {

            j = new JSONObject(jsonString);
            // Get a reference to all the views in the rootView
            textView = (TextView) rootView.findViewById(recipe_title);
            textView.setText(j.getString("name"));
            //Log.d(TAG, textView.getText().toString());
            Button b = (Button) rootView.findViewById(recipe_steps);
            b.setText("Steps");
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        JSONArray jsonArray = j.getJSONArray("steps");

                        Bundle bundle = new Bundle();
                        bundle.putString("jsonStepsArray", jsonArray.toString());
                        //getActivity().setContentView(R.layout.fragment_recipe_steps_list);
                        getActivity().setContentView(R.layout.fragment_container);
                        RecipeStepsListFragment recipeStepsListFragment = new RecipeStepsListFragment();
                        recipeStepsListFragment.setArguments(bundle);
                        FragmentManager fg = getActivity().getSupportFragmentManager();
                        fg.beginTransaction()
                                .replace(R.id.fragment_container, recipeStepsListFragment)
                                .addToBackStack(null)
                                .commit();

                    } catch (JSONException je) {
                        Log.d(TAG, je.toString());
                    }
                    }
            });
            //Log.d(TAG, b.getText().toString());
            textView = (TextView) rootView.findViewById(recipe_servings);
            textView.setText("No of Servings:" + j.getString("servings"));
            //Log.d(TAG, textView.getText().toString());

            // Get the content of listDataHeader and listDatachild from the json object
            JSONArray ingredientsJsonArray = j.getJSONArray("ingredients");
            //Log.d(TAG, ingredientsJsonArray.toString());
            JSONObject temp;
            listDataHeader = new ArrayList<String>();

            listDataChild = new HashMap<String, List<String>>();

            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                childList = new ArrayList<String>();
                listDataHeader.add(i, ingredientsJsonArray.getJSONObject(i).getString("ingredient"));
                // assuming that ingredient object always will have three values namely quantity,
                // measure and ingredient
                temp = ingredientsJsonArray.getJSONObject(i);

                childList.add(0, "Quantity : " + temp.getString("quantity"));
                childList.add(1, "Measure  : " + temp.getString("measure"));

                //Log.d(TAG, childList.toString());
                listDataChild.put(ingredientsJsonArray.getJSONObject(i).getString("ingredient"), childList);
                childList = null;
                }
            expListView = (ExpandableListView) rootView.findViewById(recipe_ingredients_list);
            listAdapter = new ExpandableIngredientListAdapter(getContext(), listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);

            if (mTwoPane) {

                // we want to call a new instance of the master list fragment to paint
                // us a linear card display of all recipes.
                RecipeMasterListFragment rmf = new RecipeMasterListFragment();

                bundle.putString("JArray", bundle.getString("JArray"));
                bundle.putBoolean("isTablet", true);
                bundle.putBoolean("sidePane", true);

                rmf.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.tabletContainer, rmf)
                        .addToBackStack(null)
                        .commit();
            }

        } catch (JSONException je) {
            Log.d(TAG, je.toString());
            }


        Log.d(TAG, getActivity().toString());
        // Return the root view
        return rootView;


    }


}
