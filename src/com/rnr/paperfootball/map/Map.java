package com.rnr.paperfootball.map;

import java.util.Vector;

import com.rnr.paperfootball.base.BaseMap;
import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.GameCallback;
import com.rnr.paperfootball.core.Link;

public class Map extends BaseMap {
	Vector<Cell> mCells;
	Vector<Link> mLinks;
	Cell mCurrent;

	@Override
	public Cell getCurrent() {
		return this.mCurrent;
	}

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

	public Map() {
		this.mCells = new Vector<Cell>(Map.CELLS_COUNT);
		this.mLinks = new Vector<Link>();

		for (int x = Map.INDEX_WIDTH_MIN; x < Map.CELLS_COL_COUNT; x++) {
			for (int y = Map.INDEX_HEIGHT_MIN; y < Map.CELLS_ROW_COUNT; y++) {
				this.mCells.add(new Cell(x, y));
			}
		}

		for (int x = Map.INDEX_WIDTH_MIN; x < Map.INDEX_WIDTH_MAX; x++) {
			Cell a0 = this.getCell(new Cell(x, Map.INDEX_HEIGHT_MIN));
			Cell b0 = this.getCell(new Cell(x + 1, Map.INDEX_HEIGHT_MIN));
			Cell a = this.getCell(new Cell(x, Map.INDEX_HEIGHT_MAX));
			Cell b = this.getCell(new Cell(x + 1, Map.INDEX_HEIGHT_MAX));

			this.mLinks.add(new Link(a0, b0));
			this.mLinks.add(new Link(a, b));
		}

		this.mCurrent = this.getCell(new Cell(Map.INDEX_WIDTH_CENTER, Map.INDEX_HEIGHT_CENTER));

		for (int y = Map.INDEX_HEIGHT_MIN; y < Map.INDEX_HEIGHT_MAX; y++) {

			if ((y < Map.INDEX_HEIGHT_CENTER - Map.GOAL_LINE_OFFSET) ||
					(y >= Map.INDEX_HEIGHT_CENTER + Map.GOAL_LINE_OFFSET)) {
				Cell a0 = this.getCell(new Cell(Map.INDEX_WIDTH_MIN, y));
				Cell b0 = this.getCell(new Cell(Map.INDEX_WIDTH_MIN, y + 1));
				Cell a1 = this.getCell(new Cell(Map.INDEX_WIDTH_MAX, y));
				Cell b1 = this.getCell(new Cell(Map.INDEX_WIDTH_MAX, y + 1));

				this.mLinks.add(new Link(a0, b0));
				this.mLinks.add(new Link(a1, b1));
			}

			Cell a2 = this.getCell(new Cell(Map.INDEX_WIDTH_CENTER - Map.FRONT_LINE_OFFSET, y));
			Cell b2 = this.getCell(new Cell(Map.INDEX_WIDTH_CENTER - Map.FRONT_LINE_OFFSET, y + 1));
			Cell a3 = this.getCell(new Cell(Map.INDEX_WIDTH_CENTER, y));
			Cell b3 = this.getCell(new Cell(Map.INDEX_WIDTH_CENTER, y + 1));
			Cell a4 = this.getCell(new Cell(Map.INDEX_WIDTH_CENTER + Map.FRONT_LINE_OFFSET, y));
			Cell b4 = this.getCell(new Cell(Map.INDEX_WIDTH_CENTER + Map.FRONT_LINE_OFFSET, y + 1));

			this.mLinks.add(new Link(a2, b2));
			this.mLinks.add(new Link(a3, b3));
			this.mLinks.add(new Link(a4, b4));
		}
	}

	// TODO: возможно, лучше параметром выделить Cell и перемести в родительский класс. искать equals
	@Override
	public Cell getCell(Cell cell) {
		for (Cell originCell : this.mCells) {
			if (originCell.equals(cell)) {
				return originCell;
			}
		}
		return null;
	}

	@Override
	public boolean isLinked(Cell a, Cell b) {
		for (Link link : this.mLinks) {
			if ((a.equals(link.getA()) && b.equals(link.getB())) ||
					(b.equals(link.getA()) && a.equals(link.getB()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean validate(Vector<Cell> path) {
		if (path == null) {
			return false;
		}

		int pathSize = path.size();

		if (pathSize < 1) {
			return false;
		}
		if (this.isLinked(this.mCurrent, path.get(0))) {
			return false;
		}
		for (int i = 1; i < path.size(); i++) {
			if (this.isLinked(path.get(i - 1), path.get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void pavePath(Vector<Cell> path) {
		if (this.validate(path)) {
			for (Cell cell : path) {
				this.mLinks.add(new Link(this.mCurrent, cell));
				this.mCurrent = cell;
			}

			for (GameCallback callback : this.mHandlers) {
				callback.repaint(this);
			}

		}
	}

}