package com.nadav.sdarot.DrawerFragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.nadav.sdarot.R;

import java.io.File;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Created by Nadav on 08/03/2016.
 */
public class SyncDriveFragment extends Fragment implements Button.OnClickListener {
    private Button mButton; //Add at the top of the fragment

    public SyncDriveFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_syncdrive, container, false);
        changeActionBarPicture(R.string.sync);
//        mButton = (Button) rootView.findViewById(R.id.button3);
//        mButton.setOnClickListener(this);


        return rootView;
    }

    public void onClick(View v){
        //Nothing here yet

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void changeActionBarPicture(int title) {

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(getString(title));
        LayoutInflater inflater2 = (LayoutInflater) actionBar.getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater2.inflate(R.layout.layout_fragment_sync, null);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        actionBar.setCustomView(customActionBarView, layoutParams);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_TITLE);

    }

}