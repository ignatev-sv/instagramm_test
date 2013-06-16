package com.example.instagramtest.items;

public class MainItem {
	private String url, title;
	
	public MainItem(String url) {
		this.url = url;
	}

	public MainItem(String url, String title) {
		this.url = url;
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
