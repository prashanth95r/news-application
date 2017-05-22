package com.mvsrnews.model;

public class NavDrawerItem {

    private int icon;
	private String title;

	public NavDrawerItem() {
	}

	public NavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getIcon(){
        return this.icon;
    }
	
	public void setIcon(int icon){
        this.icon = icon;
    }

}
