/**
 *
 */
package rnr.paperfootball;

import java.util.Vector;

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
	private CellValidator mCellValidator;

	public MapPainter(GameMap gameMap, CellValidator cv) {
		this.mWidthCell = 30;
		this.mGameMap = gameMap;
		this.mCellValidator = cv;
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
		for (int i = 0; i < GameMap.CELLS_ROW_COUNT; i++) {
			canvas.drawLine(i * this.mWidthCell, 0, i * this.mWidthCell,
					GameMap.INDEX_HEIGHT_MAX * this.mWidthCell, pEmptyLine);
		}
		for (int i = 0; i < GameMap.CELLS_COL_COUNT; i++) {
			canvas.drawLine(0, i * this.mWidthCell, GameMap.INDEX_WIDTH_MAX
					* this.mWidthCell, i * this.mWidthCell, pEmptyLine);
		}
		// Рисуем линии
		Vector<Cell> cells = this.mGameMap.getCells();
		for (int i = 0; i < cells.size() - 1; i++) {
			for (int ii = i + 1; ii < cells.size(); ii++) {
				if (this.mCellValidator.isPathExist(i, ii)) {
					int sx = (i % GameMap.CELLS_ROW_COUNT) * this.mWidthCell;
					int ex = (ii % GameMap.CELLS_ROW_COUNT) * this.mWidthCell;
					int sy = (i / GameMap.CELLS_ROW_COUNT) * this.mWidthCell;
					int ey = (ii / GameMap.CELLS_ROW_COUNT) * this.mWidthCell;

					canvas.drawLine(sx, sy, ex, ey, pLine);
				}
			}

		}
	}

}
