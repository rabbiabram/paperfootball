/**
 *
 */
package rnr.paperfootball;

/**
 * @author rodnover
 * Базовый класс для генератора карты
 */
public abstract class BaseMapBuilder {
	public abstract BaseGameMap createMap();
	public abstract BaseMapRanger createMapRanger(BaseGameMap gameMap);
	public abstract BaseMapPainter createMapPainter(BaseGameMap gameMap);
}
