/**
 *
 */
package com.rnr.paperfootball.map;

import java.util.Vector;

import com.rnr.paperfootball.base.BaseMapController;
import com.rnr.paperfootball.base.BasePlayer;
import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author rodnover
 *
 */
public class MapController extends BaseMapController {

	protected float mWidthCell;
	protected Map mGameMap;
	protected Game mGame;
	protected Vector<Cell> mCurrentPath;

	@Override
	public void setCurrentPath(Vector<Cell> path) {
		this.mCurrentPath = path;
	}

	public MapController(Game game) {
		this.mWidthCell = 30;
		this.mGame = game;
		this.mGameMap = (Map)game.getMap();
	}

	protected void paintLine(Canvas canvas, Cell a, Cell b, Paint pLine, Paint pEmptyLine) {
		canvas.drawLine(a.getX() * this.mWidthCell, a.getY() * this.mWidthCell,
				b.getX() * this.mWidthCell, b.getY() * this.mWidthCell,
				(this.mGameMap.hasLink(a, b))?(pLine):(pEmptyLine));

	}

	protected void paintLine(Canvas canvas, Cell a, Cell b, Paint pLine) {
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

		BasePlayer winner = this.mGame.getWinner();
		
		if (winner == null) {
			pLine.setColor(Color.WHITE);
		} else {
			pLine.setColor(winner.getColor());
		}
		pLine.setStrokeWidth(3);
		
		// Рисуем сетку
		// Рисуем от каждой точки справа и снизу
		for (int y = this.mGameMap.INDEX_HEIGHT_MIN(); y < this.mGameMap.INDEX_HEIGHT_MAX(); y++) {
			for (int x = this.mGameMap.INDEX_WIDTH_MIN(); x < this.mGameMap.INDEX_WIDTH_MAX(); x++) {
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
		for (int y = this.mGameMap.INDEX_HEIGHT_MIN(); y < this.mGameMap.INDEX_HEIGHT_MAX(); y++) {
			int x = this.mGameMap.INDEX_WIDTH_MAX();
			Cell cellA = this.mGameMap.getCell(new Cell(x, y));
			Cell cellB = this.mGameMap.getCell(new Cell(x, y + 1));

			this.paintLine(canvas, cellA, cellB, pLine, pEmptyLine);
		}
		// Крайние линии снизу
		for (int x = this.mGameMap.INDEX_WIDTH_MIN(); x < this.mGameMap.INDEX_WIDTH_MAX(); x++) {
			int y = this.mGameMap.INDEX_HEIGHT_MAX();
			Cell cellA = this.mGameMap.getCell(new Cell(x, y));
			Cell cellB = this.mGameMap.getCell(new Cell(x + 1, y));

			Cell cellXY = this.mGameMap.getCell(new Cell(x + 1, y - 1));

			this.paintLine(canvas, cellA, cellB, pLine, pEmptyLine);
			this.paintLine(canvas, cellA, cellXY, pLine);
		}

		Cell currentCell = this.mGameMap.getCurrent();
		// Рисуем путь
		if ((this.mCurrentPath != null) && (this.mCurrentPath.size() > 0)) {
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
		// Рисуем ворота
		Paint pGoal = new Paint();
		pGoal.setColor(Color.WHITE);
		pGoal.setStrokeWidth(3);
		Cell a = this.mGameMap.getCell(new Cell(this.mGameMap.INDEX_WIDTH_MIN(), this.mGameMap.INDEX_HEIGHT_CENTER() - this.mGameMap.GOAL_LINE_OFFSET));
		Cell b = this.mGameMap.getCell(new Cell(this.mGameMap.INDEX_WIDTH_MAX(), this.mGameMap.INDEX_HEIGHT_CENTER() - this.mGameMap.GOAL_LINE_OFFSET));
		Cell c = this.mGameMap.getCell(new Cell(this.mGameMap.INDEX_WIDTH_MIN(), this.mGameMap.INDEX_HEIGHT_CENTER() + this.mGameMap.GOAL_LINE_OFFSET));
		Cell d = this.mGameMap.getCell(new Cell(this.mGameMap.INDEX_WIDTH_MAX(), this.mGameMap.INDEX_HEIGHT_CENTER() + this.mGameMap.GOAL_LINE_OFFSET));

		float fieldGoal = 4;
		canvas.drawLine(a.getX() * this.mWidthCell, a.getY() * this.mWidthCell, 
				a.getX() * this.mWidthCell + fieldGoal, a.getY() * this.mWidthCell, pGoal);
		canvas.drawLine(b.getX() * this.mWidthCell, b.getY() * this.mWidthCell, 
				b.getX() * this.mWidthCell - fieldGoal, b.getY() * this.mWidthCell, pGoal);
		canvas.drawLine(c.getX() * this.mWidthCell, c.getY() * this.mWidthCell, 
				c.getX() * this.mWidthCell + fieldGoal, c.getY() * this.mWidthCell, pGoal);
		canvas.drawLine(d.getX() * this.mWidthCell, d.getY() * this.mWidthCell, 
				d.getX() * this.mWidthCell - fieldGoal, d.getY() * this.mWidthCell, pGoal);
	}	

	protected float calculateWidthCell(int width, int height) {
		return Math.min(height / (this.mGameMap.CELLS_ROW_COUNT() - 1), width / (this.mGameMap.CELLS_COL_COUNT() - 1));
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

	@Override
	public Rect getRect(int width, int height) {
		float sizeCell = this.calculateWidthCell(width, height);
		return new Rect(0, 0, Math.round(sizeCell * (this.mGameMap.CELLS_COL_COUNT() - 1)), 
				Math.round(sizeCell * (this.mGameMap.CELLS_ROW_COUNT() - 1)));
	}
}
