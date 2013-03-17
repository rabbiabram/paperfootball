/**
 *
 */
package com.rnr.paperfootball.base;

import java.util.Vector;

import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.PathGetter;

/**
 * @author rodnover
 *
 */
public abstract class BasePlayer {
	private String mName;
	private int mWins;
	private int mColor;
	protected PathGetter mPathGetter;

	public BasePlayer(String name, int color, PathGetter pathGetter) {
		this.setName(name);
		this.mWins = 0;
		this.mColor = color;
		this.mPathGetter = pathGetter;
	}
	
	public int getColor() {
		return this.mColor;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return this.mName;
	}

	public int getWins() {
		return this.mWins;
	}
	
	public void incWins() {
		++this.mWins;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.mName = name;
	}

	/**
	 * Возвращает путь игрока. Если путь пустой, значит, игрок проиграл.
	 * @param gameMap
	 * @return Путь. Если возвратит null, значит, путь не найден
	 * @throws InterruptedException
	 */
	public Vector<Cell> Turn(BaseMap gameMap) throws InterruptedException {
		if (this.mPathGetter == null) {
			return null;
		}

		return this.mPathGetter.getPath(gameMap);
	}

	public void stopTurn(BaseMap gameMap) {
		if (this.mPathGetter != null) {
			this.mPathGetter.stop(gameMap);
		}
	}

	public PathGetter getPathGetter() {
		return this.mPathGetter;
	}
}
