package com.nadav.sdarot.DrawerFragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.nadav.sdarot.MainActivity;
import com.nadav.sdarot.R;

import java.net.URLEncoder;


/**
 * Created by Nadav on 08/03/2016.
 */
public class feedbackFragment extends Fragment implements ImageButton.OnClickListener {
    private ImageButton mButton; //Add at the top of the fragment
    private OnFragmentInteractionListener mListener;


    public feedbackFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        final EditText editName = (EditText) rootView.findViewById(R.id.editText);
        final EditText editMail = (EditText) rootView.findViewById(R.id.editText2);
        final EditText editFeedback = (EditText) rootView.findViewById(R.id.editText3);
        mButton = (ImageButton) rootView.findViewById(R.id.imageButton);


                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //button send

                        if (!editFeedback.getText().toString().equals("")) {
                            String uriText = "mailto:ntamim2006@gmail.com" +
                                    "?subject=" + URLEncoder.encode("feedback for Tv shows app") +
                                    "&body=" + URLEncoder.encode("Name: " + editName.getText().toString() + System.getProperty("line.separator") + "E-mail: " + editMail.getText().toString()
                                    + System.getProperty("line.separator")
                                    + "Feedback: " + editFeedback.getText().toString());
                            Uri uri = Uri.parse(uriText);
                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                            sendIntent.setData(uri);
                            startActivity(Intent.createChooser(sendIntent, "Send Email"));
                        }else{
                            Toast.makeText(getActivity(), "your feedback field is empty", Toast.LENGTH_SHORT).show();
                        }



                    }
                });

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