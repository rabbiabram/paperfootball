/**
 *
 */
package com.rnr.paperfootball.base;

import java.util.Vector;

import com.rnr.paperfootball.core.Cell;

/**
 * @author rodnover
 *
 */
public abstract class BasePlayer {
	private String mName;

	public BasePlayer(String name) {
		this.setName(name);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return this.mName;
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
	public abstract Vector<Cell> Turn(BaseMap gameMap) throws InterruptedException;

}
