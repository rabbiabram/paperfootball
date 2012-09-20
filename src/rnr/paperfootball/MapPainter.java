/**
 *
 */
package rnr.paperfootball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author rodnover
 *
 */
public class MapPainter extends BaseMapPainter {

	private int mWidthCell;
	private GameMap mGameMap;

	public MapPainter(GameMap gameMap) {
		this.mWidthCell = 30;
		this.mGameMap = gameMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see rnr.paperfootball.BaseMapPainter#draw(android.graphics.Canvas)
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
		for (int y = GameMap.INDEX_HEIGHT_MIN; y < GameMap.INDEX_HEIGHT_MAX; y++) {
			for (int x = GameMap.INDEX_WIDTH_MIN; x < GameMap.INDEX_WIDTH_MAX; x++) {
				Cell cellA = this.mGameMap.getCell(x, y);
				Cell cellAX = this.mGameMap.getCell(x + 1, y);
				Cell cellAY = this.mGameMap.getCell(x, y + 1);

				Paint pX = (this.mGameMap.isLinked(cellA, cellAX))?(pLine):(pEmptyLine);
				Paint pY = (this.mGameMap.isLinked(cellA, cellAY))?(pLine):(pEmptyLine);

				canvas.drawLine(x * this.mWidthCell, y * this.mWidthCell,
						(x + 1) * this.mWidthCell, y * this.mWidthCell, pX);
				canvas.drawLine(x * this.mWidthCell, y * this.mWidthCell,
						x * this.mWidthCell, (y + 1) * this.mWidthCell, pY);
			}
		}
		// Крайние линии снизу и сверху
		for (int y = GameMap.INDEX_HEIGHT_MIN; y < GameMap.INDEX_HEIGHT_MAX; y++) {
			int x = GameMap.CELLS_COL_COUNT - 1;
			Cell cellA = this.mGameMap.getCell(x, y);
			Cell cellB = this.mGameMap.getCell(x, y + 1);

			Paint pY = (this.mGameMap.isLinked(cellA, cellB))?(pLine):(pEmptyLine);

			canvas.drawLine(x * this.mWidthCell, y * this.mWidthCell,
					x * this.mWidthCell, (y + 1) * this.mWidthCell, pY);
		}

		for (int x = GameMap.INDEX_WIDTH_MIN; x < GameMap.INDEX_WIDTH_MAX; x++) {
			int y = GameMap.CELLS_ROW_COUNT - 1;
			Cell cellA = this.mGameMap.getCell(x, y);
			Cell cellB = this.mGameMap.getCell(x + 1, y);

			Paint pX = (this.mGameMap.isLinked(cellA, cellB))?(pLine):(pEmptyLine);

			canvas.drawLine((x + 1) * this.mWidthCell, y * this.mWidthCell,
					x * this.mWidthCell, y * this.mWidthCell, pX);
		}
	}

}
