package com.mvsrnews;

import java.util.ArrayList;

import mvsrnews.library.Database;

import com.mvsrnews.adapter.NavDrawerListAdapter;
import com.mvsrnews.model.NavDrawerItem;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;

public class MainActivityND extends ActionBarActivity {
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    Database db= new Database(MainActivityND.this);
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_activity_main);
        mTitle = mDrawerTitle = getTitle();
 
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
 
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_drawer);
        
        navDrawerItems = new ArrayList<NavDrawerItem>();
        
        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(9, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons.getResourceId(10, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], navMenuIcons.getResourceId(11, -1)));
         
        navMenuIcons.recycle();
        
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      // getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.app_name, R.string.app_name ){
            @Override
			public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
 
            @Override
			public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        if (savedInstanceState == null) {
            // on first time display view for first nav item
           displayView(0);
        }
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }
    
    private class SlideMenuClickListener implements
    ListView.OnItemClickListener {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position,
        long id) {
    // display view for selected nav drawer item
    displayView(position);
}

}
    
   
    
   private void displayView(int position) {
	   
	         // update the main content by replacing fragments
        android.support.v4.app.Fragment fragment = null;
        Bundle args = new  Bundle();
        switch (position) {
        case 0:
            fragment = new MainActivity();
            break;
        case 1:
        	fragment=new BranchNews();
        	args.putString("branch1", "Office");
            fragment.setArguments(args);
            break;
        case 2:
        	fragment=new BranchNews();
        	args.putString("branch1", "ECE");
            fragment.setArguments(args);
            break;
        case 3:
        	fragment=new BranchNews();
        	args.putString("branch1", "CSE");
            fragment.setArguments(args);
            break;
        case 4:
        	fragment=new BranchNews();
        	args.putString("branch1", "IT");
            fragment.setArguments(args);
            break;
        case 5:
        	fragment=new BranchNews();
        	args.putString("branch1", "EEE");
            fragment.setArguments(args);
            break;
        case 6:
        	fragment=new BranchNews();
        	args.putString("branch1", "CIVIL");
            fragment.setArguments(args);
            break;
        case 7:
        	fragment=new BranchNews();
        	args.putString("branch1", "MECH");
            fragment.setArguments(args);
            break;
        case 8:
        	fragment=new BranchNews();
        	args.putString("branch1", "AUTO");
            fragment.setArguments(args);
            break;
        case 9:
        	fragment=new BranchNews();
        	args.putString("branch1", "MBA");
            fragment.setArguments(args);
            break;
        case 10:
        	fragment=new BranchNews();
        	args.putString("branch1", "Placements");
            fragment.setArguments(args);
            break;
        case 11:
        	fragment=new AboutFragment();
            break;
        
 
        default:
            break;
        }
 
     if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
 
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
        }
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
        case R.id.cleardata:
        	
        	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						Appdelete ap = new Appdelete();
						ap.clearApplicationData(getApplicationContext());
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						dialog.dismiss();
						break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage(
					"This will delete all the data in your Application. Are you Sure?")
					.setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();

            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.cleardata).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
        
    }
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
	
}
    
    
}