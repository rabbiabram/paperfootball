/**
 *
 */
package com.rnr.paperfootball;

import com.rnr.paperfootball.base.BasePlayer;
import com.rnr.paperfootball.core.PathGetter;

/**
 * @author rodnover
 *
 */
public class LocalPlayer extends BasePlayer {

	public LocalPlayer(String name, int color, PathGetter pathGetter) {
		super(name, color, pathGetter);
	}
}
