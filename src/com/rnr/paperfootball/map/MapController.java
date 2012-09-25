/**
 *
 */
package com.rnr.paperfootball.map;

import java.util.Vector;

import com.rnr.paperfootball.base.BaseMapController;
import com.rnr.paperfootball.core.Cell;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author rodnover
 *
 */
public class MapController extends BaseMapController {

	private float mWidthCell;
	private Map mGameMap;
	Vector<Cell> mCurrentPath;

	@Override
	public void setCurrentPath(Vector<Cell> path) {
		this.mCurrentPath = path;
	}

	public MapController(Map gameMap) {
		this.mWidthCell = 30;
		this.mGameMap = gameMap;
	}

	private void paintLine(Canvas canvas, Cell a, Cell b, Paint pLine, Paint pEmptyLine) {
		canvas.drawLine(a.getX() * this.mWidthCell, a.getY() * this.mWidthCell,
				b.getX() * this.mWidthCell, b.getY() * this.mWidthCell,
				(this.mGameMap.hasLink(a, b))?(pLine):(pEmptyLine));

	}

	private void paintLine(Canvas canvas, Cell a, Cell b, Paint pLine) {
		if (this.mGameMap.hasLink(a, b)) {
			canvas.drawLine(a.getX() * this.mWidthCell, a.getY() * this.mWidthCell,
					b.getX() * this.mWidthCell, b.getY() * this.mWidthCell, pLine);
		}
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see com.rnr.paperfootball.BaseMapPainter#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {

		Paint pEmptyLine = new Paint();
		Paint pLine = new Paint();

		pEmptyLine.setColor(Color.GRAY);
		pEmptyLine.setStrokeWidth(1);

		pLine.setColor(Color.WHITE);
		pLine.setStrokeWidth(3);

		// Рисуем сетку
		// Рисуем от каждой точки справа и снизу
		for (int y = Map.INDEX_HEIGHT_MIN; y < Map.INDEX_HEIGHT_MAX; y++) {
			for (int x = Map.INDEX_WIDTH_MIN; x < Map.INDEX_WIDTH_MAX; x++) {
				Cell cellA = this.mGameMap.getCell(new Cell(x, y));
				Cell cellAX = this.mGameMap.getCell(new Cell(x + 1, y));
				Cell cellAY = this.mGameMap.getCell(new Cell(x, y + 1));
				Cell cellAXY = this.mGameMap.getCell(new Cell(x + 1, y + 1));

				if (y > 0) {
					Cell cellC = this.mGameMap.getCell(new Cell(x + 1, y - 1));
					this.paintLine(canvas, cellA, cellC, pLine);
				}

				this.paintLine(canvas, cellA, cellAX, pLine, pEmptyLine);
				this.paintLine(canvas, cellA, cellAY, pLine, pEmptyLine);
				this.paintLine(canvas, cellA, cellAXY, pLine);
			}
		}
		// Крайние линии справа
		for (int y = Map.INDEX_HEIGHT_MIN; y < Map.INDEX_HEIGHT_MAX; y++) {
			int x = Map.CELLS_COL_COUNT - 1;
			Cell cellA = this.mGameMap.getCell(new Cell(x, y));
			Cell cellB = this.mGameMap.getCell(new Cell(x, y + 1));

			this.paintLine(canvas, cellA, cellB, pLine, pEmptyLine);
		}
		// Крайние линии снизу
		for (int x = Map.INDEX_WIDTH_MIN; x < Map.INDEX_WIDTH_MAX; x++) {
			int y = Map.CELLS_ROW_COUNT - 1;
			Cell cellA = this.mGameMap.getCell(new Cell(x, y));
			Cell cellB = this.mGameMap.getCell(new Cell(x + 1, y));

			Cell cell = this.mGameMap.getCell(new Cell(x, Map.INDEX_HEIGHT_MIN + 1));
			Cell cellXY = this.mGameMap.getCell(new Cell(x, Map.INDEX_HEIGHT_MIN));

			this.paintLine(canvas, cellA, cellB, pLine, pEmptyLine);
			this.paintLine(canvas, cell, cellXY, pLine);
		}

		Cell currentCell = this.mGameMap.getCurrent();
		// Рисуем путь
		if (this.mCurrentPath != null) {
			Paint pPathLine = new Paint();

			pPathLine.setColor(Color.WHITE);
			pPathLine.setStrokeWidth(1);

			Cell a = currentCell;
			Cell b = this.mCurrentPath.firstElement();
			canvas.drawLine(a.getX() * this.mWidthCell, a.getY() * this.mWidthCell,
					b.getX() * this.mWidthCell, b.getY() * this.mWidthCell, pPathLine);

			for (int i = 1; i < this.mCurrentPath.size(); i++) {
				a = this.mCurrentPath.get(i - 1);
				b = this.mCurrentPath.get(i);
				canvas.drawLine(a.getX() * this.mWidthCell, a.getY() * this.mWidthCell,
						b.getX() * this.mWidthCell, b.getY() * this.mWidthCell, pPathLine);

			}
			currentCell = this.mCurrentPath.lastElement();
		}
		// Рисуем мячик
		Paint pBall = new Paint();
		pBall.setColor(Color.RED);

		canvas.drawCircle(currentCell.getX() * this.mWidthCell, currentCell.getY() * this.mWidthCell, 3, pBall);
	}

	@Override
	public Cell getCell(float x, float y) {
		int cellX = Math.round(x / this.mWidthCell);
		int cellY = Math.round(y / this.mWidthCell);

		Cell cell = null;

		if ((Math.abs(cellX * this.mWidthCell - x) < 0.5 * this.mWidthCell) &&
				(Math.abs(cellY * this.mWidthCell - y) < 0.5 * this.mWidthCell)) {
			cell = this.mGameMap.getCell(new Cell(cellX, cellY));
		};

		return cell;
	}
}
