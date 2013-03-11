package com.rnr.paperfootball.ui;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	private List<Intent> mGames;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	Log.d("paper", "onCreate");
        this.mGames = (List<Intent>)this.getLastNonConfigurationInstance();
        if (this.mGames == null) {
        	Log.d("paper", "Empty");
        	this.mGames = new ArrayList<Intent>();
        	this.mGames.add(new Intent(this, GameFieldActivity.class));
        }
        this.startActivity(this.mGames.get(0));
//        this.setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
		return this.mGames;
    	
    }
}
