package in.chundi.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by userhk on 01/07/17.
 */

public class RecipeListActivity extends AppCompatActivity {

    private int num_of_items;
    private String TAG = RecipeListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_list);

    }
}


//    // COMPLETED (1) Create a private static final int called NUM_LIST_ITEMS and set it equal to 100
//    private static final int NUM_LIST_ITEMS = 100;
//
//    // COMPLETED (2) Create a GreenAdapter variable called mAdapter
//    /*
//     * References to RecyclerView and Adapter to reset the list to its
//     * "pretty" state when the reset menu item is clicked.
//     */
//    private GreenAdapter mAdapter;
//    // COMPLETED (3) Create a RecyclerView variable called mNumbersList
//    private RecyclerView mNumbersList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // COMPLETED (4) Use findViewById to store a reference to the RecyclerView in mNumbersList
//        /*
//         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
//         * do things like set the adapter of the RecyclerView and toggle the visibility.
//         */
//        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);
//
//        // COMPLETED (5) Create a LinearLayoutManager variable called layoutManager
//        /*
//         * A LinearLayoutManager is responsible for measuring and positioning item views within a
//         * RecyclerView into a linear list. This means that it can produce either a horizontal or
//         * vertical list depending on which parameter you pass in to the LinearLayoutManager
//         * constructor. By default, if you don't specify an orientation, you get a vertical list.
//         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
//         * the LinearLayoutManager constructor.
//         *
//         * There are other LayoutManagers available to display your data in uniform grids,
//         * staggered grids, and more! See the developer documentation for more details.
//         */
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        // COMPLETED (6) Use setLayoutManager on mNumbersList with the LinearLayoutManager we created above
//        mNumbersList.setLayoutManager(layoutManager);
//
//        // COMPLETED (7) Use setHasFixedSize(true) to designate that the contents of the RecyclerView won't change an item's size
//        /*
//         * Use this setting to improve performance if you know that changes in content do not
//         * change the child layout size in the RecyclerView
//         */
//        mNumbersList.setHasFixedSize(true);
//
//        // COMPLETED (8) Store a new GreenAdapter in mAdapter and pass it NUM_LIST_ITEMS
//        /*
//         * The GreenAdapter is responsible for displaying each item in the list.
//         */
//        mAdapter = new GreenAdapter(NUM_LIST_ITEMS);
//
//        // COMPLETED (9) Set the GreenAdapter you created on mNumbersList
//        mNumbersList.setAdapter(mAdapter);
//    }
