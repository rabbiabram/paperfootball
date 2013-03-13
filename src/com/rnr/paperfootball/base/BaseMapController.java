/**
 *
 */
package com.rnr.paperfootball.base;

import java.util.Vector;

import com.rnr.paperfootball.core.Cell;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author rodnover
 *
 */
public abstract class BaseMapController {
	public abstract void draw(Canvas canvas);
	public abstract Cell getCell(float x, float y);
	public abstract void setCurrentPath(Vector<Cell> path);
	public abstract Rect getRect(int width, int height);
}
