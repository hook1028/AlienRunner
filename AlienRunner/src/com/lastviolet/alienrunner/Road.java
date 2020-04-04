package com.lastviolet.alienrunner;

import android.graphics.Bitmap;

public class Road 
{
	// ������ ���� ���� ������ ���ȭ
	public static final int GRASS = 0;
	public static final int SAND = 1;
	public static final int STONE = 2;
	public static final int CHOCO = 3;
	public static final int CAKE = 4;
	public static final int SNOW = 5;
	
	int width, height, w, h;	// �� �̹����� ��ü �ʺ�� ����
	int x, y;					// ���� ���� ��� ������ (�߽����� �ƴ�)
	int dx;						// ���� �̵� �ӵ�
	int fullHeight;				// GameView�� ��ü ����
	
	// ���� �ո� ����� �迭ȭ
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
	
	boolean isDead = false;		// ���� ȭ�鿡�� ��������� Ȯ���ϴ� ����
	Bitmap img;
	
	// ������ (��ü �ʺ�, ��ü ����, ���� �ʺ�, ���� ����, ����, ù �����ΰ� �ƴѰ�)
	public Road(Bitmap img, int x, int fullHeight, int width, int height) 
	{
		this.width = width;
		this.height = height;
		this.fullHeight = fullHeight;
		this.img = img;
		
		w = width / 2;
		h = height / 2;
		
		this.x = x;
		
		y = fullHeight - height;	// ��ü ���� - ���� ����
		dx = w / 3;					// ���� �̵� �ӵ�
	}
	
	public void move(boolean isFast)
	{
		x -= dx * (isFast ? 2 : 1);
		
		if (x <= -width)
			isDead = true;
	}
}
