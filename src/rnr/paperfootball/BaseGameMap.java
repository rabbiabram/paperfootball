package rnr.paperfootball;

import java.util.Vector;

// TODO: Сделать дженериком,в качестве параметра Cell
public abstract class BaseGameMap {
	public abstract boolean isLinked(Cell a, Cell b);
	public abstract boolean validate(Vector<Cell> path);
	public abstract void pavePath(Vector<Cell> path);
	public abstract Cell getCurrent();
	public abstract Cell getCell(Cell cell);

	Vector<GameCallback> mHandlers;

	public void addHandler(GameCallback callback) {
		this.mHandlers.add(callback);
	}

	public void removeHandler(GameCallback callback) {
		this.mHandlers.remove(callback);
	}
}
