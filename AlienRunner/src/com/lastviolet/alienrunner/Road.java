package com.lastviolet.alienrunner;

import android.graphics.Bitmap;

public class Road 
{
	// 레벨에 따른 길의 종류를 상수화
	public static final int GRASS = 0;
	public static final int SAND = 1;
	public static final int STONE = 2;
	public static final int CHOCO = 3;
	public static final int CAKE = 4;
	public static final int SNOW = 5;
	
	int width, height, w, h;	// 길 이미지의 전체 너비와 높이
	int x, y;					// 길의 왼쪽 상단 꼭지점 (중심점이 아님)
	int dx;						// 길의 이동 속도
	int fullHeight;				// GameView의 전체 높이
	
	// 길의 뚫린 모양을 배열화
	public static int[][] road_case =
		{	{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 0
			{ 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 1
			{ 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 2
			{ 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 3
			{ 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 4
			{ 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 5
			{ 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }  // 6
		//	 end ----------------------------------------------------- start	
		};
	
	boolean isDead = false;		// 길이 화면에서 사라졌는지 확인하는 변수
	Bitmap img;
	
	// 생성자 (전체 너비, 전체 높이, 길의 너비, 길의 높이, 순서, 첫 생성인가 아닌가)
	public Road(Bitmap img, int x, int fullHeight, int width, int height) 
	{
		this.width = width;
		this.height = height;
		this.fullHeight = fullHeight;
		this.img = img;
		
		w = width / 2;
		h = height / 2;
		
		this.x = x;
		
		y = fullHeight - height;	// 전체 높이 - 길의 높이
		dx = w / 3;					// 길의 이동 속도
	}
	
	public void move(boolean isFast)
	{
		x -= dx * (isFast ? 2 : 1);
		
		if (x <= -width)
			isDead = true;
	}
}
