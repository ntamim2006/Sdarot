
package com.nadav.sdarot;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Fragment_main extends Fragment implements Button.OnClickListener {


    LinearLayout Button12 ,buttonRank;

    public Fragment_main() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }





    public void onClick(View v){
        //Nothing here yet

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

//    public void makeTextLong(String s){
//        Context context = getActivity();
//        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
//
//    }








}
