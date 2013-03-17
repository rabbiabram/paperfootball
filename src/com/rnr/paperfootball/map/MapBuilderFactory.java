package com.rnr.paperfootball.map;

import java.util.LinkedHashMap;

import com.rnr.paperfootball.asketmap.AsketMapBuilder;
import com.rnr.paperfootball.base.BaseMapBuilder;

public class MapBuilderFactory {
	public static LinkedHashMap<String, String> getMapTypes() {
		LinkedHashMap<String, String> types = new LinkedHashMap<String, String>();
		
		types.put("my", "Стандартная");
		types.put("ascet", "Аскетова");
		return types;
	}
	public static BaseMapBuilder createInstance(String id) {
		if (id.equals("my")) {
			return new MapBuilder();
		} else if (id.equals("ascet")) {
			return new AsketMapBuilder();
		} else {
			return null;
		}
		
	}
}
