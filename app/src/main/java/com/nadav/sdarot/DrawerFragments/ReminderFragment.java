package com.nadav.sdarot.DrawerFragments;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nadav.sdarot.AlarmReceiver;
import com.nadav.sdarot.FileManager;
import com.nadav.sdarot.R;
import com.nadav.sdarot.TimePickerFragment;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Created by Nadav on 08/03/2016.
 */
public class ReminderFragment extends Fragment implements Button.OnClickListener {
    private Button mButton; //Add at the top of the fragment
    private TimePicker timePicker;
    private OnFragmentInteractionListener mListener;
    private android.text.format.Time time;
    private Spinner spinner_select_repeat;
    ArrayAdapter<CharSequence> array;

    public ReminderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reminder, container, false);
        changeActionBarPicture(R.string.day_alarm);
        mButton = (Button) rootView.findViewById(R.id.button3);
        spinner_select_repeat = (Spinner) rootView.findViewById(R.id.spinner);
        Switch switchOnOff = (Switch)rootView.findViewById(R.id.mySwitch);
        final TextView textRepeat = (TextView) rootView.findViewById(R.id.switchStatus);
        mButton.setOnClickListener(this);
        final View view2 = (View)rootView.findViewById(R.id.view2);
        final TextView textTime = (TextView) rootView.findViewById(R.id.textView14);
        array = ArrayAdapter.createFromResource(getActivity(), R.array.list_of_repeat, R.layout.support_simple_spinner_dropdown_item);
        array.setDropDownViewResource(R.layout.spinner_alarm);
        Button cancel = (Button) rootView.findViewById(R.id.button4);
        spinner_select_repeat.setAdapter(array);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });

        File f_list = new File(getActivity().getFilesDir(),"alarmSwitch");
        String list = FileManager.readFromFile(f_list);

        File f_list_hour = new File(getActivity().getFilesDir(), "the hour from time picker");
        String hour = FileManager.readFromFile(f_list_hour);
        File f_list_minute = new File(getActivity().getFilesDir(), "the minute from time picker");
        String minute = FileManager.readFromFile(f_list_minute);

        if(list.equals("on") || list.equals("")){
            mButton.setEnabled(true);
            spinner_select_repeat.setEnabled(true);
            textTime.setEnabled(true);
            textTime.setTextColor(Color.parseColor("#0294b5"));
            mButton.setTextColor(Color.BLACK);
            textRepeat.setTextColor(Color.BLACK);
            view2.setBackgroundColor(Color.parseColor("#0294b5"));
            if(hour.toString().equals("") && minute.toString().equals("")){

            }else{
                textTime.setText(hour+":"+minute);
            }


        }else if(list.equals("off")){
            switchOnOff.setChecked(false);
            mButton.setEnabled(false);
            spinner_select_repeat.setEnabled(false);
            textTime.setEnabled(false);
            textTime.setTextColor(Color.LTGRAY);
            mButton.setTextColor(Color.LTGRAY);
            textRepeat.setTextColor(Color.LTGRAY);
            view2.setBackgroundColor(Color.LTGRAY);


        }

