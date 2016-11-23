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
package com.nadav.sdarot.alarm;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nadav.sdarot.Alarm;
import com.nadav.sdarot.MainActivity;
import com.nadav.sdarot.R;

public class AlarmAlertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
		resultIntent.setAction(Intent.ACTION_MAIN);
		resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
				resultIntent, 0);


		int mNotificationId = (int) (2 + System.currentTimeMillis());                // Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr =
				(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

		Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		// build the notification
		android.support.v7.app.NotificationCompat.Builder mBuilder = (android.support.v7.app.NotificationCompat.Builder) new android.support.v7.app.NotificationCompat.Builder(getApplicationContext())
				.setSmallIcon(R.drawable.icon_notification)
				//            .setColor(Color.WHITE)
				.setColor(Color.rgb(2, 148, 181))
				.setContentTitle("you saw some episode today?")
				.setContentText("check it now!")
				.setSound(sound)
				.setContentIntent(pendingIntent)
				.addAction(R.drawable.icon_back_to_app, "go to app", pendingIntent)
				.setVisibility(Notification.VISIBILITY_PUBLIC)
				.setPriority(Notification.PRIORITY_MAX);



		//issue the notification
		mNotifyMgr.notify(mNotificationId, mBuilder.build());

		this.finishAffinity();

	}


//		this.setTitle(alarm.getAlarmName());

//		switch (alarm.getDifficulty()) {
//		case EASY:
//			mathProblem = new MathProblem(3);
//			break;
//		case MEDIUM:
//			mathProblem = new MathProblem(4);
//			break;
//		case HARD:
//			mathProblem = new MathProblem(5);
//			break;
//		}

//		answerString = String.valueOf(mathProblem.getAnswer());
//		if (answerString.endsWith(".0")) {
//			answerString = answerString.substring(0, answerString.length() - 2);
//		}
//
//		problemView = (TextView) findViewById(R.id.textView1);
//		problemView.setText(mathProblem.toString());
//
//		answerView = (TextView) findViewById(R.id.textView2);
//		answerView.setText("= ?");
//
//		((Button) findViewById(R.id.Button0)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button1)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button2)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button3)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button4)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button5)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button6)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button7)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button8)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button9)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button_clear)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button_decimal)).setOnClickListener(this);
//		((Button) findViewById(R.id.Button_minus)).setOnClickListener(this);
//
//		TelephonyManager telephonyManager = (TelephonyManager) this
//				.getSystemService(Context.TELEPHONY_SERVICE);
//
//		PhoneStateListener phoneStateListener = new PhoneStateListener() {
//			@Override
//			public void onCallStateChanged(int state, String incomingNumber) {
//				switch (state) {
//				case TelephonyManager.CALL_STATE_RINGING:
//					Log.d(getClass().getSimpleName(), "Incoming call: "
//							+ incomingNumber);
//					try {
//						mediaPlayer.pause();
//					} catch (IllegalStateException e) {
//
//					}
//					break;
//				case TelephonyManager.CALL_STATE_IDLE:
//					Log.d(getClass().getSimpleName(), "Call State Idle");
//					try {
//						mediaPlayer.start();
//					} catch (IllegalStateException e) {
//
//					}
//					break;
//				}
//				super.onCallStateChanged(state, incomingNumber);
//			}
//		};
//
//		telephonyManager.listen(phoneStateListener,
//				PhoneStateListener.LISTEN_CALL_STATE);

		// Toast.makeText(this, answerString, Toast.LENGTH_LONG).show();



	}




	/*
	 * (non-Javadoc)
	 *
	 * @see android.app.Activity#onBackPressed()
	 */
//	@Override
//	public void onBackPressed() {
//
//			super.onBackPressed();
//	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.app.Activity#onPause()
	 */
//	@Override
//	protected void onPause() {
//		super.onPause();
//		StaticWakeLock.lockOff(this);
//	}



//	@Override
//	public void onClick(View v) {
//		if (!alarmActive)
//			return;
//		String button = (String) v.getTag();
//		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//		if (button.equalsIgnoreCase("clear")) {
//			if (answerBuilder.length() > 0) {
//				answerBuilder.setLength(answerBuilder.length() - 1);
//				answerView.setText(answerBuilder.toString());
//			}
//		} else if (button.equalsIgnoreCase(".")) {
//			if (!answerBuilder.toString().contains(button)) {
//				if (answerBuilder.length() == 0)
//					answerBuilder.append(0);
//				answerBuilder.append(button);
//				answerView.setText(answerBuilder.toString());
//			}
//		} else if (button.equalsIgnoreCase("-")) {
//			if (answerBuilder.length() == 0) {
//				answerBuilder.append(button);
//				answerView.setText(answerBuilder.toString());
//			}
//		} else {
//			answerBuilder.append(button);
//			answerView.setText(answerBuilder.toString());
//			if (isAnswerCorrect()) {
//				alarmActive = false;
//				if (vibrator != null)
//					vibrator.cancel();
//				try {
//					mediaPlayer.stop();
//				} catch (IllegalStateException ise) {
//
//				}
//				try {
//					mediaPlayer.release();
//				} catch (Exception e) {
//
//				}
//				this.finish();
//			}
//		}
//		if (answerView.getText().length() >= answerString.length()
//				&& !isAnswerCorrect()) {
//			answerView.setTextColor(Color.RED);
//		} else {
//			answerView.setTextColor(Color.BLACK);
//		}
//	}
//
//	public boolean isAnswerCorrect() {
//		boolean correct = false;
//		try {
//			correct = mathProblem.getAnswer() == Float.parseFloat(answerBuilder
//					.toString());
//		} catch (NumberFormatException e) {
//			return false;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return correct;
//	}


