
package rnr.paperfootball;

/**
 *
 * @author rodnover
 * Управляет очередностью хода игроков и состоянием карты
 */
public class Game {
	private BaseGameMap mGameMap;
	public BaseGameMap getMap() {
		return this.mGameMap;
	};


	public Game(BaseGameMap gameMap) {
		this.mGameMap = gameMap;
	}
}
