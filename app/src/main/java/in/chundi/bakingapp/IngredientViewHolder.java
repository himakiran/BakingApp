package in.chundi.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by userhk on 23/07/17.
 */

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    public TextView recipeTitle;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        recipeTitle = (TextView) itemView.findViewById(R.id.lblListHeader);
    }
}
