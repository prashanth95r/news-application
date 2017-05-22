package com.mvsrnews;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BaseActivity extends Activity {
	
	public DrawerLayout drawerLayout;
    public ListView drawerList;
    public String[] layers;
    private ActionBarDrawerToggle drawerToggle;

    @Override
	protected void onCreate(Bundle savedInstanceState)//,int resLayoutID)
    {
    	
    	 super.onCreate(savedInstanceState);
    	//    setContentView(resLayoutID);
        // R.id.drawer_layout should be in every activity with exactly the same id.
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, 0, 0) 
        {
            @Override
			public void onDrawerClosed(View view) 
            {
            	super.onDrawerClosed(view);
                getActionBar().setTitle(R.string.app_name);
            }

            @Override
			public void onDrawerOpened(View drawerView) 
            {
            	super.onDrawerOpened(drawerView);
                getActionBar().setTitle(R.string.app_name);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    //    getActionBar().setHomeButtonEnabled(true);

        layers = getResources().getStringArray(R.array.navigation_drawer_items_array);
        drawerList = (ListView) findViewById(R.id.list_drawer);
  //      View header = getLayoutInflater().inflate(R.layout.lv_activity_main, null);
    //    drawerList.addHeaderView(header, null, false);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.listview_row,android.R.id.title, 
              layers ));
     //   View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
     //           R.layout.drawer_list_footer, null, false);
     //   drawerList.addFooterView(footerView);

        drawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
           //     map.drawerClickEvent(pos);
           	String selectedValue = (String) drawerList.getAdapter().getItem(pos);
                Toast.makeText(getBaseContext(), selectedValue, Toast.LENGTH_SHORT).show();
            //	Intent i=new Intent(getApplicationContext(),NewActivity.class);
            //	startActivity(i);
            	
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
          drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}
