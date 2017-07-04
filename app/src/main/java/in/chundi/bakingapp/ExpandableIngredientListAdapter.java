package in.chundi.bakingapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by userhk on 03/07/17.
 * Used code from : http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */

public class ExpandableIngredientListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> listIngredientHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listIngredientChild;

    public ExpandableIngredientListAdapter(Context context, List<String> listDataHeader,
                                           HashMap<String, List<String>> listChildData) {


        this.mContext = context;
        this.listIngredientHeader = listDataHeader;
        this.listIngredientChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listIngredientChild.get(this.listIngredientHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_recipe_detail_ingredients_list_child_items, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listIngredientChild.get(this.listIngredientHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listIngredientHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listIngredientHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_recipe_detail_ingredients_list_group_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


//    @Override
//    public int getGroupCount() {
//        return 0;
//    }
//
//    @Override
//    public int getChildrenCount(int i) {
//        return 0;
//    }
//
//    @Override
//    public Object getGroup(int i) {
//        return null;
//    }
//
//    @Override
//    public Object getChild(int i, int i1) {
//        return null;
//    }
//
//    @Override
//    public long getGroupId(int i) {
//        return 0;
//    }
//
//    @Override
//    public long getChildId(int i, int i1) {
//        return 0;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    @Override
//    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
//        return null;
//    }
//
//    @Override
//    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
//        return null;
//    }
//
//    @Override
//    public boolean isChildSelectable(int i, int i1) {
//        return false;
//    }
}
