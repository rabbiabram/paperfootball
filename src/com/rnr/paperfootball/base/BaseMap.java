package com.rnr.paperfootball.base;

import java.util.Vector;

import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.GameCallback;

// TODO: Сделать дженериком,в качестве параметра Cell
public abstract class BaseMap {
	public abstract boolean hasLink(Cell a, Cell b);
	public abstract boolean validate(Vector<Cell> path, boolean partial);
	public abstract boolean pavePath(Vector<Cell> path);
	public abstract Cell getCurrent();
	public abstract Cell getCell(Cell cell);
	public abstract int getMinPlayersCount();
	public abstract int getIndexWinner();

	public boolean validate(Vector<Cell> path) {
		return this.validate(path, false);
	}

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

	public void sendRepaint() {
		for (GameCallback callback : this.mHandlers) {
			callback.repaint(this);
		}
	}

	public void sendEndOfGame(int indexPlayer) {
		for (GameCallback callback : this.mHandlers) {
			callback.endOfGame(indexPlayer);
		}
	}
}
