package com.rnr.paperfootball.ui;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.rnr.paperfootball.Config;
import com.rnr.paperfootball.R;

public class MainActivity extends Activity implements OnClickListener {

	private List<Intent> mGames;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.main);

        Config.getInstance().setPreferences(this.getSharedPreferences("config", MODE_PRIVATE));

        this.findViewById(R.id.exit).setOnClickListener(this);
        this.findViewById(R.id.show_config).setOnClickListener(this);
        this.findViewById(R.id.new_game).setOnClickListener(this);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.exit:
    		this.finish();
    		break;
    	case R.id.show_config:
    		this.startActivity(new Intent(this, ConfigActivity.class));
    		break;
    	case R.id.new_game:
        	this.mGames = new ArrayList<Intent>();
        	this.mGames.add(new Intent(this, GameFieldActivity.class));
        	Intent intent = this.mGames.get(0);
        	Config.getInstance().read();
        	intent.putExtra(Config.P1_TYPE, Config.getInstance().getPlayer1Type());
        	intent.putExtra(Config.P1_NAME, Config.getInstance().getPlayer1Name());
        	intent.putExtra(Config.P1_COLOR, Config.getInstance().getPlayer1Color());

        	intent.putExtra(Config.P2_TYPE, Config.getInstance().getPlayer2Type());
        	intent.putExtra(Config.P2_NAME, Config.getInstance().getPlayer2Name());
        	intent.putExtra(Config.P2_COLOR, Config.getInstance().getPlayer2Color());

        	intent.putExtra(Config.MAP_TYPE, Config.getInstance().getMapType());

        	this.startActivity(intent);
            
            
    		break;
    	default:
    		break;
    }
}
    }
