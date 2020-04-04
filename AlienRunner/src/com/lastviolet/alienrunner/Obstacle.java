package com.lastviolet.alienrunner;

import java.util.Random;

import android.graphics.Bitmap;
import android.util.Log;

public class Obstacle 
{
	// 장애물 종류 정할 변수
	public static final int SQUARE = 1;
	public static final int LONG = 2;
	public static final int WIDE = 3;
	
	int x, y;
	int w, h, width, height;
	int dx;
	
	boolean isDead = false;
	
	Bitmap img;
	Bitmap imgAll[];
	Random rnd;
	
	// 장애물의 위치를 배열화 (square = 1, long = 2, wide = 3)
	// 레벨 5는 전체 랜덤, 레벨 6은 레벨 4, 5만 랜덤
	public static int[][] obstacle_case =
		{	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },	// 00 - 1
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },	// 01 - 1
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },	// 02 - 1
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },	// 03 - 1
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },	// 04 - 1
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0 },	// 05 - 2
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0 },	// 06 - 2
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },	// 07 - 2
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0 },	// 08 - 2
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },	// 09 - 2
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 },	// 10 - 3
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0 },	// 11 - 3
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0 },	// 12 - 3
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0, 0, 0, 0 },	// 13 - 3
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0 },	// 14 - 3
			{ 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 2, 0, 0, 1, 0, 0, 0 },	// 15 - 4
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 3, 0, 0, 2, 0, 0, 0, 0 },	// 16 - 4
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0 },	// 17 - 4
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 3, 0, 0, 0, 0 },	// 18 - 4
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },	// 19 - 4
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0 },	// 20 - 5
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0 }, 	// 21 - 5 (square only)
			{ 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0 },	// 22 - 5
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0 },	// 23 - 5
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 1, 0, 1, 0, 0, 0 },	// 24 - 5
//			 end ----------------------------------------------------- start
		};
	
	private int[][] patternObstacleLong = {{0}, {1}, {2}, {3, 4, 5}, {3, 4, 5}, {6}};
	private int[][] patternObstacleSquare = {{0, 1}, {2, 3}, {4, 5}, {6, 7, 8}, {6, 7, 8}, {9, 10, 11}};
	private int[] patternObstacleWide = { 0, 1, 1, 2 };
	
	public Obstacle(Bitmap[] imgAll, int sx, int fullHeight, int width, int height, int rHeight, int widthBasic, int kind, int level) 
	{
		this.width = width;
		this.height = height;
		rnd = new Random();
	
		w = width / 2;
		h = height / 2;
		
		x = sx + w;
		y = fullHeight - rHeight - h;
		
		dx = widthBasic / 6;
		
		int n;
		
		switch(kind)
		{
		case SQUARE:
			img = imgAll[patternObstacleSquare[level][rnd.nextInt(patternObstacleSquare[level].length)]];
			break;
			
		case LONG:
			n = patternObstacleLong[level][rnd.nextInt(patternObstacleLong[level].length)];
			img = imgAll[n];
			break;
			
		case WIDE:
			img = imgAll[patternObstacleWide[level - 2]];
			break;
		}
	}
	
	public void move(boolean isFast)
	{
		x -= dx * (isFast ? 2 : 1);
		
		if (x <= -w)
			isDead = true;
	}
}
