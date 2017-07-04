package in.chundi.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public RecipeDetailListFragment() {

    }

    // Inflates the GridView of all Recipe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bundle = getArguments();
        String jsonString = bundle.getString("jsonObject");
        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail_list_item, container, false);

        try {

            j = new JSONObject(jsonString);
            // Get a reference to all the views in the rootView
            textView = (TextView) rootView.findViewById(recipe_title);
            textView.setText(j.getString("name"));

            // Get the content of listDataHeader and listDatachild from the json object
            JSONArray ingredientsJsonArray = j.getJSONArray("ingredients");
            JSONObject temp;
            listDataHeader = new ArrayList<String>();
            childList = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                listDataHeader.add(i, "Name  :  " + ingredientsJsonArray.getJSONObject(i).getString("ingredient"));
                // assuming that ingredient object always will have three values namely quantity,
                // measure and ingredient
                temp = ingredientsJsonArray.getJSONObject(i);
                childList.add(0, "Quantity : " + temp.getString("quantity"));
                childList.add(1, "Measure  : " + temp.getString("measure"));

                listDataChild.put(ingredientsJsonArray.getJSONObject(i).getString("ingredient"), childList);
            }
            expListView = (ExpandableListView) rootView.findViewById(recipe_ingredients_list);
            listAdapter = new ExpandableIngredientListAdapter(getContext(), listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);

            Button b = (Button) rootView.findViewById(recipe_steps);
            b.setText("Steps to Make " + j.getString("name"));

            textView = (TextView) rootView.findViewById(recipe_servings);
            textView.setText("No of Servings : " + j.getString("servings"));


        } catch (JSONException je) {
            Log.d(TAG, je.toString());
        }

        // Return the root view
        return rootView;
    }
}
