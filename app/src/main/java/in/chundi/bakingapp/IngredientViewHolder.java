package in.chundi.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by userhk on 23/07/17.
 */

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    public TextView ingredientTitle;
    public LinearLayout lLExpandArea;
    public TextView childQty;
    public TextView childMeasure;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        ingredientTitle = (TextView) itemView.findViewById(R.id.lblListHeader);
        lLExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);
        childQty = (TextView) itemView.findViewById(R.id.childQty);
        childMeasure = (TextView) itemView.findViewById(R.id.childMeasure);

    }
}
