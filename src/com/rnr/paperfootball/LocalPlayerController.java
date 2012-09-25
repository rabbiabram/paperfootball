package com.rnr.paperfootball;

import java.util.Vector;

import android.util.Log;

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
				Log.d("paperfootbal.path", "wrong path");
				throw new WrongPathException("Оканчивать ход можно только на пустой точке.");
			}

			Log.d("paperfootbal.path", "end path");
			mapController.setCurrentPath(null);
			synchronized (this.mPath) {
				this.mIsStart = false;
				this.mPath.notifyAll();
			}
			return false;
		} else {
			this.mPath.add(cell);

			if (mMap.validate(this.mPath, true)) {
				Log.d("paperfootbal.path", String.format("add cell: %d, %d", cell.getX(), cell.getY()));
				mapController.setCurrentPath(this.mPath);
				this.mOldCell = cell;
				return true;
			} else {
				Log.d("paperfootbal.path", String.format("ignore cell: %d, %d", cell.getX(), cell.getY()));
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

			do {
				this.mPath.wait();
			} while (this.mIsStart);
		}

		return this.mPath;
	}

}
