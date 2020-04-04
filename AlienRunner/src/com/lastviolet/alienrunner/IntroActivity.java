package com.lastviolet.alienrunner;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class IntroActivity extends Activity {
	
	ImageView img_logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		
		img_logo = (ImageView)findViewById(R.id.img_logo);
		Animation ani = AnimationUtils.loadAnimation(this, R.anim.appear_logo);
		img_logo.setAnimation(ani);
		
		// 데이터 로딩
		
		Timer timer = new Timer();
		timer.schedule(task, 5000);
	}
	
	@Override
	public void onBackPressed() 
	{
		return;
	}
	
	private void loadData()
	{
		SharedPreferences pref = getSharedPreferences("_data", Context.MODE_PRIVATE);
		
		G.isMusic = pref.getBoolean("_isMusic", true);
		G.isSound = pref.getBoolean("_isSound", true);
		G.isVibrate = pref.getBoolean("_isVibrate", true);
		G.isSelectItem = pref.getBoolean("_isSelectedItem", false);
		
		G.coin = pref.getInt("_coin", 0);
		G.gem = pref.getInt("_gem", 0);
		G.record = pref.getInt("_recorde", 0);
		G.selectedItem = pref.getInt("_selectedItem", 0);
		G.selectedChar = pref.getInt("_selectedChar", 0);
		
		for (int i = 0; i < G.item_have.length; i++)
		{
			G.item_have[i] = pref.getInt("item_have" + i, 0);
		}
		
		for (int i = 0; i < G.hasChar.length; i++)
		{
			G.hasChar[i] = pref.getBoolean("hasChar" + i, (i == 0) ? true : false);
		}
	}
	
	TimerTask task = new TimerTask() {
		
		@Override
		public void run() 
		{
			Intent intent = new Intent(IntroActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	};
}