package in.chundi.bakingapp;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by userhk on 03/07/17.
 * Used code from : http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */

public class ExpandableStepsAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private List<String> listStepsHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listStepsChild;
    private String TAG = ExpandableStepsAdapter.class.getSimpleName();

    public ExpandableStepsAdapter(Context context, List<String> listDataHeader,
                                  HashMap<String, List<String>> listChildData) {


        this.mContext = context;
        this.listStepsHeader = listDataHeader;
        this.listStepsChild = listChildData;
        //Log.d(TAG, listChildData.toString());
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        //Log.d(TAG, "Child returned is " + this.listStepsChild.get(this.listStepsHeader.get(groupPosition)));
        //.get(childPosititon).toString());
        Log.d(TAG, " Group Possn : " + groupPosition + " child Posn : " + childPosititon + "  ;");
        Log.d(TAG, " : LIST STPS HDR : " + this.listStepsHeader.get(groupPosition));
        Log.d(TAG, " : LIST STPS CHD : " + this.listStepsChild.get(this.listStepsHeader.get(groupPosition)));
        Log.d(TAG, " -------------- ");
        return this.listStepsChild.get(this.listStepsHeader.get(groupPosition));
        // .get(childPosititon);


    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ArrayList<String> childText = (ArrayList<String>) getChild(groupPosition, childPosition);
        //Log.d(TAG, "CHILDTEXT : " + childText.toString());
        if (convertView == null) {
            Log.d(TAG, "CONVERT VIEW IS NULL");
            Log.d(TAG, "PARENT VIEW GROUP IS : " + parent.toString());
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_recipe_steps_list_child_items, null);
        }

        GetDescVidThumbnailFromSteps gd = new GetDescVidThumbnailFromSteps(childText);

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.StepsListLongDesc);

        txtListChild.setText(gd.getLongDesc());
        //Log.d(TAG, gd.getLongDesc());

        final String urlText = gd.getVideoUrl();

        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) convertView.findViewById(R.id.playerView);

        //Picasso.with(mContext).load(R.drawable.bakingthumb).into(imageView);

        //Log.d(TAG, "URI IS : " + Uri.parse(urlText).toString());
        initializePlayer(Uri.parse(urlText));


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.d(TAG, ("No of Children  Of " + this.listStepsHeader.get(groupPosition) + " : " + this.listStepsChild.size()));
        //return this.listIngredientChild.size();//.get(this.listIngredientHeader.get(groupPosition))
//        return this.listStepsChild.get(this.listStepsHeader.get(groupPosition))
//                .size();
        // Because every step with a short description has only one child that has three fields namely long description,
        // video url and thumbnail url.
        return 1;

    }

    /**
     * +     * Initialize ExoPlayer.
     * +     * @param mediaUri The URI of the sample to play.
     * +
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(mContext, "bakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(mContext, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * +     * Release ExoPlayer.
     * +
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }




    @Override
    public Object getGroup(int groupPosition) {
        return this.listStepsHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listStepsHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        //Log.d(TAG, headerTitle);
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
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
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
