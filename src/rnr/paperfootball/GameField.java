package rnr.paperfootball;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class GameField extends Activity {

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
        this.mGame.addPlayer(new BasePlayer());
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
