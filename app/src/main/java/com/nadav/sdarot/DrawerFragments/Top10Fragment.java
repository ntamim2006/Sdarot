package com.nadav.sdarot.DrawerFragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nadav.sdarot.AdapterTop10;
import com.nadav.sdarot.EpisodeClass;
import com.nadav.sdarot.MainActivity;
import com.nadav.sdarot.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nadav on 08/03/2016.
 */
public class Top10Fragment extends Fragment implements Button.OnClickListener {
    private Button mButton; //Add at the top of the fragment
    private OnFragmentInteractionListener mListener;
    ListView listview;

    public Top10Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top10, container, false);
        listview = (ListView)rootView.findViewById(R.id.listView2);
        List<EpisodeClass> episodes = new ArrayList<>();
        String[] names = this.getResources().getStringArray(R.array.names_arraylist);
        String[] img_resouces = this.getResources().getStringArray(R.array.image_arraylist);
        String[] plots = this.getResources().getStringArray(R.array.plots_arraylist);
        Double[] ranks = {8.6, 9.5, 8.7, 9.0, 8.6, 8.2, 8.0, 8.6, 7.9, 8.0};
        AdapterTop10 ap = new AdapterTop10(getActivity(), R.layout.row_layout);

        for(int j=0; j<10; j++) {

                EpisodeClass obj = new EpisodeClass(img_resouces[j], names[j], plots[j], ranks[j]);
                ap.add(obj);
            }
//        EpisodeClass obj = new EpisodeClass(img_resouces[0], names[0], plots[0]);
//        ap.add(obj);
        ap.setDropDownViewResource(R.layout.spinner_alarm);
        listview.setAdapter(ap);


        return rootView;
    }

    public void onClick(View v){
        //Nothing here yet
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        MainActivity.mDrawerLayout.setVisibility(View.INVISIBLE);
        MainActivity.goToFrame = false;

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



}