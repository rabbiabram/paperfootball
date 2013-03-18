package com.rnr.paperfootball.map;

import java.util.Vector;

import com.rnr.paperfootball.base.BaseMap;
import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.Link;

public class Map extends BaseMap {
	protected Vector<Cell> mCells;
	protected Vector<Link> mLinks;
	protected Cell mCurrent;
	protected Vector<Cell> mGoals;

	@Override
	public Cell getCurrent() {
		return this.mCurrent;
	}

	public int CELLS_COL_COUNT() {
		return 11;
	}
	public int CELLS_ROW_COUNT() {
		return 9;
	}
	
	public int INDEX_WIDTH_MIN() {
		return 0;
	}
	public int INDEX_HEIGHT_MIN() { 
		return 0;
	}

	public int INDEX_WIDTH_MAX() {
		return CELLS_COL_COUNT() - INDEX_WIDTH_MIN() - 1;
	}
	public int INDEX_HEIGHT_MAX() {
		return CELLS_ROW_COUNT() - INDEX_HEIGHT_MIN() - 1;
	}
	public int CELLS_COUNT() {
		return CELLS_COL_COUNT() * CELLS_ROW_COUNT();
	}
	public int INDEX_WIDTH_CENTER() {
		return (INDEX_WIDTH_MAX() - INDEX_WIDTH_MIN()) / 2;
	}
	public int INDEX_HEIGHT_CENTER() {
		return (INDEX_HEIGHT_MAX() - INDEX_HEIGHT_MIN()) / 2;
	}

