package in.chundi.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by userhk on 02/07/17.
 */


public class StepsViewHolder extends RecyclerView.ViewHolder {


    public TextView stepsShortDescTextView;

    public StepsViewHolder(View recipeView) {
        super(recipeView);

        stepsShortDescTextView = (TextView) recipeView.findViewById(R.id.StepsShortDesc);

    }

}
