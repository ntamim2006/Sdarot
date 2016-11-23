package com.nadav.sdarot;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;

import java.io.File;
import java.util.Calendar;
import android.widget.TimePicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        /*
            public constructor.....
            TimePickerDialog(Context context, int theme,
             TimePickerDialog.OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView)

            The 'theme' parameter allow us to specify the theme of TimePickerDialog

            .......List of Themes.......
            THEME_DEVICE_DEFAULT_DARK
            THEME_DEVICE_DEFAULT_LIGHT
            THEME_HOLO_DARK
            THEME_HOLO_LIGHT
            THEME_TRADITIONAL

         */
        TimePickerDialog tpd = new TimePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK
                ,this, hour, minute, false);

        //You can set a simple text title for TimePickerDialog
        //tpd.setTitle("Title Of Time Picker Dialog");

        /*.........Set a custom title for picker........*/
        TextView tvTitle = new TextView(getActivity());

        tvTitle.setText("Choose time for alarm");
        tvTitle.setBackgroundColor(Color.parseColor("#0294b5"));
        tvTitle.setPadding(5, 3, 5, 3);
        tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        tpd.setCustomTitle(tvTitle);
        /*.........End custom title section........*/

        return tpd;
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        TextView tv = (TextView) getActivity().findViewById(R.id.textView14);

        //Set a message for user

        //Get the AM or PM for current time
        String aMpM = "AM";
        if(hourOfDay >11)
        {
            aMpM = "PM";
        }

        //Make the 24 hour time format to 12 hour time format
        int currentHour = hourOfDay;



        String currentMin = minute+"";
        if(minute<10){
            currentMin = "0"+minute;
        }

        String x = currentHour + "," +currentMin;

        if(hourOfDay>11)
        {
            currentHour = hourOfDay - 12;
        }

        tv.setText("Your chosen time is...\n\n");
        //Display the user changed time on TextView
        tv.setText(String.valueOf(currentHour)
                + " : " + currentMin + " " + aMpM + "\n");

        final AlarmManager alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
         Intent intent = new Intent(getActivity(),AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.cancel(pIntent);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        File f_list_hour = new File(getActivity().getFilesDir(), "the hour from time picker");
        FileManager.writeToFile(f_list_hour, hourOfDay+"");
        File f_list_minute = new File(getActivity().getFilesDir(), "the minute from time picker");
        FileManager.writeToFile(f_list_minute, minute+"");
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);

    }
}