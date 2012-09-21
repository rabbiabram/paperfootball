
package rnr.paperfootball;

import java.util.Vector;

/**
 *
 * @author rodnover
 * Управляет очередностью хода игроков и состоянием карты
 */
public class Game extends Thread {
	public static final int MIN_PLAYERS_COUNT = 2;
	private BaseGameMap mGameMap;
	private Vector<BasePlayer> mPlayers;
	private BasePlayer mCurrentPlayer;
	private int mTurnCount;

	public int getTurnCount() {
		return this.mTurnCount;
	}

	// Показывает, в процессе ли игра.
	private boolean isStart;

	public BaseGameMap getMap() {
		return this.mGameMap;
	};


	public Game(BaseGameMap gameMap) {
		this.mGameMap = gameMap;
		this.mPlayers = new Vector<BasePlayer>();
		this.isStart = false;
	}

	public Vector<BasePlayer> getPlayers() {
		return this.mPlayers;
	}


	public void addPlayer(BasePlayer player) {
		if (!this.isStart) {
			this.mPlayers.add(player);
		}
	}

	public void removePlayer(BasePlayer player) {
		if (!this.isStart) {
			this.mPlayers.remove(player);
		}
	}

	public void startGame() throws InsufficientPlayersException {
		this.isStart = true;
		this.mTurnCount = 0;

		if (this.mPlayers.size() < Game.MIN_PLAYERS_COUNT) {
			throw new InsufficientPlayersException("Недостаточное количестов игроков");
		}

		this.mCurrentPlayer = this.mPlayers.firstElement();
		this.start();
	}
	public void run() {
		while (true) {
			Vector<Cell> path;

			do {
				 path = this.mCurrentPlayer.Turn(this.mGameMap);
			}
			while (!this.mGameMap.validate(path));

			this.mGameMap.pavePath(path);

			int nextPlayerIndex = (this.mPlayers.indexOf(this.mCurrentPlayer) + 1) % this.mPlayers.size();
			this.mCurrentPlayer = this.mPlayers.get(nextPlayerIndex);

			++this.mTurnCount;
		}
	}
}
