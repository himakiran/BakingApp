package in.chundi.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by userhk on 02/07/17.
 */


public class RecipeViewHolder extends RecyclerView.ViewHolder {

    public ImageView listRecipeImageView;
    public TextView listRecipeTextView;

    public RecipeViewHolder(View recipeView) {
        super(recipeView);
        listRecipeImageView = (ImageView) recipeView.findViewById(R.id.recipe_item_image);
        listRecipeTextView = (TextView) recipeView.findViewById(R.id.recipe_item_title);

    }

}
