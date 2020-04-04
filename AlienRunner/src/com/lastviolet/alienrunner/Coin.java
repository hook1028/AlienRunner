package com.lastviolet.alienrunner;

import android.graphics.Bitmap;

public class Coin 
{
	public static final int BRONZE = 0;
	public static final int SILVER = 1;
	public static final int GOLD = 2;
	
	public static final int FRONT_FIRST = 3;
	public static final int FRONT_SECOND = 4;
	public static final int NFRONT_FIRST = 5;
	public static final int NFRONT_SECOND = 6;
	
	int x, y, w, h;
	int width, height;
	int dx;
	int kind;

	Bitmap[] imgs;
	Bitmap img;
	
	boolean isDead = false;
	
	public Coin(Bitmap[] imgs, int r, int sx, int fullHeight, int nHeight, int order, boolean isFirst, int kind) 
	{
		this.imgs = imgs;
		this.width = this.height = r;
		this.kind = kind;
		
		img = imgs[kind];
		
		w = width / 2;
		h = height / 2;
		
		x = sx + (isFirst ? 0 : width) + w;
		y = fullHeight - nHeight - h;
		
		dx = width / 3;
	}
	
	public void move(boolean isFast)
	{
		x -= dx * (isFast ? 2 : 1);
		
		if (x <= -w)
			isDead = true;
	}
	
	void move(boolean isFast, int mex, int mey)
	{
		double radian = Math.atan2(y - mey, mex - x);
		
		x = (int)(x + Math.cos(radian) * w * 2);
		y = (int)(y - Math.sin(radian) * w * 2);
	}
}
