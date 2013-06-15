package com.yoon.mytimer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MyTimer extends Activity {//implements OnClickListener {

	private CountDownTimer countDownTimer;
	private boolean timerStarted = false;
	private boolean timer_started = false;
	
	private Button startB;
	private Button riceT;
	private Button eggT;
	private Button button;
	
	private long timeElapsed;
	private long startTime;
	private long pausedTime;
	
	private TextView text;
	private TextView timeElapsedView;
	
	//private final long startTimeR = 5 * 60 * 1000;
	private final long startTimeR = 70 * 60 * 1000;
	private final long startTimeE = 15 * 60 * 1000;
	private long interval = 1 * 1000;
	
	//for vibrator
	private Vibrator v;
	int dot = 200;
	int dash = 500;
	int short_gap = 200;
	int medium_gap = 500;
	int long_gap = 1000;
	long[] pattern = {dot, short_gap, short_gap, dot, medium_gap, dash, short_gap, dash, short_gap, dash,
						medium_gap, dot, short_gap, dot, short_gap, dot, long_gap};
		
	//for timer set
	private Spinner s;
	
	//Sound
	private SoundPool sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_timer);
		
		text = (TextView)this.findViewById(R.id.time);
		timeElapsedView = (TextView)this.findViewById(R.id.timeElapsed);
		
		
		startB = (Button)this.findViewById(R.id.button);
		startB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!timerStarted && countDownTimer != null) {
					countDownTimer.start();
					timerStarted = true;
					timer_started = true;
					startB.setText("Cancel");

				} else if(timerStarted && countDownTimer != null) {
					countDownTimer.cancel();
					timerStarted = false;
					timer_started = false;
					startB.setText("Start");
				}
			}
			
		});

		riceT = (Button)this.findViewById(R.id.Rice);
		riceT.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(timer_started) {			
					timer_started = false;
					countDownTimer.cancel();
					startB.setText("Start");
				}
				final long time = Long.parseLong(s.getSelectedItem().toString())*1000 * 60;
				countDownTimer = new MalibuCountDownTimer(time, interval);
				//if(startTimeR / (interval * 3600) >= 1) {
				//text.setText("Time: " + String.valueOf(startTimeR / (interval * 3600)) + " : " + String.valueOf(((startTimeR / interval) % 3600) / 60) + " : " + String.valueOf(((startTimeR / interval) % 36000) % 60));
				text.setText("Time: " + String.valueOf(time / (interval * 3600)) + " : " + String.valueOf(((time / interval) % 3600) / 60) + " : " + String.valueOf(((time / interval) % 36000) % 60));
				//} else {
			    //	text.setText("Time: " + String.valueOf(startTimeR / interval / 60) + " : " + String.valueOf(startTimeR / interval % 60));
			    //}
				startTime = time;
				timerStarted = false;
			}
			
		});
		
/*		eggT = (Button)this.findViewById(R.id.egg);
		eggT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(timer_started) {
					timer_started = false;
					countDownTimer.cancel();
					startB.setText("Start");
				}
				countDownTimer = new MalibuCountDownTimer(startTimeE, interval);
				long hour = startTimeE / (interval * 60 * 60);
				if (hour >= 1) {
					text.setText("Time: " + String.valueOf(hour) + " : " + String.valueOf(((startTimeE / interval) % 60) / 60) + " : " + String.valueOf(((startTimeE / interval) % 60) % 60));
			    } else {
			    	text.setText("Time: " + String.valueOf(startTimeE / interval / 60) + " : " + String.valueOf(startTimeE / interval % 60));
			    }
			    startTime = startTimeE;
				timerStarted = false;
				//countDownTimer.cancel();
			}
			
		});*/
		
		//for pausedTime
		button = (Button)this.findViewById(R.id.button3);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(pausedTime != 0){
					countDownTimer.cancel();
					timerStarted = false;
					countDownTimer = new MalibuCountDownTimer(pausedTime, interval);
					startB.setText("Start");
				}
			}
			
		});
		
		addItemsOnSpinner();

		
		//sp.load(afd, R.id.)
		//long time = (Long) s.getSelectedItem();

		
	}

/*
	private void selectedItem(){
		final long time = Long.parseLong(s.getSelectedItem().toString())*1000;
		countDownTimer = new MalibuCountDownTimer(time, interval);
		text.setText("Time: " + String.valueOf(time / (interval * 3600)) + " : " + String.valueOf(((time / interval) % 3600) / 60) + " : " + String.valueOf(((time / interval) % 36000) % 60));
		startTime = time;
		timerStarted = false;
	}
*/

	private void addItemsOnSpinner() {
		// TODO Auto-generated method stub
		s = (Spinner)this.findViewById(R.id.spinner1);
		List<Integer> items = new ArrayList<Integer>();
		for(int i =	5; i <= 60; i+=5) {
			items.add(i);
		}
		//calling customer xml file which is named as spinner_item.xml
		ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner_item, items);		
		s.setAdapter(arrayAdapter);
	}
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		System.exit(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_timer, menu);
		return true;
	}
	
	public class MalibuCountDownTimer extends CountDownTimer {

		public MalibuCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(pattern, -1);
			text.setText("Time's up");
			timeElapsedView.setText("Time Elapsed: " + (startTime / interval) / 60 + " : " + (startTime / interval) % 60);
			startB.setText("Start");

		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			pausedTime = millisUntilFinished;
			text.setText("Time remain: " + (millisUntilFinished / interval /60) + ":" + (millisUntilFinished / interval) % 60);
			timeElapsed = (startTime - millisUntilFinished + 1000) / 1000;
			timeElapsedView.setText("Time Elapsed: " + String.valueOf(timeElapsed));
			
		}
		
	}


}


