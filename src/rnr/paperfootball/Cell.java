/**
 *
 */
package rnr.paperfootball;

/**
 * @author rodnover
 * Класс ячейки. Хранит в себе координаты размещения
 */
public class Cell {
	int mX;
	int mY;

	public int getX() {
		return this.mX;
	}

	public int getY() {
		return this.mY;
	}

	public Cell(int x, int y) {
		this.mX = x;
		this.mY = y;
	}

	public boolean isLocate(int x, int y) {
		return (this.mX == x) && (this.mY == y);
	}
}
