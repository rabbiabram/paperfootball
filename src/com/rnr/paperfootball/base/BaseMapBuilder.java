/**
 *
 */
package com.rnr.paperfootball.base;

import com.rnr.paperfootball.core.Game;

/**
 * @author rodnover
 * Базовый класс для генератора карты
 */
public abstract class BaseMapBuilder {
	public abstract BaseMap createMap();
	public abstract BaseMapController createMapPainter(Game game);
}
