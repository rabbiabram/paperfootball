package com.rnr.paperfootball.core;

import com.rnr.paperfootball.base.BaseMap;


public interface GameCallback {
	void repaint(BaseMap map);
	void endOfGame(int indexPlayer);
}
