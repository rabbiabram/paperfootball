package com.rnr.paperfootball.core;

import com.rnr.paperfootball.base.BaseMapController;

public interface TouchHandler {
	boolean setPoint(BaseMapController mapController, float x, float y) throws WrongPathException;

	void undo(BaseMapController mapController);

}