//        wrote to file


        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mButton.setEnabled(true);
                    spinner_select_repeat.setEnabled(true);
                    textTime.setEnabled(true);
                    textTime.setTextColor(Color.parseColor("#0294b5"));
                    mButton.setTextColor(Color.BLACK);
                    textRepeat.setTextColor(Color.BLACK);
                    view2.setBackgroundColor(Color.parseColor("#0294b5"));

                    final AlarmManager alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getActivity(),AlarmReceiver.class);
                    final PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    Calendar calendar = Calendar.getInstance();
                    if(calendar.get(Calendar.HOUR_OF_DAY)<20){
                        calendar.set(Calendar.HOUR_OF_DAY, 20);
                        calendar.set(Calendar.MINUTE, 00);
                        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
                    }else{
                        calendar.set(Calendar.HOUR_OF_DAY, 20);
                        calendar.set(Calendar.MINUTE, 00);
                        calendar.set(Calendar.DAY_OF_YEAR, 1);
                        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
                    }

                    File f_list = new File(getActivity().getFilesDir(), "alarmSwitch");
                    FileManager.writeToFile(f_list, "on");

                }else{
                    final AlarmManager alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getActivity(),AlarmReceiver.class);
                    final PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmMgr.cancel(pIntent);
                    File f_list_hour = new File(getActivity().getFilesDir(), "the hour from time picker");
                    File f_list_minute = new File(getActivity().getFilesDir(), "the minute from time picker");
                    FileManager.writeToFile(f_list_hour,"");
                    FileManager.writeToFile(f_list_minute,"");

                    mButton.setEnabled(false);
                    spinner_select_repeat.setEnabled(false);
                    textTime.setText("20:00");
                    textTime.setEnabled(false);
                    textTime.setTextColor(Color.LTGRAY);
                    mButton.setTextColor(Color.LTGRAY);
                    textRepeat.setTextColor(Color.LTGRAY);
                    view2.setBackgroundColor(Color.LTGRAY);

                    File f_list = new File(getActivity().getFilesDir(), "alarmSwitch");
                    FileManager.writeToFile(f_list, "off");
                }
            }
        });

cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final AlarmManager alarmMgr2 = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(getActivity(),AlarmReceiver.class);
        final PendingIntent pIntent2 = PendingIntent.getBroadcast(getActivity(), 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr2.cancel(pIntent2);
    }
});

        spinner_select_repeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        break;
                    case 1:
                        final AlarmManager alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getActivity(),AlarmReceiver.class);
                        final PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmMgr.cancel(pIntent);

                        File f_list_hour = new File(getActivity().getFilesDir(), "the hour from time picker");
                        String hour = FileManager.readFromFile(f_list_hour);
                        File f_list_minute = new File(getActivity().getFilesDir(), "the minute from time picker");
                        String minute = FileManager.readFromFile(f_list_minute);

                        Calendar c = Calendar.getInstance();
                        if(hour.toString().equals("") && minute.toString().equals("")){ //if the user dont pick from clock

                            if(c.get(Calendar.HOUR_OF_DAY)<20){
                                c.set(Calendar.HOUR_OF_DAY, 20);
                                c.set(Calendar.MINUTE, 00);
                                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 2*AlarmManager.INTERVAL_DAY, pIntent);
                                Toast.makeText(getActivity(), "4", Toast.LENGTH_SHORT).show();

                            }else{
                                c.set(Calendar.HOUR_OF_DAY, 20);
                                c.set(Calendar.MINUTE, 00);
                                c.set(Calendar.DAY_OF_YEAR, 1);
                                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 2*AlarmManager.INTERVAL_DAY, pIntent);
                                Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();

                            }
                        }else{
                            c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));//17
                            c.set(Calendar.MINUTE, Integer.parseInt(minute));//30
                           if(c.get(Calendar.HOUR_OF_DAY)<Calendar.getInstance().get(Calendar.HOUR_OF_DAY)){
                               c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));//17
                               c.set(Calendar.MINUTE, Integer.parseInt(minute));//30
                               c.set(Calendar.DAY_OF_MONTH,Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1);
                               alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 2*AlarmManager.INTERVAL_DAY, pIntent);
                               Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();


                           }else{
                               c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));//17
                               c.set(Calendar.MINUTE, Integer.parseInt(minute));//30
                               alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 2*AlarmManager.INTERVAL_DAY, pIntent);
                               Toast.makeText(getActivity(), hour+" "+minute, Toast.LENGTH_SHORT).show();
                           }
                        }
//                        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;



    }



    @Override
    public void onClick(View v) {

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
        View customActionBarView = inflater2.inflate(R.layout.layout_fragment_reminder, null);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        actionBar.setCustomView(customActionBarView, layoutParams);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_TITLE);

    }

}