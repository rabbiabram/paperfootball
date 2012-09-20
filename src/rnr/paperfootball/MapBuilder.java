/**
 *
 */
package rnr.paperfootball;


/**
 * @author rodnover
 *
 */
public class MapBuilder extends BaseMapBuilder {

	@Override
	public BaseGameMap createMap() {
		return new GameMap();
	}

	@Override
	public BaseMapRanger createMapRanger(BaseGameMap gameMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseMapPainter createMapPainter(BaseGameMap gameMap) {
		return new MapPainter((GameMap)gameMap);
	}
}
