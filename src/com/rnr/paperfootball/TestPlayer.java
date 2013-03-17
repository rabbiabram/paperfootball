package com.rnr.paperfootball;

import java.util.Vector;

import com.rnr.paperfootball.base.BaseMap;
import com.rnr.paperfootball.base.BasePlayer;
import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.PathGetter;

public class TestPlayer extends BasePlayer {

	public TestPlayer(String name, int color, PathGetter pathGetter) {
		super(name, color, pathGetter);
	}

	@Override
	public Vector<Cell> Turn(BaseMap gameMap) {
		Vector<Cell> path = new Vector<Cell>();
		Cell start = gameMap.getCurrent();

		path.add(gameMap.getCell(new Cell(start.getX() - 1, start.getY())));

		return path;
	}

	@Override
	public void stopTurn(BaseMap gameMap) {
	}

}
