package com.example.instagramtest.adapters;


import java.util.List;
import java.util.zip.Inflater;

import utils.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instagramtest.R;
import com.example.instagramtest.items.MainItem;

public class MainAdapter extends BaseAdapter{
	private List<MainItem> items;
	private Context ctx;
	ImageLoader imageLoader;
	
	 public MainAdapter(Context ctx, List<MainItem> items) {
		this.items = items;
		this.ctx = ctx;
		this.imageLoader = new ImageLoader(ctx);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public MainItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup vg) {
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(com.example.instagramtest.R.layout.list_item, null);
		
		ImageView image = (ImageView)v.findViewById(R.id.image);
		
		TextView name = (TextView)v.findViewById(R.id.name);
		name.setText("Username: " + items.get(position).getTitle());
		imageLoader.DisplayImage(items.get(position).getUrl(), image);
		
		return v;
	}

}
