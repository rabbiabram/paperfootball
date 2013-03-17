package com.rnr.paperfootball.asketmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.Game;
import com.rnr.paperfootball.map.MapController;

public class AsketMapController extends MapController {

	public AsketMapController(Game game) {
		super(game);
	}

	@Override
	public void draw(Canvas canvas) {
		this.mWidthCell = this.calculateWidthCell(canvas.getWidth(), canvas.getHeight());

		Paint fill = new Paint();
		fill.setStyle(Paint.Style.FILL);

		// закрашиваем холст черным цветом
		fill.setColor(Color.BLACK);
		canvas.drawPaint(fill);

		Paint pEmptyLine = new Paint();
		Paint pLine = new Paint();

		pEmptyLine.setColor(Color.GRAY);
		pEmptyLine.setStrokeWidth(1);

		pLine.setColor(Color.WHITE);
		pLine.setStrokeWidth(3);

		// Рисуем сетку
		// Рисуем от каждой точки справа и снизу
		for (int y = AsketMap.INDEX_HEIGHT_MIN; y < AsketMap.INDEX_HEIGHT_MAX; y++) {
			for (int x = AsketMap.INDEX_WIDTH_MIN; x < AsketMap.INDEX_WIDTH_MAX; x++) {
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
		for (int y = AsketMap.INDEX_HEIGHT_MIN; y < AsketMap.INDEX_HEIGHT_MAX; y++) {
			int x = AsketMap.INDEX_WIDTH_MAX;
			Cell cellA = this.mGameMap.getCell(new Cell(x, y));
			Cell cellB = this.mGameMap.getCell(new Cell(x, y + 1));

			this.paintLine(canvas, cellA, cellB, pLine, pEmptyLine);
		}
		// Крайние линии снизу
		for (int x = AsketMap.INDEX_WIDTH_MIN; x < AsketMap.INDEX_WIDTH_MAX; x++) {
			int y = AsketMap.INDEX_HEIGHT_MAX;
			Cell cellA = this.mGameMap.getCell(new Cell(x, y));
			Cell cellB = this.mGameMap.getCell(new Cell(x + 1, y));

			Cell cellXY = this.mGameMap.getCell(new Cell(x + 1, y - 1));

			this.paintLine(canvas, cellA, cellB, pLine, pEmptyLine);
			this.paintLine(canvas, cellA, cellXY, pLine);
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
		pBall.setColor(this.mGame.getCurrentPlayer().getColor());

		canvas.drawCircle(currentCell.getX() * this.mWidthCell, currentCell.getY() * this.mWidthCell, 3, pBall);
	}
	protected float calculateWidthCell(int width, int height) {
		return Math.min(height / (AsketMap.CELLS_ROW_COUNT - 1), width / (AsketMap.CELLS_COL_COUNT - 1));
	}
	@Override
	public Rect getRect(int width, int height) {
		float sizeCell = this.calculateWidthCell(width, height);
		return new Rect(0, 0, Math.round(sizeCell * (AsketMap.CELLS_COL_COUNT - 1)), 
				Math.round(sizeCell * (AsketMap.CELLS_ROW_COUNT - 1)));
	}
}
