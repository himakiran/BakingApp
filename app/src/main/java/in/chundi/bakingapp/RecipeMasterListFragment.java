package in.chundi.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
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
import static in.chundi.bakingapp.R.layout.fragment_recipe_master_list;


/**
 * Created by userhk on 02/07/17.
 * code used and modified from https://guides.codepath.com/android/using-the-recyclerview
 */

public class RecipeMasterListFragment extends Fragment {

    private static final int SPAN_COUNT = 2;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    public String TAG = RecipeMasterListFragment.class.getSimpleName();
    //    LinearLayoutManager LLayoutManager;
    protected RecyclerView mRecyclerView;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;
    private JSONArray j;
    private Bundle bundle;
    private GetRecipes gr;
    private Boolean mTwoPane;
    private Boolean sidePane;
    private int scrollPosition;

    public RecipeMasterListFragment() {


    }

    // Inflates the GridView of all Recipe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        bundle = getArguments();
        mTwoPane = bundle.getBoolean("isTablet");
        sidePane = bundle.getBoolean("sidePane");

        try {
            j = new JSONArray(bundle.getString("JArray"));
        } catch (JSONException je) {
            Log.d(TAG, je.toString());
        }
        // use GetRecipes method to get recipes array list
        gr = new GetRecipes(this.getContext(), j);
        ArrayList<Recipe> arrayList = gr.getRecipeArrayList();

        View rootView = inflater.inflate(fragment_recipe_master_list, container, false);
        // This fragment is called from main activity from a phone layout.
        if (!mTwoPane && !sidePane) {

            rootView.setTag(TAG);

            // Get a reference to the GridView in the fragment_recipe_master_list xml layout file
            mRecyclerView = (RecyclerView) rootView.findViewById(recipes);


            // Create the adapter
            // This adapter takes in the context and an ArrayList of ALL the recipe image resources to display


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
                scrollPosition = mRecyclerView.getLayoutManager()
                        .getPosition(mRecyclerView);

            }
            setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
            mRecyclerView.scrollToPosition(scrollPosition);



        } // This fragment called from main activity from tablet layout
        else if (mTwoPane && !sidePane) {

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipes_tablet);
            Log.d(TAG, "reached here");
            mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
            if (savedInstanceState != null) {
                // Restore saved layout manager type.
                mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                        .getSerializable(KEY_LAYOUT_MANAGER);
            }


            scrollPosition = 0;

            // If a layout manager has already been set, get current scroll position.
            if (mRecyclerView.getLayoutManager() != null) {
                scrollPosition = mRecyclerView.getLayoutManager()
                        .getPosition(mRecyclerView);
            }
            mLayoutManager = new GridLayoutManager(getActivity(), 3);

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.scrollToPosition(scrollPosition);

        } // This fragment called from side pane of tablet
        else if (mTwoPane && sidePane) {

            rootView = inflater.inflate(R.layout.fragment_recipe_master_list_sidepane, container, false);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipes_side_pane);
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
            if (savedInstanceState != null) {
                // Restore saved layout manager type.
                mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                        .getSerializable(KEY_LAYOUT_MANAGER);
            }
            scrollPosition = 0;

            // If a layout manager has already been set, get current scroll position.
            if (mRecyclerView.getLayoutManager() != null) {
                scrollPosition = mRecyclerView.getLayoutManager()
                        .getPosition(mRecyclerView);
            }

            setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
            mRecyclerView.scrollToPosition(scrollPosition);


        }



        mRecyclerView.setHasFixedSize(false);

        RecipeListAdapter rAdapter = new RecipeListAdapter(this.getContext(), arrayList);


        mRecyclerView.setAdapter(rAdapter);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        try {
                            Log.d(TAG, "POSITION  IS " + position);
                            JSONObject jsonObject = j.getJSONObject(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("jsonObject", jsonObject.toString());
                            bundle.putBoolean("isTablet", mTwoPane);
                            bundle.putBoolean("sidePane", sidePane);
                            bundle.putString("JArray", j.toString());

                            //getActivity().setContentView(R.layout.fragment_recipe_detail_list);

                            getActivity().setContentView(R.layout.fragment_container);
                            RecipeDetailListFragment recipeDetailListFragment = new RecipeDetailListFragment();
                            recipeDetailListFragment.setArguments(bundle);
                            FragmentManager fg = getActivity().getSupportFragmentManager();
                            fg.beginTransaction()
                                    .replace(R.id.fragment_container, recipeDetailListFragment)
                                    .addToBackStack(null)
                                    .commit();

                        } catch (JSONException je) {
                            Log.d(TAG, je.toString());
                        }

                    }
                }

        );

        //Log.d(TAG, getActivity().toString());


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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);

    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
}
