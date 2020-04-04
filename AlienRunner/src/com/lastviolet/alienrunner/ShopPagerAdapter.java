package com.lastviolet.alienrunner;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class ShopPagerAdapter extends PagerAdapter 
{
	LayoutInflater inflater;
	ArrayList<ProductDTO> items;
	ArrayList<ProductDTO> aliens;
	
	public ShopPagerAdapter(ArrayList<ProductDTO> items, ArrayList<ProductDTO> aliens, LayoutInflater inflater) 
	{
		this.items = items;
		this.aliens = aliens;
		this.inflater = inflater;
	}
	
	@Override
	public int getCount() 
	{
		return 2;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) 
	{
		View view = inflater.inflate(R.layout.pager_shop, null);
		GridView grid_shop = (GridView)view.findViewById(R.id.grid_shop);
		ShopAdapter adapter = null;
		
		switch(position)
		{
		case 0:
			adapter = new ShopAdapter(items, inflater);
			grid_shop.setAdapter(adapter);
			grid_shop.getLayoutParams().width = 250 * items.size();
			grid_shop.setNumColumns(items.size());
			break;
			
		case 1:
			adapter = new ShopAdapter(aliens, inflater);
			grid_shop.setAdapter(adapter);
			grid_shop.getLayoutParams().width = 250 * aliens.size();
			grid_shop.setNumColumns(aliens.size());
			break;
		}
		
		container.addView(view);
		
		return view;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) 
	{
		return arg0 == arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) 
	{
		container.removeView((View)object);
	}
}
