/**
 *
 */
package com.rnr.paperfootball.base;

/**
 * @author rodnover
 * Базовый класс для генератора карты
 */
public abstract class BaseMapBuilder {
	public abstract BaseMap createMap();
	public abstract BaseMapPainter createMapPainter(BaseMap gameMap);
}
