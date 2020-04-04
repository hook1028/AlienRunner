package com.lastviolet.alienrunner;

import android.graphics.Bitmap;
import android.util.Log;

public class Player
{
	// 상황에 맞는 플레이어 이미지를 저장한 상수
	static final int HIT = 0;
	static final int JUMP_01 = 1;
	static final int JUMP_02 = 2;
	static final int WALK_01 = 3;
	static final int WALK_02 = 4;
	static final int[] HP = { 2, 3, 3, 3, 4 };
	
	int width, height;			// 플레이어 캐릭터의 너비, 높이
	int x, y, dx, dy, y0, x0;		// 플레이어 캐릭터의 중심점, 이동속도, 원래 y위치
	int w, h, w0, h0;	
	int loop = 0;				// 움직임 속도를 담당하는 변수
	int jumpCount = 0;			// 점프 카운트를 세는 변수
	int doubleJumpCount = 0;	// 더블 점프 카운트를 세는 변수
	int collisionCount = 8;		// 충돌 시 무적 상태를 카운트 하는 변수
	int kind;					// 캐릭터의 종류
	int move = 0;				// 캐릭터의 걷는 모습을 조절하는 변수
	int size = 0;				// 캐릭터가 커질 시 사이즈를 지정하는 변수
	
	// 점프 시 dy를 계산하는데 사용되는 변수
	float[] jump = { 1.0f, 0.6f, 0.3f, 0.1f, 0, 0, -0.1f, -0.3f, -0.6f, -1.0f };	// 10
	float[] doubleJump = { 1.0f, 0.6f, 0.3f, 0.1f, 0, 0, -0.2f, -0.6f, -1.2f, -2.0f };	// 10
	
	// 상태를 확인하는 변수
	boolean isBig = false;
	boolean isJump = false;
	boolean isDoubleJump = false;
	boolean isDrop = false;
	boolean isCollision = false;
	boolean isInvincible = false;	// 무적 상태를 확인하는 변수
	
	// 이미지
	Bitmap[][] imgs;
	Bitmap[][] imgsBig;
	Bitmap img;
	
	// 생성자 (캐릭터의 총 이미지, 캐릭터의 너비, 캐릭터의 높이, 전체 높이, 길의 높이, 캐릭터 종류) 
	public Player(Bitmap[][]imgs, Bitmap[][]imgsBig, int pWidth, int pHeight, int fHeight, int rHeight, int kind) 
	{
		this.imgs = imgs;
		this.imgsBig = imgsBig;
		this.kind = kind;
		img = imgs[kind][WALK_01];	// 걷는 첫 모습으로 초기화
		
		width = pWidth;
		height = pHeight;
		
		w = width / 2;
		h = height / 2;
		
		x = rHeight + w;			// 길의 높이 + w로 중심점 설정
		y = fHeight - rHeight - h;	// 전체 높이 - 길의 높이 - h로 중심점 설정
		y0 = y;
		x0 = x;
		w0 = w;
		h0 = h;
	}
	
	public void move(boolean isFast)
	{
		loop++;
		if (loop % (isFast ? 2 : 3) == 0)
		{	
			if (isBig)
			{
				if (size < imgsBig[kind].length - 2)
				{
					img = imgsBig[kind][size];
					size++;
					y = y0 + h - img.getHeight();
					x = x - w + img.getWidth() / 2;
					h = img.getHeight() / 2;
					w = img.getWidth() / 2;
				}
				else
				{
					size = (size == 3) ? 4 : 3;
					img = imgsBig[kind][size];
				}
			}
					
			if (!isJump && !isDrop && !isCollision && !isBig)	// 걷고 있을 때
			{
				move = 1 - move;
				img = imgs[kind][WALK_01 + move];	
			}
			else if (isDrop)		// 떨어질 때
			{
				drop();
				return;
			}
		}
		
		if (isJump && !isBig)
			jump();
		if (isDoubleJump && !isBig)
			doubleJump();
		if (isCollision)
			collision();	
	} // end of jump
	
	private void jump()
	{
		if(isDoubleJump)			// 더블점프 중일 시 점프 메소드는 호출되지 않도록 한다.
		{
			isJump = false;
			return;
		}
		
		img = imgs[kind][JUMP_01];	// 뛰는 이미지 설정

		dy = (int)(h * jump[jumpCount]);
		y -= dy;
		jumpCount++;
			
		if (jumpCount == 10)		// 점프 동작이 모두 끝나면 원 상태로 돌린다.
		{	
			if (y >= y0)			// 점프 후 dy의 오차 때문에 캐릭터가 아래로 꺼졌을 시
				y = y0;				// 길 위로 돌려놓는다.
			
			jumpCount = 0;			// 점프 카운트 초기화
			isJump = false;			// 점프 종료
			if (jumpCount == 10)	// 걷는 이미지로 복구
				img = imgs[kind][WALK_01];	
		}					
	}
	
	private void doubleJump()
	{
		img = imgs[kind][(doubleJumpCount > 7) ? JUMP_01 : JUMP_02];	// 이미지 두 개로 표현
		
		if(jumpCount < 4)	// 점프에서 뛰는 동작이 끝나지 않았을 시 마저 뛰고 2단 점프 실행
		{
			y -= (int)(h * jump[jumpCount]);
			jumpCount++;
		}
		
		dy = (int)(h * doubleJump[doubleJumpCount]);
		y -= dy;
		doubleJumpCount++;
				
		if (doubleJumpCount == 10)		// 더블 점프 동작이 모두 끝나면 원 상태로 돌린다.
		{
			if (y >= y0)				// 더블 점프 시 float값에 의한 오차를 조정
				y = y0;
			
			jumpCount = 0;				// 점프 카운터 및 더블 점프 카운터 초기화
			doubleJumpCount = 0;
			isDoubleJump = false;		// 더블 점프 완료
			img = imgs[kind][WALK_01];	// 걷는 이미지로 복구
		}
	} // end of doubleJump
	
	private void collision()
	{
		img = imgs[kind][HIT];
		isInvincible = true;
		collisionCount--;
	
		// 충돌시 플레이어의 체력을 -1 한다.
		
		// 충돌 후 8타임동안은 무적상태로 만들어준다. (이미지 변경)
		if (collisionCount == 0)
		{
			img = imgs[kind][WALK_01];
			isCollision = false;
			isInvincible = false;
			collisionCount = 8;
		}
	}
	
	public void returnSize()
	{
		img = imgs[kind][WALK_01];
		y = y0;
		x = x0;
		h = img.getHeight() / 2;
		w = img.getWidth() / 2;
		size = 0;
	}
	public void drop()
	{
		img = imgs[kind][HIT];		// 떨어지는 이미지로 바꿈
		
		dx = (w / 3) * 2;			// 길과 같이 이동하기 위해 dx 설정
		dy = h;
		
		x -= dx;
		y += dy;
	}
}
