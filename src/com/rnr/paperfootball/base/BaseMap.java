package com.rnr.paperfootball.base;

import java.util.Vector;

import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.GameCallback;

// TODO: Сделать дженериком,в качестве параметра Cell
public abstract class BaseMap {
	public abstract boolean isLinked(Cell a, Cell b);
	public abstract boolean validate(Vector<Cell> path);
	public abstract void pavePath(Vector<Cell> path);
	public abstract Cell getCurrent();
	public abstract Cell getCell(Cell cell);

	protected Vector<GameCallback> mHandlers;

	public BaseMap() {
		this.mHandlers = new Vector<GameCallback>();
	}

	public void addHandler(GameCallback callback) {
		this.mHandlers.add(callback);
	}

	public void removeHandler(GameCallback callback) {
		this.mHandlers.remove(callback);
	}
}
