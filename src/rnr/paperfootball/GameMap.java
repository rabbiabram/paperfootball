package rnr.paperfootball;

import java.util.Vector;

public class GameMap extends BaseGameMap {
	Vector<Cell> mCells;
	Vector<Link> mLinks;

	public static final int CELLS_COUNT = 99;
	public static final int CELLS_COL_COUNT = 11;
	public static final int CELLS_ROW_COUNT = 9;
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
		this.mLinks = new Vector<Link>(1000);

		for (int x = GameMap.INDEX_WIDTH_MIN; x < GameMap.CELLS_COL_COUNT; x++) {
			for (int y = GameMap.INDEX_HEIGHT_MIN; y < GameMap.CELLS_ROW_COUNT; y++) {
				this.mCells.add(new Cell(x, y));
			}
		}

		for (int x = GameMap.INDEX_WIDTH_MIN; x < GameMap.INDEX_WIDTH_MAX; x++) {
			Cell a0 = this.getCell(x, GameMap.INDEX_HEIGHT_MIN);
			Cell b0 = this.getCell(x + 1, GameMap.INDEX_HEIGHT_MIN);
			Cell a = this.getCell(x, GameMap.INDEX_HEIGHT_MAX);
			Cell b = this.getCell(x + 1, GameMap.INDEX_HEIGHT_MAX);

			this.mLinks.add(new Link(a0, b0));
			this.mLinks.add(new Link(a, b));
		}

		for (int y = GameMap.INDEX_HEIGHT_MIN; y < GameMap.INDEX_HEIGHT_MAX; y++) {

			if ((y < GameMap.INDEX_HEIGHT_CENTER - GameMap.GOAL_LINE_OFFSET) ||
					(y >= GameMap.INDEX_HEIGHT_CENTER + GameMap.GOAL_LINE_OFFSET)) {
				Cell a0 = this.getCell(GameMap.INDEX_WIDTH_MIN, y);
				Cell b0 = this.getCell(GameMap.INDEX_WIDTH_MIN, y + 1);
				Cell a1 = this.getCell(GameMap.INDEX_WIDTH_MAX, y);
				Cell b1 = this.getCell(GameMap.INDEX_WIDTH_MAX, y + 1);

				this.mLinks.add(new Link(a0, b0));
				this.mLinks.add(new Link(a1, b1));
			}

			Cell a2 = this.getCell(GameMap.INDEX_WIDTH_CENTER - GameMap.FRONT_LINE_OFFSET, y);
			Cell b2 = this.getCell(GameMap.INDEX_WIDTH_CENTER - GameMap.FRONT_LINE_OFFSET, y + 1);
			Cell a3 = this.getCell(GameMap.INDEX_WIDTH_CENTER, y);
			Cell b3 = this.getCell(GameMap.INDEX_WIDTH_CENTER, y + 1);
			Cell a4 = this.getCell(GameMap.INDEX_WIDTH_CENTER + GameMap.FRONT_LINE_OFFSET, y);
			Cell b4 = this.getCell(GameMap.INDEX_WIDTH_CENTER + GameMap.FRONT_LINE_OFFSET, y + 1);

			this.mLinks.add(new Link(a2, b2));
			this.mLinks.add(new Link(a3, b3));
			this.mLinks.add(new Link(a4, b4));
		}
	}

	public Cell getCell(int x, int y) {
		for (Cell cell : this.mCells) {
			if (cell.isLocate(x, y)) {
				return cell;
			}
		}
		return null;
	}

	public boolean isLinked(Cell a, Cell b) {
		for (Link link : this.mLinks) {
			if ((a.equals(link.getA()) && b.equals(link.getB())) ||
					(b.equals(link.getA()) && a.equals(link.getB()))) {
				return true;
			}
		}
		return false;
	}

}
