package com.lastviolet.alienrunner;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements Callback 
{
	// 사이즈 관련 변수
	int width;
	int height;
	
	// Acitvity 및 View 관련 클래스
	Context mContext;
	GameThread mThread;
	SurfaceHolder mHolder;
	
	// 생성자
	public GameView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		
		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {}

	// 화면이 다 생성된 후 호출되므로 너비, 높이 설정 및 스레드 동작을 한다.
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		this.width = getWidth();
		this.height = getHeight();
		
		mThread = new GameThread();
		mThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}
	
	// 스레드로 터치다운 이벤트를 넘긴다.
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{	
		int x, y;
		
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			
			x = (int)event.getX();
			y = (int)event.getY();
			mThread.onTouchDown(x, y);
			
			break;
			
		case MotionEvent.ACTION_UP:
			
			x = (int)event.getX();
			y = (int)event.getY();
			mThread.onTouchUp(x, y);
			
			break;
		}	
		return true;
	}
	
	void pauseGame(boolean isPause)
	{
		mThread.pauseThread(isPause);
	}
	
	void stopGame()
	{
		mThread.stopThread();
	}	
	
	/* Game Thread
	 **************************************************************************************/
	class GameThread extends Thread
	{		
		// Bitmap 이미지
		Bitmap imgBgSelected;
		Bitmap[] btn_jump = new Bitmap[2];
		Bitmap[] btn_item = new Bitmap[2];
		Bitmap[] imgRoad = new Bitmap[6];
		Bitmap[] imgBg = new Bitmap[6];
		Bitmap[] imgObstacleLong = new Bitmap[7];
		Bitmap[] imgObstacleSquare = new Bitmap[12];
		Bitmap[] imgObstacleWide = new Bitmap[3];
		Bitmap[] imgItem = new Bitmap[5];
		Bitmap[] imgCoin = new Bitmap[3];
		Bitmap[][] imgPlayer = new Bitmap[5][5];
		Bitmap[][] imgPlayerBig = new Bitmap[5][5];
		
		// 게임 관련 변수
		float[] playerBigSize = { 1, 1.2f, 1.5f, 2.0f };
		int sx;			// 기준 x 좌표
		int widthBasic = width / 20;
		int move_backPos;
		int dropCount = 0;
		int collisionCount = 0;
		int patternRoad = 0;
		int patternObstacle = 0;
		long downBefore = 0;
		long downNow = 0;
		long downGap;
		
		int level = 0;
		int hp = Player.HP[G.selectedChar];
		int score;
		int countCoin;
		int countGem;

		// Class
		Random rnd = new Random();
		Player me;
		Rect btnJumpRect;
		Rect btnItemRect;
		
		// ArrayList
		ArrayList<Road> road = new ArrayList<Road>();
		ArrayList<Obstacle> obstacle = new ArrayList<Obstacle>();
		ArrayList<Coin> coin = new ArrayList<Coin>();
		ArrayList<Item> item = new ArrayList<Item>();
		
		// run() 관련 변수
		boolean isRun = true;
		boolean isPause = false;
		
		// 게임 동작 관련 변수
		boolean isJump = false;
		boolean isItem = false;
		boolean isOnRoad = false;
		boolean isCollision = false;
		boolean wasAppearedItem = false;
		boolean[] wereBgsChanged = { false, false, false, false, false }; 
		
		// 아이템 관련 변수
		boolean isFast = false;
		int fastTime = 0;
		int bigTime = 0;
		int magnetTime = 0;
		int goldTime = 0;
		
		/* FPS 관련 변수들 **************************************************************/
		
		final int FPS = 25;					// 초당 프레임
		int frameTime = 1000 / FPS;		// 한 프레임에 걸리는 시간
		long startTime;					// 그리기 전 기록
		long endTime;					// 한 번 그린 후 기록
		long timeGap;					// 실제 걸린 시간
		long sleepTime;					// 빠를 경우 스레드가 쉬어야 하는 시간
		int skipFrame;					// 느릴 경우 스레드가 건너 뛰어야 하는 프레임 수
		
		/* **************************************************************************/
		
		
		public GameThread() 
		{ 				
			makeBitmap();
		}
		
		private void makeBitmap()		// Bitmap 생성 메소드
		{
			Bitmap src;
			Resources res = mContext.getResources();
			
			// 배경 그리기
			for (int i = 0; i < imgBg.length; i++)
			{
				src = BitmapFactory.decodeResource(res, R.drawable.bg_01 + i);
				imgBg[i] = Bitmap.createScaledBitmap(src, width, height, true);
				src.recycle();
				src = null;	
			}
						
			// 길 그리기
			for (int i = 0; i < imgRoad.length; i++)
			{
				src = BitmapFactory.decodeResource(res, R.drawable.road_01_grass + i);
				imgRoad[i] = Bitmap.createScaledBitmap(src, widthBasic, (widthBasic) * 3, true);
				src.recycle();
				src = null;
			}
			
			// 장애물 그리기
			// 정사각형 장애물
			for (int i = 0; i < imgObstacleSquare.length; i++)
			{
				src = BitmapFactory.decodeResource(res, R.drawable.obstacle_square_01 + i);
				imgObstacleSquare[i] = Bitmap.createScaledBitmap(src, width / 22, width / 22, true);
				src.recycle();
				src = null;
			}
			
			// 넓은 장애물
			for (int i = 0; i < imgObstacleWide.length; i++)
			{
				src = BitmapFactory.decodeResource(res, R.drawable.obstacle_wide_01 + i);
				imgObstacleWide[i] = Bitmap.createScaledBitmap(src, width / 11, widthBasic, true);
				src.recycle();
				src = null;
			}
			
			// 긴 장애물
			for (int i = 0; i < imgObstacleLong.length; i++)
			{
				src = BitmapFactory.decodeResource(res, R.drawable.obstacle_long_01 + i);
				imgObstacleLong[i] = Bitmap.createScaledBitmap(src, widthBasic, height / 6, true);
				src.recycle();
				src = null;
			}
			
			// 코인 그리기
			for (int i = 0; i < imgCoin.length; i++)
			{
				src = BitmapFactory.decodeResource(res, R.drawable.coin_01_bronze + i);
				imgCoin[i] = Bitmap.createScaledBitmap(src, widthBasic / 2, widthBasic / 2, true);
				src.recycle();
				src = null;
			}
			
			// 아이템 그리기
			for (int i = 0; i < imgItem.length; i++)
			{
				src = BitmapFactory.decodeResource(res, R.drawable.item_01_hp + i);
				imgItem[i] = Bitmap.createScaledBitmap(src, widthBasic, widthBasic, true);
				src.recycle();
				src = null;
			}
			
			// 캐릭터 그리기
			for (int i = 0; i < imgPlayer.length; i++)
			{
				for (int j = 0; j < imgPlayer[i].length; j++)
				{
					src = BitmapFactory.decodeResource(res, R.drawable.alien_01_hit + (i * 6) + ((j > 2) ? j + 1 : j));
					imgPlayer[i][j] = Bitmap.createScaledBitmap(src, widthBasic, height / 8, true);
					src.recycle();
					src = null;
				}
			}
			
			// 확대 캐릭터 그리기
			for (int i = 0; i < imgPlayerBig.length; i++)
			{
				for (int j = 0; j < imgPlayerBig[i].length; j++)
				{
					src = BitmapFactory.decodeResource(res, R.drawable.alien_01_walk_01 + (i * 6) + ((j < 4) ? 0 : 1));
					imgPlayerBig[i][j] = Bitmap.createScaledBitmap(src, (int)(widthBasic * playerBigSize[j < 4 ? j : 3]), (int)(height / 6 * playerBigSize[j < 4 ? j : 3]), true);
					src.recycle();
					src = null;
				}
			}
			
			// 버튼 그리기
			src = BitmapFactory.decodeResource(res, R.drawable.btn_game_jump_n);
			btn_jump[0] = Bitmap.createScaledBitmap(src, width / 6, height / 5, true);
			src.recycle();
			src = null;
			
			src = BitmapFactory.decodeResource(res, R.drawable.btn_game_jump_c);
			btn_jump[1] = Bitmap.createScaledBitmap(src, width / 6, height / 5, true);
			src.recycle();
			src = null;
			
			src = BitmapFactory.decodeResource(res, R.drawable.btn_game_item_n);
			btn_item[0] = Bitmap.createScaledBitmap(src, width / 6, height / 5, true);
			src.recycle();
			src = null;
			
			src = BitmapFactory.decodeResource(res, R.drawable.btn_game_item_c);
			btn_item[1] = Bitmap.createScaledBitmap(src, width / 6, height / 5, true);
			src.recycle();
			src = null;
			
			// 버튼의 영역 구하기
			btnJumpRect = new Rect(20, height - btn_jump[0].getHeight() - 10, 20 + btn_jump[0].getWidth(), height - 10);
			btnItemRect = new Rect(width - btn_item[0].getWidth() - 20, height - btn_item[0].getHeight() - 10, width - 20, height - 10);
		}
		
		private void removeResources()		// GameActivity를 종료하기 전 자원을 제거하는 메소드
		{
			// 배경 이미지
			for (int i = 0; i < imgBg.length; i++)
			{
				imgBg[i].recycle();
				imgBg[i] = null;	
			}
			
			// 길 이미지
			for (int i = 0; i < imgRoad.length; i++)
			{
				imgRoad[i].recycle();
				imgRoad[i] = null;	
			}
			
			// 장애물 이미지
			// 정사각형 장애물
			for (int i = 0; i < imgObstacleSquare.length; i++)
			{
				imgObstacleSquare[i].recycle();
				imgObstacleSquare[i] = null;
			}
			
			// 긴 장애물
			for (int i = 0; i < imgObstacleWide.length; i++)
			{
				imgObstacleWide[i].recycle();
				imgObstacleWide[i] = null;
			}
			
			// 높은 장애물
			for (int i = 0; i < imgObstacleLong.length; i++)
			{
				imgObstacleLong[i].recycle();
				imgObstacleLong[i] = null;
			}
			
			// 코인 이미지
			for (int i = 0; i < imgCoin.length; i++)
			{
				imgCoin[i].recycle();
				imgCoin[i] = null;
			}
			
			// 아이템 이미지
			for (int i = 0; i < imgItem.length; i++)
			{
				imgItem[i].recycle();
				imgItem[i] = null;
			}
			
			// 캐릭터 이미지
			for (int i = 0; i < imgPlayer.length; i++)
			{
				for (int j = 0; j < imgPlayer[i].length; j++)
				{
					imgPlayer[i][j].recycle();
					imgPlayer[i][j] = null;
				}
			}
			
			// 캐릭터 확대 이미지
			for (int i = 0; i < imgPlayerBig.length; i++)
			{
				for (int j = 0; j < imgPlayerBig[i].length; j++)
				{
					imgPlayerBig[i][j].recycle();
					imgPlayerBig[i][j] = null;
				}
			}
			
			// 버튼 이미지
			btn_jump[0].recycle();
			btn_jump[0] = null;
			btn_jump[1].recycle();
			btn_jump[1] = null;
			
			btn_item[0].recycle();
			btn_item[0] = null;
			btn_item[1].recycle();
			btn_item[1] = null;
		}
		
		// 화면 그리기 관련 메소드
		void makeAll()
		{
			// 맨 첫화면 만들기
			if (road.size() == 0)
			{
				imgBgSelected = imgBg[level]; // 첫 배경화면을 설정한다.
				
				while(road.size() < Road.road_case[0].length * 2)
				{
					for (int i = 0; i < Road.road_case[0].length; i++)
					{
						if(Road.road_case[0][i] == 1)
						{
							road.add(new Road(imgRoad[level], sx, height, widthBasic, imgRoad[level].getHeight()));
							
							if (i > 4 || road.size() > Road.road_case.length)
							{
								coin.add(new Coin(imgCoin, widthBasic / 2, sx, height, imgRoad[level].getHeight() + me.h, i, true, Coin.BRONZE));		
								coin.add(new Coin(imgCoin, widthBasic / 2, sx, height, imgRoad[level].getHeight() + me.h, i, false, Coin.BRONZE));
							}				
						}
						sx += widthBasic;
					}
				}
			}
			else if (road.size() < Road.road_case[0].length)	// 길 이미지 분량이 한 페이지 이하면
			{
				patternRoad = rnd.nextInt(7);
				patternObstacle = rnd.nextInt((level < 5) ? 5 * (level + 1) : 10) + ((level < 5) ? 0 : 15);
				
				int kindCoin = (goldTime > 0) ? Coin.GOLD : (level > 3) ? Coin.SILVER : Coin.BRONZE;
				// 오른쪽 화면 밖에 미리 화면을 그린다.
				for (int i = 0; i < Road.road_case[0].length; i++)
				{
					// 길이 있을 경우
					if(Road.road_case[patternRoad][i] == 1)
					{
						// 길을 만든다.
						road.add(new Road(imgRoad[level], sx, height, imgRoad[level].getWidth(), imgRoad[level].getHeight()));
					}
					
					// 장애물 만들기
					switch(Obstacle.obstacle_case[patternObstacle][i])
					{
					case Obstacle.SQUARE:
						obstacle.add(new Obstacle(imgObstacleSquare, sx, height, width / 22, width / 22, imgRoad[0].getHeight(), widthBasic, Obstacle.SQUARE, level));
						coin.add(new Coin(imgCoin, widthBasic / 2, sx, height, imgRoad[level].getHeight() + me.height, i, true, kindCoin));
						coin.add(new Coin(imgCoin, widthBasic / 2, sx, height, imgRoad[level].getHeight() + me.height, i, false, kindCoin));
						break;
						
					case Obstacle.LONG:
						obstacle.add(new Obstacle(imgObstacleLong, sx, height, widthBasic, imgObstacleLong[0].getHeight(), imgRoad[0].getHeight(), widthBasic, Obstacle.LONG, level));
						coin.add(new Coin(imgCoin, widthBasic / 2, sx, height, imgRoad[level].getHeight() + (me.height * 2), i, true, kindCoin));
						coin.add(new Coin(imgCoin, widthBasic / 2, sx, height, imgRoad[level].getHeight() + (me.height * 2), i, false, kindCoin));
						break;
					
					case Obstacle.WIDE:
						obstacle.add(new Obstacle(imgObstacleWide, sx, height, width / 11, widthBasic, imgRoad[0].getHeight(), widthBasic, Obstacle.WIDE, level));	
						break;
						
					case 0:
						if (Road.road_case[patternRoad][i] == 0) break;
						if (i > 1 && Obstacle.obstacle_case[patternObstacle][i - 1] == Obstacle.WIDE) break;
						
						if (!wasAppearedItem && rnd.nextInt(20) == 0)
						{
							wasAppearedItem = true;
							item.add(new Item(imgItem, widthBasic, sx, height, imgRoad[level].getHeight() + me.height));
						}
						else
						{	
							coin.add(new Coin(imgCoin, widthBasic / 2, sx, height, imgRoad[level].getHeight() + me.h0, i, true, kindCoin));
							coin.add(new Coin(imgCoin, widthBasic / 2, sx, height, imgRoad[level].getHeight() + me.h0, i, false, kindCoin));
						}
						break;
					}
					
					sx += widthBasic;
				}	
				wasAppearedItem = false;		
			}
		}
		
		void moveAll()
		{
			// 배경 움직이기
			int dx = width / 400;			// 배경의 움직이는 속도 조절 (최소 1)
			sx -= widthBasic / 2 / 3 * (isFast ? 2 : 1);
			move_backPos -= dx;
			if (move_backPos <= -width)					
				move_backPos = 0;	
			
			// 길 움직이기
			boolean isBgChange = true;
			for (int i = road.size() - 1; i >= 0; i--)
			{
				Road r = road.get(i);
				r.move(isFast);
				
				if (r.isDead)
					road.remove(r);
				
				if (r.img != imgRoad[level])
					isBgChange = false;
			}
			
			if (isBgChange)
				imgBgSelected = imgBg[level];
			
			// 장애물 움직이기
			for (int i = obstacle.size() - 1; i >= 0; i--)
			{
				Obstacle o = obstacle.get(i);
				o.move(isFast);
				
				if (o.isDead)
					obstacle.remove(o);
			}
			
			// 코인 움직이기
			for (int i = coin.size() - 1; i >= 0; i--)
			{
				Coin c = coin.get(i);
				
				if (goldTime > 0)
				{
					c.img = imgCoin[Coin.GOLD];
					c.kind = Coin.GOLD;
				}
					
				if (magnetTime > 0 && c.x <= width - (width / 2) - c.w)
					c.move(isFast, me.x, me.y);
				else
					c.move(isFast);
				
				if (c.isDead)
					coin.remove(c);
			}
			
			// 아이템 움직이기
			for (int i = item.size() - 1; i >= 0; i--)
			{
				Item t = item.get(i);
				t.move(isFast);
				
				if (t.isDead)
					item.remove(t);
			}
			
			// 캐릭터 움직이기
			me.move(isFast);
			calItemTime();
			score += (isFast) ? 2 : 1;	// 이동 거리로 스코어를 계산
			setView();
		}
		
		void drawAll(Canvas canvas)
		{
			// 배경 그리기
			canvas.drawBitmap(imgBgSelected, move_backPos, 0, null);
			canvas.drawBitmap(imgBgSelected, width + move_backPos, 0, null);
			
			// 길 그리기
			for (int i = road.size() - 1; i >= 0; i--)
			{
				Road r = road.get(i);
				canvas.drawBitmap(r.img, r.x, r.y, null);
			}
			
			// 장애물 그리기
			for (Obstacle o : obstacle)
			{
				canvas.drawBitmap(o.img, o.x - o.w, o.y - o.h, null);
			}
			
			// 코인 그리기
			for (Coin c : coin)
			{
				canvas.drawBitmap(c.img, c.x - c.w, c.y - c.h, null);
			}
			
			// 아이템
			for (Item i : item)
			{
				canvas.drawBitmap(i.img, i.x - i.w, i.y - i.h, null);
			}
			
			// 캐릭터 그리기
			canvas.drawBitmap(me.img, me.x - me.w, me.y - me.h, null);
			
			// 버튼 그리기
			canvas.drawBitmap(btn_jump[isJump ? 1 : 0], btnJumpRect.left, btnJumpRect.top, null);			
			canvas.drawBitmap(btn_item[isItem ? 1 : 0], btnItemRect.left, btnItemRect.top, null);
		
			// TODO test
//			Log.i("score", score + "");
//			Log.i("coin", countCoin + "");
		}
		
		void checkCollision()
		{
			// 캐릭터가 길 위에 있는지 확인
			isOnRoad = false;
			if (!me.isJump && !me.isDoubleJump && !me.isInvincible)	// 점프 상태, 무적 상태가 아닐 때
			{
				for (Road r : road)
				{
					Rect rect = new Rect(r.x, r.y, r.x + r.w + me.w - 1, height);				
					isOnRoad = rect.contains(me.x, me.y + me.width);	// 길 위에 있는지 확인
					
					if (isOnRoad)
					{
						dropCount = 0;
						break;
					}
				}
				
				/** 게임오버 부분 **/
				if (!isOnRoad)	// 길 위에 없다면 = 추락
				{
					dropCount++;
					if (dropCount == 4)		// 작은 절벽에서 dropCount가 8번이기 때문에 반 이상 나갔을 시 추락
						me.isDrop = true;
				}
					
				if (me.y >= height + me.w)	// 화면 밖으로 캐릭터가 나갔을 시 게임오버
				{
					hp = 0;
					setView();
				}
			}
			
			// 장애물과 충돌 확인
			if (!me.isCollision && !me.isInvincible)
			{
				for (Obstacle o : obstacle)
				{
					Rect rect = new Rect(o.x - o.w, o.y - o.h, o.x + o.w, o.y + o.h);
					if (rect.contains(me.x, me.y))
					{
						isCollision = true;
						break;
					}
				}
				
				// square 6번, long 7번, wide 11 ~ 12번 충돌이 일어나므로 절반인 3을 카운트한다.
				
				if (isCollision)
				{
					collisionCount++;
					isCollision = false;
					
					if (collisionCount >= 3)
					{
						me.isCollision = true;
						collisionCount = 0;
						
						hp--;
						setView();
					}
				}
				else
					collisionCount = 0;
			}
			
			// GameOver 부분
			if (hp <= 0)
				gameOver();
			
			// 아이템과 충돌 확인
			for (Item i : item)
			{
				if (Math.pow(i.x - me.x, 2) + Math.pow(i.y - me.y, 2) <= Math.pow(i.w + me.w, 2))
				{
					i.isDead = true;
					actionItem(i.kind);
				}
			}
			
			// 코인과 충돌 확인
			for (Coin c : coin)
			{
				if (Math.pow(c.x - me.x, 2) + Math.pow(c.y - me.y, 2) <= Math.pow(c.w + me.h, 2))
				{
					c.isDead = true;
					// 먹은 코인의 종류에 따라 스코어를 달리한다.
					score += (c.kind == Coin.BRONZE) ? 1 : ((c.kind == Coin.SILVER) ? 2 : 4);
					countCoin += (c.kind == Coin.BRONZE) ? 1 : ((c.kind == Coin.SILVER) ? 2 : 4);
					setView();
				}	
			}
		}
		
		void actionItem(int kind)
		{
			switch(kind)
			{
			case Item.HP:
				if (hp == Player.HP[G.selectedChar]) return;
				
				hp++;
				setView();
				
				break;
				
			case Item.FAST:
				if (me.isBig) break;
				isFast = true;
				fastTime = 25 * 6;	// 6초간 적용
				me.isInvincible = true;
				
				break;
				
			case Item.MAGNET:
				magnetTime = 25 * 6;
				
				break;
				
			case Item.GEM:
				countGem++;
				setView();
				
				break;
				
			case Item.BIG:
				if (isFast) break;
				bigTime = 25 * 6;
				me.isBig = true;
				me.isInvincible = true;
				
				break;
				
			case Item.GOLD:
				goldTime = 25 * 60;
				break;
			}
		}
		
		// 아이템 적용시간을 계산하는 메소드
		void calItemTime()
		{
			if (fastTime == 0)
			{
				isFast = false;
				me.isInvincible = false;
			}
			if (me.isBig && bigTime == 0)
			{
				me.isBig = false;
				me.isInvincible = false;
				me.returnSize();
			}
			
			if (fastTime > 0) fastTime--;
			if (bigTime > 0) bigTime--;
			if (magnetTime > 0) magnetTime--;
			if (goldTime > 0) goldTime--;
		}
		
		// 레벨을 체크하는 메소드
		void checkLevel()
		{
			if (score > 5000 && score <= 10000)
				level = 1;					
			else if (score > 10000 && score <= 18000)
				level = 2;
			else if (score > 18000 && score <= 28000)
				level = 3;
			else if (score > 28000 && score <= 45000)
				level = 4;
			else if (score > 45000)
				level = 5;
		}
		
		void gameOver()
		{
//			 ( gameover 액티비티로 스코어, 코인 값을 넘긴다)
			float[] bonus = { 1, 1, 1.2f, 1.5f, 2f };
			countCoin = (int)(countCoin * bonus[G.selectedChar]);
			G.coin += countCoin;
			
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putInt("_coin", countCoin);
			bundle.putInt("_gem", countGem);
			bundle.putInt("_score", score);
			msg.setData(bundle);
			((GameActivity)mContext).handler.sendMessage(msg);
//			Log.i("gameOver", "gameOver 호출");
		}
		
		void setView()
		{
			post(new Runnable(){

				@Override
				public void run() {
					
					String str = null;
					GameActivity gameActivity = (GameActivity)mContext;
					
					str = String.format("%07d", score);
					gameActivity.text_score.setText(str);
					
					str = String.format("%07d", (score < G.record) ? G.record : score);
					gameActivity.text_champion.setText(str);
					
					str = String.format("%06d", countCoin);
					gameActivity.text_coin.setText(str);
					
					str = String.format("%04d", countGem);
					gameActivity.text_gem.setText(str);
					
					if(G.isSelectItem)
					{
						str = String.format("%02d", G.item_have[G.selectedItem]);
						gameActivity.text_item.setText(str);
					}
					
					for (int i = 0; i < gameActivity.img_hp.length; i++)
					{
						gameActivity.img_hp[i].setVisibility(View.VISIBLE);
					}
					
					for (int i = 0; i < 4 - hp; i++)
					{
						gameActivity.img_hp[3 - i].setVisibility(View.INVISIBLE);
					}
				}
			});
		}
				
		// 터치 이벤트 처리 메소드
		void onTouchDown(int x, int y)
		{
			if (isPause)return;
			if (btnJumpRect.contains(x, y))
			{
				isJump = true;
				if(me.isDoubleJump) return;
				
				// 점프 버튼을 누른 시간 차를 계산한다.
				downNow = System.currentTimeMillis();
				downGap = downNow - downBefore;
				downBefore = downNow;
				
				if (downGap < 190 && !me.isDrop)	// 떨어지는 중이 아니고, 시간 차가 짧다면
				{
					me.isDoubleJump = true;
				}
					
				else if (!me.isDrop)
					me.isJump = true;	
			}
				
			if (btnItemRect.contains(x, y))
			{
				isItem = true;
				
				// TODO 아이템 사용 부분
				// 아이템 사용은 중간에 나가도 사용 분량이 저장될 수 있게 saveData를 사용한다.
				if (G.item_have[G.selectedItem] > 0)
				{
					actionItem(G.selectedItem);
					G.item_have[G.selectedItem]--;
					setView();
				}	
			}		
		}
		
		void onTouchUp(int x, int y)
		{
			if (btnJumpRect.contains(x, y))
				isJump = false;
			
			if (btnItemRect.contains(x, y))
				isItem = false;
		}
		
		// FPS 관련 메소드
		void adjustFPS()
		{
			endTime = System.currentTimeMillis();
			timeGap = endTime - startTime;
			sleepTime = frameTime - timeGap;
			
			// 스레드가 그림 그리는 시간이 frameTime보다 빠른 경우
			if (sleepTime > 0)
			{
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
			// 스레드가 그림 그리는 시간이 frameTime보다 느린 경우
			else if (sleepTime < 0)
			{
				skipFrame = 0;
				while(sleepTime < 0 && skipFrame < 5)
				{
					makeAll();
					moveAll();
					checkCollision();
					checkLevel();
					
					skipFrame++;
					sleepTime += frameTime;
				}
			}
			
			startTime = endTime;
		}
		
		@Override
		public void run() 
		{
			Canvas canvas = null;
			me = new Player(imgPlayer, imgPlayerBig, imgPlayer[0][0].getWidth(), imgPlayer[0][0].getHeight(), height, imgRoad[0].getHeight(), G.selectedChar);
			
			startTime = System.currentTimeMillis();
			while(isRun)
			{
				canvas = mHolder.lockCanvas();
				
				try
				{
					synchronized(mHolder)
					{
						// 그림 그리는 작업 하는 부분		
						makeAll();
						moveAll();
						checkCollision();
						checkLevel();
						
						// FPS 조절
						adjustFPS();
						
						drawAll(canvas);
					}	
				} finally
				{
					if (canvas != null)
						mHolder.unlockCanvasAndPost(canvas);
				}
				
				if (isPause)
				{
					synchronized(this)
					{
						try {
							wait();
						} catch (InterruptedException e) {}
					}
				}
			}
			
			// while 문이 끝났으므로 종료 직전. 모든 자원을 제거한다.
			removeResources();
		}
		
		void pauseThread(boolean isPause)
		{
			this.isPause = isPause;
			
			synchronized(this)
			{
				this.notify();
			}
		}
		
		void stopThread()
		{
			isRun = false;
			
			synchronized(this)
			{
				this.notify();
			}
		}
	}
}
