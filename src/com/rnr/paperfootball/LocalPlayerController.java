package com.rnr.paperfootball;

import java.util.Vector;

import com.rnr.paperfootball.base.BaseMap;
import com.rnr.paperfootball.base.BaseMapController;
import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.PathGetter;
import com.rnr.paperfootball.core.TouchHandler;
import com.rnr.paperfootball.core.WrongPathException;

public class LocalPlayerController implements TouchHandler, PathGetter {
	volatile boolean mIsStart;
	Vector<Cell> mPath;

	Cell mOldCell;
	private BaseMap mMap;
	private boolean mIsStopped;
	public LocalPlayerController() {
		this.mIsStart = false;
	}
	@Override
	public boolean setPoint(BaseMapController mapController, float x, float y) throws WrongPathException {
		if (!this.mIsStart || (mapController == null)) {
			return false;
		}

		Cell cell = mapController.getCell(x, y);

		if (cell == null) {
			throw new WrongPathException("Не выбрана точка.");
		}

		if (cell.equals(this.mOldCell)) {
			if (!mMap.validate(this.mPath)) {
				throw new WrongPathException("Оканчивать ход можно только на пустой точке.");
			}

			mapController.setCurrentPath(null);
			synchronized (this.mPath) {
				this.mIsStart = false;
				this.mPath.notifyAll();
			}
			return false;
		} else {
			this.mPath.add(cell);

			if (mMap.validate(this.mPath, true)) {
				mapController.setCurrentPath(this.mPath);
				this.mOldCell = cell;
				return true;
			} else {
				this.mPath.remove(this.mPath.size() - 1);
				throw new WrongPathException("Недопустимая точка для хода.");
			}
		}
	}

	@Override
	public Vector<Cell> getPath(BaseMap gameMap) throws InterruptedException {
		this.mPath = new Vector<Cell>();
		this.mMap = gameMap;
		this.mOldCell = null;

		synchronized (this.mPath) {
			this.mIsStart = true;
			this.mIsStopped = false;

			do {
				this.mPath.wait();
			} while (this.mIsStart);
		}

		if (this.mIsStopped) {
			return null;
		} else {
			return this.mPath;
		}
	}
	@Override
	public void stop(BaseMap gameMap) {
		synchronized (this.mPath) {
			this.mIsStart = false;
			this.mIsStopped = true;
			this.mPath.notifyAll();
		}
	}

}
