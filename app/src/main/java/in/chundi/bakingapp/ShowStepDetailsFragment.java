package in.chundi.bakingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static in.chundi.bakingapp.R.id.playerView;

/**
 * Created by userhk on 23/07/17.
 */

public class ShowStepDetailsFragment extends Fragment {

    Button nextBtn, prevBtn;
    private ArrayList<String> childSteps;
    private Bundle bundle;
    private String TAG = ShowStepDetailsFragment.class.getSimpleName();
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Uri mediaUri;
    private int TotalSteps;
    private int currentStep;
    private HashMap<String, ArrayList<String>> listDataChild;
    private ArrayList<String> listDataHeader;


    public ShowStepDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        bundle = getArguments();
        childSteps = bundle.getStringArrayList("stepsLIST");
        TotalSteps = bundle.getInt("NoOfSteps");
        currentStep = bundle.getInt("currentPos");
        listDataChild = (HashMap<String, ArrayList<String>>) bundle.getSerializable("hashMap");
        listDataHeader = bundle.getStringArrayList("dataHeader");

        return getRootView(inflater, container, childSteps);

    }


    private void refreshView(LayoutInflater inflater, ViewGroup container, int currentStep) {
        childSteps = listDataChild.get(listDataHeader.get(currentStep));
        View newRootView;
        newRootView = getRootView(inflater, container, childSteps);
        // disabling the back button
        // user uses prev and next button to navigate steps instead of back button
        newRootView.setFocusableInTouchMode(true);
        newRootView.requestFocus();
        newRootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        getActivity().setContentView(newRootView);

    }

    private View getRootView(final LayoutInflater inflater, final ViewGroup container, ArrayList<String> childSteps) {

        this.childSteps = childSteps;
        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps_list_child_items, container, false);
        rootView.setTag(TAG);

        GetDescVidThumbnailFromSteps gd = new GetDescVidThumbnailFromSteps(childSteps);
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.welcome);

        TextView txtListChild = (TextView) rootView
                .findViewById(R.id.StepsListLongDesc);

        txtListChild.setText(gd.getLongDesc());
        //Log.d(TAG, gd.getLongDesc());

        final String urlText = gd.getVideoUrl();
        Log.i(TAG, urlText);
        final String thumbNaiUrl = gd.getThumbnai();
        Log.i(TAG, thumbNaiUrl);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.thumbnailVideo);
        if (imageView != null) {
            if (!(thumbNaiUrl.equals(""))) {
                Picasso.with(getContext()).load(thumbNaiUrl).fit().into(imageView);
                imageView.setVisibility(View.VISIBLE);
            }
        }
        // Initialize the player view.
        Log.i(TAG, "setting mPlayerView");
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(playerView);

        if (!urlText.equals("")) {

            mPlayerView.requestFocus();
            mPlayerView.setUseArtwork(true);
            if (!thumbNaiUrl.equals("")) {

                Uri imageUri = Uri.parse(thumbNaiUrl);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
                mPlayerView.setDefaultArtwork(bitmap);
            }

            //Picasso.with(mContext).load(Uri.parse(thumbNaiUrl)).into(mPlayerView);
            //Picasso.with(mContext).load(R.drawable.bakingthumb).into(imageView);

            //Log.d(TAG, "URI IS : " + Uri.parse(urlText).toString());
            mediaUri = Uri.parse(urlText);
            initializePlayer(mediaUri);
        } else {
            Log.d(TAG, "setting No url hence No mPlayerView");
            mPlayerView.setVisibility(View.GONE);
        }

        nextBtn = (Button) rootView.findViewById(R.id.nextBtn);
        prevBtn = (Button) rootView.findViewById(R.id.prevBtn);

        TotalSteps = listDataHeader.size();
        Log.d(TAG, "Total steps are " + TotalSteps);

        Log.d(TAG, " Current step is " + currentStep);
        toggleButtons();
        listDataChild = (HashMap<String, ArrayList<String>>) bundle.getSerializable("hashMap");
        listDataHeader = bundle.getStringArrayList("dataHeader");

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentStep += 1;
                // enableDisableButtons();
                if (mExoPlayer != null)
                    releasePlayer();
                toggleButtons();
                Log.d("next btn bkstck ", "" + getActivity().getSupportFragmentManager().getBackStackEntryCount());
                if (currentStep != TotalSteps)
                    refreshView(inflater, container, currentStep);


            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStep != 0)
                    currentStep -= 1;
                if (mExoPlayer != null)
                    releasePlayer();
                toggleButtons();
                refreshView(inflater, container, currentStep);


            }
        });


        return rootView;

    }

    private void toggleButtons() {
        if (currentStep == (TotalSteps - 1)) {
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(true);
        } else if (currentStep == 0) {
            prevBtn.setEnabled(false);
            nextBtn.setEnabled(true);
        } else if (currentStep >= 1 && currentStep <= TotalSteps) {
            nextBtn.setEnabled(true);
            prevBtn.setEnabled(true);
        }

    }



    /**
     * +     * Initialize ExoPlayer.
     * +     * @param mediaUri The URI of the sample to play.
     * +
     */
    private void initializePlayer(Uri mediaUri) {

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            Log.d(TAG, "mExoplayer initialization");
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setVisibility(View.VISIBLE);
            mPlayerView.setPlayer(mExoPlayer);
            String useragent = Util.getUserAgent(getContext(), getContext().getString(R.string.app_name));

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultHttpDataSourceFactory(useragent),
                    new DefaultExtractorsFactory(), null, null);
            Log.d(TAG, mediaSource.toString());
            mExoPlayer.prepare(mediaSource);

//                mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


            mExoPlayer.setPlayWhenReady(true);

        }

    }

    /**
     * +     * Release ExoPlayer.
     * +
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(mediaUri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer(mediaUri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


}
