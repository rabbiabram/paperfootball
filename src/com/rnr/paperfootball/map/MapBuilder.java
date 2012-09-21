/**
 *
 */
package com.rnr.paperfootball.map;

import com.rnr.paperfootball.base.BaseMap;
import com.rnr.paperfootball.base.BaseMapBuilder;
import com.rnr.paperfootball.base.BaseMapPainter;


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
	public BaseMapPainter createMapPainter(BaseMap gameMap) {
		return new MapPainter((Map)gameMap);
	}
}
