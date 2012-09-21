package com.rnr.paperfootball.ui;

import com.rnr.paperfootball.LocalPlayer;
import com.rnr.paperfootball.TestPlayer;
import com.rnr.paperfootball.base.BaseMapBuilder;
import com.rnr.paperfootball.core.Game;
import com.rnr.paperfootball.core.InsufficientPlayersException;
import com.rnr.paperfootball.map.MapBuilder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class GameFieldActivity extends Activity {

	private Game mGame;
	private BaseMapBuilder mGameBuilder;
	private GameFieldView mGameFieldView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mGameBuilder = new MapBuilder();
        this.mGame = new Game(this.mGameBuilder.createMap());

        this.mGameFieldView = new GameFieldView(this);
        this.mGameFieldView.setMapPainter(this.mGameBuilder.createMapPainter(this.mGame.getMap()));
        this.mGame.getMap().addHandler(this.mGameFieldView);

        this.setContentView(this.mGameFieldView);
        this.mGameFieldView.requestFocus();

        this.mGame.addPlayer(new TestPlayer());
        this.mGame.addPlayer(new LocalPlayer());

        try {
			this.mGame.startGame();
		} catch (InsufficientPlayersException e) {
			e.printStackTrace();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
