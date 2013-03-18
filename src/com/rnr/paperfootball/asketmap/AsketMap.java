package com.rnr.paperfootball.asketmap;

import java.util.Vector;

import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.Link;
import com.rnr.paperfootball.map.Map;

public class AsketMap extends Map {
	@Override
	public int CELLS_COL_COUNT(){ 
		return 21;
	}
	@Override
	public int CELLS_ROW_COUNT() {
		return 11;
	}
	
	@Override
	public void recreate() {
		this.mCells = new Vector<Cell>(this.CELLS_COUNT());
		this.mLinks = new Vector<Link>();
		this.mGoals = new Vector<Cell>();

		for (int x = this.INDEX_WIDTH_MIN(); x < this.CELLS_COL_COUNT(); x++) {
			for (int y = this.INDEX_HEIGHT_MIN(); y < this.CELLS_ROW_COUNT(); y++) {
				this.mCells.add(new Cell(x, y));
			}
		}

		for (int x = this.INDEX_WIDTH_MIN(); x < this.INDEX_WIDTH_MAX(); x++) {
			Cell a0 = this.getCell(new Cell(x, this.INDEX_HEIGHT_MIN()));
			Cell b0 = this.getCell(new Cell(x + 1, this.INDEX_HEIGHT_MIN()));
			Cell a = this.getCell(new Cell(x, this.INDEX_HEIGHT_MAX()));
			Cell b = this.getCell(new Cell(x + 1, this.INDEX_HEIGHT_MAX()));

			this.mLinks.add(new Link(a0, b0));
			this.mLinks.add(new Link(a, b));
		}

		this.mCurrent = this.getCell(new Cell(this.INDEX_WIDTH_CENTER(), this.INDEX_HEIGHT_CENTER()));

		for (int y = this.INDEX_HEIGHT_MIN(); y < this.INDEX_HEIGHT_MAX(); y++) {
				Cell a0 = this.getCell(new Cell(this.INDEX_WIDTH_MIN(), y));
				Cell b0 = this.getCell(new Cell(this.INDEX_WIDTH_MIN(), y + 1));
				Cell a1 = this.getCell(new Cell(this.INDEX_WIDTH_MAX(), y));
				Cell b1 = this.getCell(new Cell(this.INDEX_WIDTH_MAX(), y + 1));

				this.mLinks.add(new Link(a0, b0));
				this.mLinks.add(new Link(a1, b1));
		}
		this.mGoals.add(this.getCell(new Cell(this.INDEX_WIDTH_MAX(), this.INDEX_HEIGHT_CENTER())));
		this.mGoals.add(this.getCell(new Cell(this.INDEX_WIDTH_MIN(), this.INDEX_HEIGHT_CENTER())));
	}	
}
