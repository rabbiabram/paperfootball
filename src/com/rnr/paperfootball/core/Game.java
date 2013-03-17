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
	private boolean mIsFinished;

	public BasePlayer getCurrentPlayer() {
		return this.mCurrentPlayer;
	}
	public BasePlayer getNextPlayer() {
		int nextPlayerIndex = (this.mPlayers.indexOf(this.mCurrentPlayer) + 1) % this.mPlayers.size();
		return this.mPlayers.get(nextPlayerIndex);
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
		this.mIsFinished = false;
		this.mHandlers = new Vector<GameCallback>();
	}

	public Vector<BasePlayer> getPlayers() {
		return this.mPlayers;
	}


	public void addPlayer(BasePlayer player) {
		if (!this.isStart && (player != null)) {
			this.mPlayers.add(player);
		}
	}

	public void removePlayer(BasePlayer player) {
		if (!this.isStart) {
			this.mPlayers.remove(player);
		}
	}

	protected void runGame(BasePlayer current) {
		this.isStart = true;
		this.mTurnCount = 0;

		this.mCurrentPlayer = current;

		this.mGameMap.recreate();

		this.start();
		
	}
	public void startGame() throws InsufficientPlayersException {
		if (this.mPlayers.size() < this.mGameMap.getMinPlayersCount()) {
			throw new InsufficientPlayersException("Недостаточное количестов игроков");
		}
		
		this.runGame(this.mPlayers.firstElement());
	}
	
	public void startNew() {
		if (!this.mIsFinished) {
			this.mIsFinished = true;
			this.mCurrentPlayer.stopTurn(this.mGameMap);
			this.mCurrentPlayer = this.getNextPlayer();
			this.sendEndOfGame(this.mPlayers.indexOf(this.mCurrentPlayer));
			this.mCurrentPlayer = this.getNextPlayer();
		}

		this.mGameMap.recreate();
		

		this.mTurnCount = 0;
		this.sendRepaint();
		
		if (this.mIsFinished) {
			synchronized (this) {
				Log.d("paper.thread", "Started");
				this.mIsFinished = false;
				this.notifyAll();
			}
		}

	}
	
	public void run() {
		super.run();
		 try {
			while (this.isStart) {
				Vector<Cell> path;

				this.sendRepaint();
				do {
					path = this.mCurrentPlayer.Turn(this.mGameMap);

					if (path == null) {
						synchronized (this) {
							while (this.mIsFinished) {
								this.wait();
							}
						}
					}
				}
				while ((path != null) && !this.mGameMap.pavePath(path));

				if (path == null) {
					Log.d("paper.thread", "Next party");
					continue;
				}
				int indexWinner = this.mGameMap.getIndexWinner();
				if (indexWinner != -1) {
					this.sendEndOfGame(indexWinner);
					this.mIsFinished = true;
					this.mCurrentPlayer = this.getPlayers().get(indexWinner);
					this.mCurrentPlayer = this.getNextPlayer();
					synchronized (this) {
						while (this.mIsFinished) {
							Log.d("paper.thread", "Stopped");
							this.wait();
						}
					}
					continue;
				}

				this.mCurrentPlayer = this.getNextPlayer();

				++this.mTurnCount;
			}
			
		} catch (InterruptedException e) {
			Log.v("paper.thread", "Interrupted thread");
		}
	}
}
