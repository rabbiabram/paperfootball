/**
 *
 */
package rnr.paperfootball;

import java.util.Vector;

/**
 * @author rodnover
 *
 */
public class CellValidator extends BaseCellValidator {
	private GameMap mGameMap;

	public CellValidator(GameMap gameMap) {
		this.mGameMap = gameMap;
	}

	@Override
	public boolean isPathPossible(Cell lhs, Cell rhs) {
		return this.isPathPossible(this.mGameMap.getCells().indexOf(lhs), this.mGameMap.getCells().indexOf(rhs));
	}

	@Override
	public boolean isPathExist(Cell lhs, Cell rhs) {
		return this.isPathExist(this.mGameMap.getCells().indexOf(lhs), this.mGameMap.getCells().indexOf(rhs));
	}

	@Override
	public boolean isPathPossible(int lhs, int rhs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPathExist(int lhs, int rhs) {
		if ((lhs == -1) || (rhs == -1)) {
			return false;
		}

		Vector<Cell> cells = this.mGameMap.getCells();
		Cell cellLhs = cells.get(lhs);
		Cell cellRhs = cells.get(rhs);

		int deltaX = (rhs % GameMap.CELLS_ROW_COUNT) - (lhs % GameMap.CELLS_ROW_COUNT);
		int deltaY = (rhs / GameMap.CELLS_ROW_COUNT) - (lhs / GameMap.CELLS_ROW_COUNT);

		return ((deltaX == 1) && (deltaY == 0) && cellLhs.isRight() && cellRhs.isLeft()) ||
				((deltaX == -1) && (deltaY == 0) && cellLhs.isLeft() && cellRhs.isRight()) ||
				((deltaX == 1) && (deltaY == 1) && cellLhs.isDownRight() && cellRhs.isUpLeft()) ||
				((deltaX == 0) && (deltaY == 1) && cellLhs.isDown() && cellRhs.isUp()) ||
				((deltaX == -1) && (deltaY == 1) && cellLhs.isDownLeft() && cellRhs.isUpRight()) ||
				((deltaX == 1) && (deltaY == -1) && cellLhs.isUpRight() && cellRhs.isDownLeft()) ||
				((deltaX == 0) && (deltaY == -1) && cellLhs.isUp() && cellRhs.isDown()) ||
				((deltaX == -1) && (deltaY == -1) && cellLhs.isUpLeft() && cellRhs.isDownRight());
	}

}
