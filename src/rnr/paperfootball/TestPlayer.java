package rnr.paperfootball;

import java.util.Vector;

public class TestPlayer extends BasePlayer {

	@Override
	public Vector<Cell> Turn(BaseGameMap gameMap) {
		Vector<Cell> path = new Vector<Cell>();
		Cell start = gameMap.getCurrent();

//		path.add(start);

		path.add(gameMap.getCell(new Cell(start.getX() - 1, start.getY())));

		return path;
	}

}
