package com.lastviolet.alienrunner;

import java.util.Random;

import android.graphics.Bitmap;

public class Item 
{
	public static final int HP = 0;
	public static final int FAST = 1;
	public static final int MAGNET = 2;
	public static final int GEM = 3;
	public static final int BIG = 4;
	public static final int GOLD = 5;
	
	int x, y, w, h;
	int width, height;
	int dx;
	int kind;
	
	Bitmap[] imgAll;
	Bitmap img;
	Random rnd;
	
	boolean isDead = false;
	
	public Item(Bitmap[] imgAll, int r, int sx, int fullHeight, int nHeight)
	{
		this.imgAll = imgAll;
		this.width = this.height = r;
		rnd = new Random();
		
		w = h = width / 2;		
		x = sx + w;
		y = fullHeight - nHeight - h;
		dx = w / 3;
		
		int n = rnd.nextInt(20);
		// hp: 15%, fast: 20%, magnet: 40%, gem: 5%, big: 20%
		kind = (n < 3) ? 0 : (n < 7) ? 1 : (n < 15) ? 2 : (n < 16) ? 3 : 4;
		img = imgAll[kind];
	}
	
	public void move(boolean isFast)
	{
		x -= dx * (isFast ? 2 : 1);
		
		if (x <= -w)
			isDead = true;
	}
}
