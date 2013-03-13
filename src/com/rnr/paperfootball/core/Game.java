package com.rnr.paperfootball.core;

import java.util.Vector;

import android.util.Log;

import com.rnr.paperfootball.base.BaseMap;
import com.rnr.paperfootball.base.BasePlayer;

/**
 *
 * @author rodnover
 * Управляет очередностью хода игроков и состоянием карты
 */
public class Game extends Thread {
	public static final int MIN_PLAYERS_COUNT = 2;
	private BaseMap mGameMap;
	private Vector<BasePlayer> mPlayers;
	private BasePlayer mCurrentPlayer;
	private int mTurnCount;

	protected Vector<GameCallback> mHandlers;

	public BasePlayer getCurrentPlayer() {
		return this.mCurrentPlayer;
	}
	public void addHandler(GameCallback callback) {
		this.mHandlers.add(callback);
	}

	public void removeHandler(GameCallback callback) {
		this.mHandlers.remove(callback);
	}

	public void sendRepaint() {
		for (GameCallback callback : this.mHandlers) {
			callback.repaint(this.mGameMap);
		}
	}

	public void sendEndOfGame(int indexPlayer) {
		for (GameCallback callback : this.mHandlers) {
			callback.endOfGame(indexPlayer);
		}
	}

	public int getTurnCount() {
		return this.mTurnCount;
	}

	// Показывает, в процессе ли игра.
	private boolean isStart;

	public BaseMap getMap() {
		return this.mGameMap;
	};


	public Game(BaseMap gameMap) {
		this.mGameMap = gameMap;
		this.mPlayers = new Vector<BasePlayer>();
		this.isStart = false;
		this.mHandlers = new Vector<GameCallback>();
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

		if (this.mPlayers.size() < this.mGameMap.getMinPlayersCount()) {
			throw new InsufficientPlayersException("Недостаточное количестов игроков");
		}

		this.mCurrentPlayer = this.mPlayers.firstElement();

		this.start();
	}
	public void run() {
		 try {
			while (true) {
				Vector<Cell> path;

				this.sendRepaint();
				do {
					path = this.mCurrentPlayer.Turn(this.mGameMap);

					if (path == null) {

					}
				}
				while (!this.mGameMap.pavePath(path));

				int indexWinner = this.mGameMap.getIndexWinner();
				if (indexWinner != -1) {
					this.sendEndOfGame(indexWinner);
					break;
				}

				int nextPlayerIndex = (this.mPlayers.indexOf(this.mCurrentPlayer) + 1) % this.mPlayers.size();
				this.mCurrentPlayer = this.mPlayers.get(nextPlayerIndex);

				++this.mTurnCount;
			}
		} catch (InterruptedException e) {
			Log.v("paper.thread", "Interrupted thread");
		}
	}
}
