package com.rnr.paperfootball.asketmap;

import java.util.Vector;

import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.Link;
import com.rnr.paperfootball.map.Map;

public class AsketMap extends Map {
	public static int CELLS_COL_COUNT = 21;
	public static int CELLS_ROW_COUNT = 11;
	
	public static int INDEX_WIDTH_MAX = CELLS_COL_COUNT - INDEX_WIDTH_MIN - 1;
	public static int INDEX_HEIGHT_MAX = CELLS_ROW_COUNT - INDEX_HEIGHT_MIN - 1;
	public static int CELLS_COUNT = CELLS_COL_COUNT * CELLS_ROW_COUNT;
	public static int INDEX_WIDTH_CENTER = (INDEX_WIDTH_MAX - INDEX_WIDTH_MIN) / 2;
	public static int INDEX_HEIGHT_CENTER = (INDEX_HEIGHT_MAX - INDEX_HEIGHT_MIN) / 2;

	@Override
	public void recreate() {
		this.mCells = new Vector<Cell>(AsketMap.CELLS_COUNT);
		this.mLinks = new Vector<Link>();
		this.mGoals = new Vector<Cell>();

		for (int x = AsketMap.INDEX_WIDTH_MIN; x < AsketMap.CELLS_COL_COUNT; x++) {
			for (int y = AsketMap.INDEX_HEIGHT_MIN; y < AsketMap.CELLS_ROW_COUNT; y++) {
				this.mCells.add(new Cell(x, y));
			}
		}

		for (int x = AsketMap.INDEX_WIDTH_MIN; x < AsketMap.INDEX_WIDTH_MAX; x++) {
			Cell a0 = this.getCell(new Cell(x, AsketMap.INDEX_HEIGHT_MIN));
			Cell b0 = this.getCell(new Cell(x + 1, AsketMap.INDEX_HEIGHT_MIN));
			Cell a = this.getCell(new Cell(x, AsketMap.INDEX_HEIGHT_MAX));
			Cell b = this.getCell(new Cell(x + 1, AsketMap.INDEX_HEIGHT_MAX));

			this.mLinks.add(new Link(a0, b0));
			this.mLinks.add(new Link(a, b));
		}

		this.mCurrent = this.getCell(new Cell(AsketMap.INDEX_WIDTH_CENTER, AsketMap.INDEX_HEIGHT_CENTER));

		for (int y = AsketMap.INDEX_HEIGHT_MIN; y < AsketMap.INDEX_HEIGHT_MAX; y++) {

			if ((y < AsketMap.INDEX_HEIGHT_CENTER - AsketMap.GOAL_LINE_OFFSET) ||
					(y >= AsketMap.INDEX_HEIGHT_CENTER + AsketMap.GOAL_LINE_OFFSET)) {
				Cell a0 = this.getCell(new Cell(AsketMap.INDEX_WIDTH_MIN, y));
				Cell b0 = this.getCell(new Cell(AsketMap.INDEX_WIDTH_MIN, y + 1));
				Cell a1 = this.getCell(new Cell(AsketMap.INDEX_WIDTH_MAX, y));
				Cell b1 = this.getCell(new Cell(AsketMap.INDEX_WIDTH_MAX, y + 1));

				this.mLinks.add(new Link(a0, b0));
				this.mLinks.add(new Link(a1, b1));
			}

		}
		this.mGoals.add(this.getCell(new Cell(AsketMap.INDEX_WIDTH_MAX, AsketMap.INDEX_HEIGHT_CENTER)));
		this.mGoals.add(this.getCell(new Cell(AsketMap.INDEX_WIDTH_MIN, AsketMap.INDEX_HEIGHT_CENTER)));
	}	
}
