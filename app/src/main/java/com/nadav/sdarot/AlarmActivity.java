/* Copyright 2014 Sheldon Neilson www.neilson.co.za
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.nadav.sdarot;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import com.nadav.sdarot.database.Database;
import com.nadav.sdarot.preferences.AlarmPreferencesActivity;

public class AlarmActivity extends BaseActivity {

	ListView mathAlarmListView;
	AlarmListAdapter alarmListAdapter;
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alarm_activity);
		actionBar= getSupportActionBar();
		actionBar.setTitle("Alarms");

		LayoutInflater inflater = (LayoutInflater) getSupportActionBar().getThemedContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView = inflater.inflate(
				R.layout.layout_alarms, null);
		ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
		actionBar.setCustomView(customActionBarView, layoutParams);

		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_TITLE);

		mathAlarmListView = (ListView) findViewById(android.R.id.list);
		mathAlarmListView.setLongClickable(true);
		mathAlarmListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
				view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
				final Alarm alarm = (Alarm) alarmListAdapter.getItem(position);
				Builder dialog = new AlertDialog.Builder(AlarmActivity.this);
				dialog.setTitle("Delete");
				dialog.setMessage("Delete this alarm?");
				dialog.setPositiveButton("Ok", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						Database.init(AlarmActivity.this);
						Database.deleteEntry(alarm);
						AlarmActivity.this.callMathAlarmScheduleService();
						
						updateAlarmList();
					}
				});
				dialog.setNegativeButton("Cancel", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				dialog.show();

				return true;
			}
		});

		callMathAlarmScheduleService();

		alarmListAdapter = new AlarmListAdapter(this);
		this.mathAlarmListView.setAdapter(alarmListAdapter);
		mathAlarmListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				Alarm alarm = (Alarm) alarmListAdapter.getItem(position);
				Intent intent = new Intent(AlarmActivity.this, AlarmPreferencesActivity.class);
				intent.putExtra("alarm", alarm);
				intent.putExtra("name", alarm.getAlarmName());
				startActivity(intent);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);		
		menu.findItem(R.id.menu_item_save).setVisible(false);
		menu.findItem(R.id.menu_item_delete).setVisible(false);

	    return result;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items

		switch (item.getItemId()) {

			case android.R.id.home:
				super.onBackPressed();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
		
	@Override
	protected void onPause() {
		// setListAdapter(null);
		Database.deactivate();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateAlarmList();
	}
	
	public void updateAlarmList(){
		Database.init(AlarmActivity.this);
		final List<Alarm> alarms = Database.getAll();
		alarmListAdapter.setMathAlarms(alarms);
		
		runOnUiThread(new Runnable() {
			public void run() {
				// reload content			
				AlarmActivity.this.alarmListAdapter.notifyDataSetChanged();				
				if(alarms.size() > 0){
					findViewById(android.R.id.empty).setVisibility(View.INVISIBLE);
				}else{
					findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.checkBox_alarm_active) {
			CheckBox checkBox = (CheckBox) v;
			Alarm alarm = (Alarm) alarmListAdapter.getItem((Integer) checkBox.getTag());
			alarm.setAlarmActive(checkBox.isChecked());
			Database.update(alarm);
			AlarmActivity.this.callMathAlarmScheduleService();
			if (checkBox.isChecked()) {
				Toast.makeText(AlarmActivity.this, alarm.getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG).show();
			}
		}

	}

}