	public int FRONT_LINE_OFFSET = 2;
	public int GOAL_LINE_OFFSET = 1;

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
	public boolean hasLink(Cell a, Cell b) {
		for (Link link : this.mLinks) {
			if ((a.equals(link.getA()) && b.equals(link.getB())) ||
					(b.equals(link.getA()) && a.equals(link.getB()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean validate(Vector<Cell> path, boolean partial) {
		if (path == null) {
			return false;
		}

		Vector<Cell> innerPath = new Vector<Cell>();
		innerPath.add(this.mCurrent);
		innerPath.addAll(path);

		int pathSize = innerPath.size();

		if (pathSize < 2) {
			return false;
		}
		for (int i = 1; i < innerPath.size(); i++) {
			Cell a = innerPath.get(i - 1);
			Cell b = innerPath.get(i);
			// Уже были ходы между этими точками
			if (!this.validateLink(a, b)) {
				return false;
			}
			// Проверяем наличие колец в пути
			for (int ii = i + 1; ii < innerPath.size(); ii++) {
				Cell aa = innerPath.get(ii);
				Cell bb = innerPath.get(ii - 1);
				if ((a.equals(aa) && b.equals(bb)) ||
						(a.equals(bb) && b.equals(aa))) {
					return false;
				}
			}
			// Проверяем наличие точки в пути
			if ((i > 1) && !this.isLinked(a)) {
				return false;
			}
		}
		// Если путь полный, то последняя точка должна быть новая или точка гола или тупик.
		return partial || !this.isLinked(innerPath.lastElement()) || 
				(this.mGoals.indexOf(innerPath.lastElement()) != -1) || 
				(this.isStalemate(innerPath));
	}

	private int getMaxCountLink(Cell cell) {
		int maxCountLink = 8;
		int x = cell.getX();
		int y = cell.getY();
		
		
		if ((x == this.INDEX_WIDTH_MIN()) || (x == this.INDEX_WIDTH_MAX())) {
			maxCountLink -= 3;
		}
		
		if ((y == this.INDEX_HEIGHT_MIN()) || (y == this.INDEX_HEIGHT_MAX())) {
			maxCountLink -= 3;
		}
		
		if (maxCountLink == 2) {
			++maxCountLink;
		}
		
		return maxCountLink;
	}
	public boolean isStalemate(Cell cell) {
		return this.getLinkedCell(cell).size() == this.getMaxCountLink(cell);
	}
	private boolean isStalemate(Vector<Cell> innerPath) {
		Cell finish = innerPath.lastElement();

		int countLinkPath = 1;
		
		if (innerPath.firstElement().equals(finish)) {
			++countLinkPath;
		}
		
		for (int ind = 1; ind < innerPath.size() - 1; ind++) {
			if (innerPath.get(ind).equals(finish)) {
				countLinkPath += 2;
			}
		}
		int countLink = this.getLinkedCell(finish).size() + countLinkPath;
		return countLink == this.getMaxCountLink(finish);
	}

	private Vector<Cell> getLinkedCell(Cell cell) {
		Vector<Cell> linkedCells = new Vector<Cell>();
		for (Link link : this.mLinks) {
			if (cell.equals(link.getA())) {
				linkedCells.add(link.getB());
			} else if (cell.equals(link.getB())) {
				linkedCells.add(link.getA());
			}
		}
		return linkedCells;
	}
	private boolean isLinked(Cell a) {
		for (Link link : this.mLinks) {
			if (a.equals(link.getA()) ||
					a.equals(link.getB())) {
				return true;
			}
		}
		return false;
	}

	private boolean validateLink(Cell a, Cell b) {
		return !((a == null) || (b == null) ||
				this.hasLink(a, b) || (Math.abs(a.getX() - b.getX()) > 1) ||
				(Math.abs(a.getY() - b.getY()) > 1) ||
				(a.equals(b)));
	}

	@Override
	public boolean pavePath(Vector<Cell> path) {
		if (this.validate(path)) {
			for (Cell cell : path) {
				this.mLinks.add(new Link(this.mCurrent, cell));
				this.mCurrent = cell;
			}
			return true;
		}
		return false;
	}

	@Override
	public int getMinPlayersCount() {
		return this.mGoals.size();
	}

	@Override
	public int getIndexWinner() {
		return this.mGoals.indexOf(this.mCurrent);
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

			if ((y < this.INDEX_HEIGHT_CENTER() - this.GOAL_LINE_OFFSET) ||
					(y >= this.INDEX_HEIGHT_CENTER() + this.GOAL_LINE_OFFSET)) {
				Cell a0 = this.getCell(new Cell(this.INDEX_WIDTH_MIN(), y));
				Cell b0 = this.getCell(new Cell(this.INDEX_WIDTH_MIN(), y + 1));
				Cell a1 = this.getCell(new Cell(this.INDEX_WIDTH_MAX(), y));
				Cell b1 = this.getCell(new Cell(this.INDEX_WIDTH_MAX(), y + 1));

				this.mLinks.add(new Link(a0, b0));
				this.mLinks.add(new Link(a1, b1));
			}

			Cell a2 = this.getCell(new Cell(this.INDEX_WIDTH_CENTER() - this.FRONT_LINE_OFFSET, y));
			Cell b2 = this.getCell(new Cell(this.INDEX_WIDTH_CENTER() - this.FRONT_LINE_OFFSET, y + 1));
			Cell a3 = this.getCell(new Cell(this.INDEX_WIDTH_CENTER(), y));
			Cell b3 = this.getCell(new Cell(this.INDEX_WIDTH_CENTER(), y + 1));
			Cell a4 = this.getCell(new Cell(this.INDEX_WIDTH_CENTER() + this.FRONT_LINE_OFFSET, y));
			Cell b4 = this.getCell(new Cell(this.INDEX_WIDTH_CENTER() + this.FRONT_LINE_OFFSET, y + 1));

			this.mLinks.add(new Link(a2, b2));
			this.mLinks.add(new Link(a3, b3));
			this.mLinks.add(new Link(a4, b4));
		}
		this.mGoals.add(this.getCell(new Cell(this.INDEX_WIDTH_MAX(), this.INDEX_HEIGHT_CENTER())));
		this.mGoals.add(this.getCell(new Cell(this.INDEX_WIDTH_MIN(), this.INDEX_HEIGHT_CENTER())));
	}
}
