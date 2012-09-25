package com.rnr.paperfootball.ui;

import com.rnr.paperfootball.LocalPlayer;
import com.rnr.paperfootball.LocalPlayerController;
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
        this.mGameFieldView.setGame(this.mGame);
        this.mGameFieldView.setMapPainter(this.mGameBuilder.createMapPainter(this.mGame.getMap()));
        this.mGame.addHandler(this.mGameFieldView);

        LocalPlayerController playerController = new LocalPlayerController();

        this.mGameFieldView.addHandler(playerController);

        this.mGame.addPlayer(new LocalPlayer("Player 1", playerController));
        this.mGame.addPlayer(new LocalPlayer("Player 2", playerController));

        this.setContentView(this.mGameFieldView);
        this.mGameFieldView.requestFocus();

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
