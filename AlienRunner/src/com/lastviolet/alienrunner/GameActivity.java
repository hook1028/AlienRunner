package com.lastviolet.alienrunner;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity 
{
	final int COUNT = 3;
	
	GameView mGameView;
	TextView text_score;
	TextView text_champion;
	TextView text_coin;
	TextView text_gem;
	TextView text_item;
	ImageView btn_pause_resume;
	ImageView img_pause_count;
	ImageView img_set_item;
	View dialog_pause;
	View layout_selectedItem;
	ImageView[] img_hp = new ImageView[4];
	
	Timer timer;
	TimerTask task;
	
	boolean isPause;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		mGameView = (GameView)findViewById(R.id.gameView);
		btn_pause_resume = (ImageView)findViewById(R.id.btn_pause_resume);
		dialog_pause = (View)findViewById(R.id.dialog_pause);
		img_pause_count = (ImageView)findViewById(R.id.img_dialog_pause_count);
		
		text_score = (TextView)findViewById(R.id.text_score);
		text_champion = (TextView)findViewById(R.id.text_champion);
		text_coin = (TextView)findViewById(R.id.text_coin);
		text_gem = (TextView)findViewById(R.id.text_gem);
		
		if (G.isSelectItem)
		{
			layout_selectedItem = findViewById(R.id.layout_selectedItem);
			img_set_item = (ImageView)findViewById(R.id.img_set_item);
			text_item = (TextView)findViewById(R.id.text_item);
			
			layout_selectedItem.setVisibility(View.VISIBLE);
			img_set_item.setImageResource(R.drawable.item_01_hp + G.selectedItem);
		}
		
		for (int i = 0; i < img_hp.length; i++)
		{
			img_hp[i] = (ImageView)findViewById(R.id.img_hp_01 + i);
		}
	}
	
	@Override
	protected void onPause() 
	{
		if (mGameView != null)
			mGameView.pauseGame(true);
		
		super.onPause();
	}
	
	@Override
	public void onBackPressed() 
	{
		// 정말로 나가겠냐는 다이얼로그창을 만든다.
		mGameView.stopGame();
		finish();
	}
	
	public void mOnClick(View v)
	{
		switch(v.getId())
		{
		case R.id.btn_pause_resume:
			
			isPause = !isPause;
			btn_pause_resume.setImageResource(isPause ? R.drawable.btn_game_resume : R.drawable.btn_game_pause);
			
			// 게임 멈춤을 나타내는 다이얼 로그를 만든다.
			if (isPause)
			{
				dialog_pause.setVisibility(View.VISIBLE);
				mGameView.pauseGame(isPause);
			}
			
			else
			{
				timer = new Timer();
				task = new TimerTask(){

					int i = 0;
					@Override
					public void run() {
						
						runOnUiThread(new Runnable(){

							@Override
							public void run() 
							{
								img_pause_count.setVisibility(View.VISIBLE);
								img_pause_count.setImageResource(R.drawable.count_3 - ((i < 3) ? i : 2));
								i++;
								
								if (i == 4)
								{
									img_pause_count.setVisibility(View.GONE);
									dialog_pause.setVisibility(View.GONE);
									task.cancel();
									mGameView.pauseGame(isPause);
								}
							}					
						});						
					}
				};
				
				timer.schedule(task, 0, 1000);
			}
			break;
		}
	}
	
	Handler handler = new Handler()
	{
		int i = 0;
		
		@Override
		public void handleMessage(Message msg)
		{	
//			Log.i("handleMessage", "handler 호출");
			mGameView.stopGame();
			Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
			intent.putExtra("_bundle", msg.getData());
			startActivity(intent);
			finish();
		}
	};
}
