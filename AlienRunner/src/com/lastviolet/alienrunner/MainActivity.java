package com.lastviolet.alienrunner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	View dialog_setting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dialog_setting = findViewById(R.id.dialog_setting);
	}
	
	@Override
	public void onBackPressed() 
	{
		if (dialog_setting.getVisibility() == View.VISIBLE)
		{
			dialog_setting.setVisibility(View.GONE);
			return;
		}
			
		finish();
	}
	
	public void mOnClick(View v)
	{	
		if(dialog_setting.getVisibility() == View.VISIBLE)
			return;
		
		Intent intent;
		
		switch(v.getId())
		{
		case R.id.btn_title_gameStart:

			intent = new Intent(this, GameActivity.class);
			startActivity(intent);
			
			break;
			
		case R.id.btn_title_shop:

			intent = new Intent(this, ShopActivity.class);
			startActivity(intent);
			
			break;
			
		case R.id.btn_title_setting:
			
			// 다이얼로그 창을 띄운다.
			ImageView img;
			
			img = (ImageView)dialog_setting.findViewById(R.id.btn_setting_dialog_music);
			img.setImageResource((G.isMusic) ? R.drawable.dialog_setting_on : R.drawable.dialog_setting_off);
						
			img = (ImageView)dialog_setting.findViewById(R.id.btn_setting_dialog_sound);
			img.setImageResource((G.isSound) ? R.drawable.dialog_setting_on : R.drawable.dialog_setting_off);
			
			img = (ImageView)dialog_setting.findViewById(R.id.btn_setting_dialog_vibrate);
			img.setImageResource((G.isVibrate) ? R.drawable.dialog_setting_on : R.drawable.dialog_setting_off);		
			
			dialog_setting.setVisibility(View.VISIBLE);
						
			break;
			
		case R.id.btn_title_exit:
			
			finish();
			break;
		}
	}
	
	public void dialogOnClick(View v)
	{	
		switch(v.getId())
		{
		case R.id.btn_setting_dialog_music:
			
			G.isMusic = !G.isMusic;
			((ImageView)v).setImageResource((G.isMusic) ? R.drawable.dialog_setting_on : R.drawable.dialog_setting_off);
			
			break;
			
		case R.id.btn_setting_dialog_sound:
			
			G.isSound = !G.isSound;
			((ImageView)v).setImageResource((G.isSound) ? R.drawable.dialog_setting_on : R.drawable.dialog_setting_off);
			
			break;
			
		case R.id.btn_setting_dialog_vibrate:
			
			G.isVibrate = !G.isVibrate;
			((ImageView)v).setImageResource((G.isVibrate) ? R.drawable.dialog_setting_on : R.drawable.dialog_setting_off);
			
			break;
			
		case R.id.btn_setting_dialog_ok:
			
			dialog_setting.setVisibility(View.GONE);
			break;		
		}
	}
}
