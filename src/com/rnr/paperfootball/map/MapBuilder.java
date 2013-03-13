/**
 *
 */
package com.rnr.paperfootball.map;

import com.rnr.paperfootball.base.BaseMap;
import com.rnr.paperfootball.base.BaseMapBuilder;
import com.rnr.paperfootball.base.BaseMapController;
import com.rnr.paperfootball.core.Game;


/**
 * @author rodnover
 *
 */
public class MapBuilder extends BaseMapBuilder {

	@Override
	public BaseMap createMap() {
		return new Map();
	}

	@Override
	public BaseMapController createMapPainter(Game game) {
		return new MapController(game);
	}
}
