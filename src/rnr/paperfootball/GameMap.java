package rnr.paperfootball;

import java.util.Vector;

public class GameMap extends BaseGameMap {
	Vector<Cell> mCells;

	public static final int CELLS_COUNT = 99;
	public static final int CELLS_ROW_COUNT = 11;
	public static final int CELLS_COL_COUNT = 9;
	public static final int INDEX_WIDTH_MIN = 0;
	public static final int INDEX_WIDTH_MAX = 10;
	public static final int INDEX_WIDTH_CENTER = (INDEX_WIDTH_MAX - INDEX_WIDTH_MIN) / 2;
	public static final int INDEX_HEIGHT_MIN = 0;
	public static final int INDEX_HEIGHT_MAX = 8;
	public static final int INDEX_HEIGHT_CENTER = (INDEX_HEIGHT_MAX - INDEX_HEIGHT_MIN) / 2;
	public static final int FRONT_LINE_OFFSET = 2;
	public static final int GOAL_LINE_OFFSET = 1;

	public GameMap() {
		this.mCells = new Vector<Cell>(GameMap.CELLS_COUNT);

		for (int i = 0; i < GameMap.CELLS_COUNT; i++) {
			int relation = Cell.EMPTY;

			if (i < GameMap.CELLS_ROW_COUNT) {
				relation |= Cell.LEFT | Cell.UP_LEFT | Cell.UP |
						Cell.UP_RIGHT | Cell.RIGHT;
			}
			if (i >= GameMap.CELLS_COUNT -  GameMap.CELLS_ROW_COUNT) {
				relation |= Cell.LEFT | Cell.DOWN_LEFT | Cell.DOWN |
						Cell.DOWN_RIGHT | Cell.RIGHT;
			}
			if (i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_MIN) {
				relation |= Cell.DOWN | Cell.DOWN_LEFT | Cell.LEFT |
						Cell.UP_LEFT | Cell.UP;
			}
			if (i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_MAX) {
				relation |= Cell.UP | Cell.UP_RIGHT | Cell.RIGHT |
						Cell.DOWN_RIGHT | Cell.DOWN;
			}
			if ((i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_CENTER - FRONT_LINE_OFFSET) ||
					(i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_CENTER) ||
					(i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_CENTER + FRONT_LINE_OFFSET)) {
				relation |= Cell.UP | Cell.DOWN;
			}

			if ((i / GameMap.CELLS_ROW_COUNT == GameMap.INDEX_HEIGHT_CENTER) &&
					((i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_MIN) ||
							(i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_MAX))) {
				relation = Cell.EMPTY;
			}
			if ((i / GameMap.CELLS_ROW_COUNT == GameMap.INDEX_HEIGHT_CENTER - GameMap.GOAL_LINE_OFFSET) &&
					((i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_MIN) ||
							(i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_MAX))) {
				relation -= Cell.DOWN;
			}
			if ((i / GameMap.CELLS_ROW_COUNT == GameMap.INDEX_HEIGHT_CENTER + GameMap.GOAL_LINE_OFFSET) &&
					((i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_MIN) ||
							(i % GameMap.CELLS_ROW_COUNT == GameMap.INDEX_WIDTH_MAX))) {
				relation -= Cell.UP;
			}
			this.mCells.add(new Cell(relation));
		}
	}

	public Vector<Cell> getCells() {
		return this.mCells;
	}

}
