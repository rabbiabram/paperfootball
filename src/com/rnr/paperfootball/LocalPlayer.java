/**
 *
 */
package com.rnr.paperfootball;

import java.util.Vector;

import com.rnr.paperfootball.base.BaseMap;
import com.rnr.paperfootball.base.BasePlayer;
import com.rnr.paperfootball.core.Cell;
import com.rnr.paperfootball.core.PathGetter;

/**
 * @author rodnover
 *
 */
public class LocalPlayer extends BasePlayer {
	PathGetter mPathGetter;

	public LocalPlayer(PathGetter pathGetter) {
		this.mPathGetter = pathGetter;
	}

	public PathGetter getPathGetter() {
		return this.mPathGetter;
	}

	public void setPathGetter(PathGetter pathGetter) {
		this.mPathGetter = pathGetter;
	}

	@Override
	public Vector<Cell> Turn(BaseMap gameMap) throws InterruptedException {
		if (this.mPathGetter == null) {
			return null;
		}

		return this.mPathGetter.getPath(gameMap);
	}

}
