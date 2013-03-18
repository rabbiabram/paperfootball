package com.rnr.paperfootball.base;

import java.util.Vector;

import com.rnr.paperfootball.core.Cell;

// TODO: Сделать дженериком,в качестве параметра Cell
public abstract class BaseMap {
	public abstract boolean hasLink(Cell a, Cell b);
	public abstract boolean validate(Vector<Cell> path, boolean partial);
	public abstract boolean pavePath(Vector<Cell> path);
	public abstract Cell getCurrent();
	public abstract Cell getCell(Cell cell);
	public abstract int getMinPlayersCount();
	public abstract int getIndexWinner();
	public abstract boolean isStalemate(Cell cell);
	public abstract void recreate();
	public boolean validate(Vector<Cell> path) {
		return this.validate(path, false);
	}

	public BaseMap() {
		this.recreate();
	}
}
