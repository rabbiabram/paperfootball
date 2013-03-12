package com.rnr.paperfootball.ui;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	private List<Intent> mGames;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	this.mGames = new ArrayList<Intent>();
    	this.mGames.add(new Intent(this, GameFieldActivity.class));
        this.startActivity(this.mGames.get(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
