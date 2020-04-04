package com.lastviolet.alienrunner;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopAdapter extends BaseAdapter 
{
	ArrayList<ProductDTO> list;
	LayoutInflater inflater;

	public ShopAdapter(ArrayList<ProductDTO> list, LayoutInflater inflater) 
	{
		this.list = list;
		this.inflater = inflater;
	}
	
	@Override
	public int getCount() 
	{
		return list.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if (convertView == null)
			convertView = inflater.inflate(R.layout.grid_shop, null);
		
		ProductDTO product = list.get(position);
		((ImageView)convertView.findViewById(R.id.img_product)).setImageResource(product.getImg());
		((TextView)convertView.findViewById(R.id.text_product_name)).setText(product.getName());
		((TextView)convertView.findViewById(R.id.text_product_price)).setText(product.getPrice() + "");
		
		return convertView;
	}
}
