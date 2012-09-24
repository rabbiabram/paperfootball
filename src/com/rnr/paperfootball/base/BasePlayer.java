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

	public abstract Vector<Cell> Turn(BaseMap gameMap) throws InterruptedException;

}
