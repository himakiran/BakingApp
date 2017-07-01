package in.chundi.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by userhk on 01/07/17.
 * This adapter ties the recycler view (activity_recipe_list.xml ) to recipe images
 * each of which will fill up the image view in activity_recipe_list_item.xml to the adapter
 * The adpater shall take a json object and retrieve images/thumbnails to show the recipe
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeListAdapter.class.getSimpleName();
    private int mRecipeItems;


    public RecipeListAdapter(int numOfRecipes) {
        mRecipeItems = numOfRecipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutIdForRecipeListItem = R.layout.activity_recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(LayoutIdForRecipeListItem, parent, shouldAttachToParentImmediately);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Log.d(TAG, "# " + position);
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mRecipeItems;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView listRecipeImageView;
        TextView listRecipeTextView;

        public RecipeViewHolder(View recipeView) {
            super(recipeView);

            listRecipeImageView = (ImageView) recipeView.findViewById(R.id.recipe_item_image);
            listRecipeTextView = (TextView) recipeView.findViewById(R.id.recipe_item_title);
        }

        void bind(int listIndex) {

            listRecipeImageView.setImageResource(R.drawable.welcome);
            // need to replace above with
            //listRecipeImageView.setImageResource(getImageOfRecipeItem())
            listRecipeTextView.setText("not yet set");
            // need to replace above with
            //listRecipeTextView.setText(getTitleOfRecipeItem())

        }


    }


}
