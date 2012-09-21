/**
 *
 */
package com.rnr.paperfootball.map;

import com.rnr.paperfootball.base.BaseMapPainter;
import com.rnr.paperfootball.core.Cell;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author rodnover
 *
 */
public class MapPainter extends BaseMapPainter {

	private int mWidthCell;
	private Map mGameMap;

	public MapPainter(Map gameMap) {
		this.mWidthCell = 30;
		this.mGameMap = gameMap;
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

				Paint pX = (this.mGameMap.isLinked(cellA, cellAX))?(pLine):(pEmptyLine);
				Paint pY = (this.mGameMap.isLinked(cellA, cellAY))?(pLine):(pEmptyLine);

				canvas.drawLine(x * this.mWidthCell, y * this.mWidthCell,
						(x + 1) * this.mWidthCell, y * this.mWidthCell, pX);
				canvas.drawLine(x * this.mWidthCell, y * this.mWidthCell,
						x * this.mWidthCell, (y + 1) * this.mWidthCell, pY);
			}
		}
		// Крайние линии снизу
		for (int y = Map.INDEX_HEIGHT_MIN; y < Map.INDEX_HEIGHT_MAX; y++) {
			int x = Map.CELLS_COL_COUNT - 1;
			Cell cellA = this.mGameMap.getCell(new Cell(x, y));
			Cell cellB = this.mGameMap.getCell(new Cell(x, y + 1));

			Paint pY = (this.mGameMap.isLinked(cellA, cellB))?(pLine):(pEmptyLine);

			canvas.drawLine(x * this.mWidthCell, y * this.mWidthCell,
					x * this.mWidthCell, (y + 1) * this.mWidthCell, pY);
		}
		// Крайние линии справа
		for (int x = Map.INDEX_WIDTH_MIN; x < Map.INDEX_WIDTH_MAX; x++) {
			int y = Map.CELLS_ROW_COUNT - 1;
			Cell cellA = this.mGameMap.getCell(new Cell(x, y));
			Cell cellB = this.mGameMap.getCell(new Cell(x + 1, y));

			Paint pX = (this.mGameMap.isLinked(cellA, cellB))?(pLine):(pEmptyLine);

			canvas.drawLine((x + 1) * this.mWidthCell, y * this.mWidthCell,
					x * this.mWidthCell, y * this.mWidthCell, pX);
		}

		// Рисуем мячик
		Paint pBall = new Paint();
		pBall.setColor(Color.RED);

		Cell currentCell = this.mGameMap.getCurrent();

		canvas.drawCircle(currentCell.getX() * this.mWidthCell, currentCell.getY() * this.mWidthCell, 3, pBall);
	}

}
