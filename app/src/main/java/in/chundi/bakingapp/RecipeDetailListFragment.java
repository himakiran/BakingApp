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
import android.widget.Button;
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
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    protected RecyclerView mRecyclerView;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;
    TextView textView;
    RecipeIngredientListAdapter listAdapter;
    ArrayList<String> listDataHeader;
    ArrayList<String> childList;
    HashMap<String, List<String>> listDataChild;
    private Bundle bundle;
    private JSONObject j;
    private String TAG = RecipeDetailListFragment.class.getSimpleName();
    private Boolean mTwoPane;
    private Boolean sidePane;
    private int scrollPosition;
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
        rootView.setTag(TAG);

        try {

            j = new JSONObject(jsonString);
            // Get a reference to all the views in the rootView
            textView = (TextView) rootView.findViewById(recipe_title);
            textView.setText(j.getString(getString(R.string.name)));
            //Log.d(TAG, textView.getText().toString());
            Button b = (Button) rootView.findViewById(recipe_steps);
            b.setText(getString(R.string.STEPS));
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
            textView.setText(getString(R.string.NumOfServings) + j.getString("servings"));
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

                childList.add(0, getString(R.string.Qty) + temp.getString("quantity"));
                childList.add(1, getString(R.string.Msr) + temp.getString("measure"));

                //Log.d(TAG, childList.toString());
                listDataChild.put(ingredientsJsonArray.getJSONObject(i).getString("ingredient"), childList);
                childList = null;
                }
            mRecyclerView = (RecyclerView) rootView.findViewById(recipe_ingredients_list);

            // Here we r displaying the recycler view in a linear layout fashion
            mLayoutManager = new LinearLayoutManager(getActivity());
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

            if (savedInstanceState != null) {
                // Restore saved layout manager type.
                mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                        .getSerializable(KEY_LAYOUT_MANAGER);
            }
            /*
            Comments : Required :
            It is required that you restore the position of the recycler view post rotation.

            Note: I had already implemented for two cases below but forgot to do that here..
            now done.
             */
            scrollPosition = 0;
            if (mRecyclerView.getLayoutManager() != null) {
                scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                        .getPosition(mRecyclerView);

            }
            setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
            mRecyclerView.scrollToPosition(scrollPosition);

            listAdapter = new RecipeIngredientListAdapter(getContext(), listDataHeader, listDataChild);
            mRecyclerView.setAdapter(listAdapter);

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

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }


        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

}
