/**
 *
 */
package rnr.paperfootball;

/**
 * @author rodnover
 *
 */
public class Cell {
	public static final int EMPTY = 0;
	public static final int UP = 0x1;
	public static final int UP_RIGHT = 0x2;
	public static final int RIGHT = 0x4;
	public static final int DOWN_RIGHT = 0x8;
	public static final int DOWN = 0x10;
	public static final int DOWN_LEFT = 0x20;
	public static final int LEFT = 0x40;
	public static final int UP_LEFT = 0x80;

	private int mRelation;

	public Cell() {
		this.mRelation = Cell.EMPTY;
	}

	public Cell(int relation) {
		this.mRelation = relation;
	}

	public int getRelation(){
		return this.mRelation;
	}

	public void setRelation(int relation) {
		this.mRelation = relation;
	}

	public boolean isUp() {
		return (this.mRelation & Cell.UP) != 0;
	}

	public boolean isUpRight() {
		return (this.mRelation & Cell.UP_RIGHT) != 0;
	}

	public boolean isRight() {
		return (this.mRelation & Cell.RIGHT) != 0;
	}

	public boolean isDownRight() {
		return (this.mRelation & Cell.DOWN_RIGHT) != 0;
	}

	public boolean isDown() {
		return (this.mRelation & Cell.DOWN) != 0;
	}

	public boolean isDownLeft() {
		return (this.mRelation & Cell.DOWN_LEFT) != 0;
	}

	public boolean isLeft() {
		return (this.mRelation & Cell.LEFT) != 0;
	}

	public boolean isUpLeft() {
		return (this.mRelation & Cell.UP_LEFT) != 0;
	}
}
