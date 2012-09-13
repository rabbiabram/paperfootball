/**
 *
 */
package rnr.paperfootball;

/**
 * @author rodnover
 *
 */
public abstract class BaseCellValidator {
	public abstract boolean isPathPossible(int lhs, int rhs);
	public abstract boolean isPathPossible(Cell lhs, Cell rhs);
	public abstract boolean isPathExist(int lhs, int rhs);
	public abstract boolean isPathExist(Cell lhs, Cell rhs);

}
