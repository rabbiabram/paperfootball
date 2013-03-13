/**
 *
 */
package com.rnr.paperfootball.core;

import java.util.Vector;

import com.rnr.paperfootball.base.BaseMap;

/**
 * @author rodnover
 *
 */

public interface PathGetter {
	Vector<Cell> getPath(BaseMap gameMap) throws InterruptedException;
	void stop(BaseMap gameMap);
}
