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

        this.setContentView(this.mGameFieldView);
        this.mGameFieldView.requestFocus();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
