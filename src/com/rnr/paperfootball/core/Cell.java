/**
 *
 */
package com.rnr.paperfootball.core;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mX;
		result = prime * result + mY;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Cell)) {
			return false;
		}

		Cell other = (Cell) obj;

		if (this.mX != other.mX) {
			return false;
		}
		if (this.mY != other.mY) {
			return false;
		}
		return true;
	}

	public boolean isLocate(int x, int y) {
		return (this.mX == x) && (this.mY == y);
	}
}
