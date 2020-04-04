package com.lastviolet.alienrunner;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Gallery.LayoutParams;
import android.widget.TabHost;

public class ShopActivity extends Activity 
{
	ViewPager pager_shop;
	TabHost tabHost;
	ArrayList<ProductDTO> items = new ArrayList<ProductDTO>();
	ArrayList<ProductDTO> aliens = new ArrayList<ProductDTO>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		
		pager_shop = (ViewPager)findViewById(R.id.viewPager_shop);
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();
		
		tabHost.addTab(tabHost.newTabSpec("Item").setIndicator("ITEM").setContent(R.id.tab01));
		tabHost.addTab(tabHost.newTabSpec("Alien").setIndicator("ALIEN").setContent(R.id.tab02));
	
		for (int i = 0; i < 2; i++)
		{
			tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = LayoutParams.MATCH_PARENT;
		}
		
		addItems();
		addAliens();
		
		pager_shop.setAdapter(new ShopPagerAdapter(items, aliens, getLayoutInflater()));
	}
	
	private void addItems()
	{
		items.add(new ProductDTO("HP 물약", R.drawable.item_01_hp, 5000));
		items.add(new ProductDTO("가속 로켓", R.drawable.item_02_fast, 3000));
		items.add(new ProductDTO("코인 자석", R.drawable.item_03_magnet, 2000));
		items.add(new ProductDTO("비비빅 알약", R.drawable.item_05_big_shop, 2500));
		items.add(new ProductDTO("골드 코인즈", R.drawable.item_06_gold_shop, 2500));
	}
	
	private void addAliens()
	{
		aliens.add(new ProductDTO("초록이", R.drawable.alien_02_shop, 100));
		aliens.add(new ProductDTO("분홍이", R.drawable.alien_03_shop, 200));
		aliens.add(new ProductDTO("파랑이", R.drawable.alien_04_shop, 300));
		aliens.add(new ProductDTO("노랑이", R.drawable.alien_05_shop, 350));
	}
	
	public void mOnClick (View v)
	{
		switch(v.getId())
		{
		
		}
	}
}
