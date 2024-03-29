package com.fourfoureight.lolhelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.Build;

public class About extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.container);
        if (((GlobalVariables) this.getApplication()).getskin() == 1)
    	{
    		layout.setBackgroundResource(R.drawable.bg);
    	}
    	if (((GlobalVariables) this.getApplication()).getskin() == 2)
    	{
    		layout.setBackgroundResource(R.drawable.bg2);
    	}
    	String createdString = ((GlobalVariables) this.getApplication()).getCreatedByString();
    	String rightString = ((GlobalVariables) this.getApplication()).getAboutRolesRightString();
    	String versionString = ((GlobalVariables) this.getApplication()).getVersionString();
    	
    	TextView created = (TextView)findViewById(R.id.textView1);
    	TextView right = (TextView)findViewById(R.id.textView3);
    	TextView version = (TextView)findViewById(R.id.textView4);
    	
    	created.setText(createdString);
    	right.setText(rightString);
    	version.setText(versionString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
