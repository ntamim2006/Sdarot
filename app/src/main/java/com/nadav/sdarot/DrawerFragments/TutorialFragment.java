package com.nadav.sdarot.DrawerFragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nadav.sdarot.MainActivity;
import com.nadav.sdarot.R;


/**
 * Created by Nadav on 08/03/2016.
 */
public class TutorialFragment extends Fragment implements Button.OnClickListener {
    private Button mButton; //Add at the top of the fragment

    public TutorialFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);
        mButton = (Button) rootView.findViewById(R.id.button3);
        mButton.setOnClickListener(this);
        return rootView;
    }

    public void onClick(View v){
        //Nothing here yet

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



}