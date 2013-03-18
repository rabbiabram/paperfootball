package com.rnr.paperfootball.asketmap;

import com.rnr.paperfootball.base.BaseMap;
import com.rnr.paperfootball.base.BaseMapBuilder;
import com.rnr.paperfootball.base.BaseMapController;
import com.rnr.paperfootball.core.Game;
import com.rnr.paperfootball.map.MapController;

public class AsketMapBuilder extends BaseMapBuilder {
	@Override
	public BaseMap createMap() {
		return new AsketMap();
	}

	@Override
	public BaseMapController createMapPainter(Game game) {
		return new MapController(game);
	}

}
