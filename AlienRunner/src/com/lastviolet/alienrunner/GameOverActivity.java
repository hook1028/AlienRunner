package com.lastviolet.alienrunner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOverActivity extends Activity 
{
	TextView text_score;
	TextView text_champion;
	TextView text_get_coin;
	TextView text_get_gem;
	TextView text_total_coin;
	TextView text_total_gem;
	ImageView img_char;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("_bundle");
		int score = bundle.getInt("_score");
		int getCoin = bundle.getInt("_coin");
		int getGem = bundle.getInt("_gem");
		G.coin += getCoin;
		G.gem += getGem;
		if (G.record < score)
			G.record = score;
		
		text_score = (TextView)findViewById(R.id.text_gameover_score);
		text_champion = (TextView)findViewById(R.id.text_gameover_record);
		text_get_coin = (TextView)findViewById(R.id.text_gameover_get_coin);
		text_get_gem = (TextView)findViewById(R.id.text_gameover_get_gem);
		text_total_coin = (TextView)findViewById(R.id.text_gameover_total_coin);
		text_total_gem = (TextView)findViewById(R.id.text_gameover_total_gem);
		img_char = (ImageView)findViewById(R.id.img_gameover_char);
		
		String str = null;
		
		str = String.format("%07d", score);
		text_score.setText(str);
		
		str = String.format("%07d", G.record);
		text_champion.setText(str);
		
		str = String.format("%06d", getCoin);
		text_get_coin.setText(str);
		
		str = String.format("%04d", getGem);
		text_get_gem.setText(str);
		
		str = String.format("%06d", G.coin);
		text_total_coin.setText(str);
		
		str = String.format("%04d", G.gem);
		text_total_gem.setText(str);
		
		img_char.setImageResource(R.drawable.alien_01_shop + (6 * G.selectedChar));
		
		// TODO 데이터 저장
		saveData();
	}

	private void saveData()
	{
		SharedPreferences pref = getSharedPreferences("_data", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		
		editor.putBoolean("_isMusic", G.isMusic);
		editor.putBoolean("_isSound", G.isSound);
		editor.putBoolean("_isVibrate", G.isVibrate);
		editor.putBoolean("_isSelectedItem", G.isSelectItem);
		
		editor.putInt("_coin", G.coin);
		editor.putInt("_gem", G.gem);
		editor.putInt("_recorde", G.record);
		editor.putInt("_selectedItem", G.selectedItem);
		editor.putInt("_selectedChar", G.selectedChar);
		
		for (int i = 0; i < G.item_have.length; i++)
		{
			editor.putInt("item_have" + i, G.item_have[i]);
		}
		
		for (int i = 0; i < G.hasChar.length; i++)
		{
			editor.putBoolean("hasChar" + i, G.hasChar[i]);
		}
		
		editor.commit();
	}
	
	
	public void mOnClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_gameover_retry:
			
			Intent intent = new Intent(this, GameActivity.class);
			startActivity(intent);
			finish();
			
			break;
			
		case R.id.btn_gameover_exit:
			finish();
			break;
		}
	}
}
