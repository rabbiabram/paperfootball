package com.rnr.paperfootball.core;

import java.util.LinkedHashMap;

import com.rnr.paperfootball.LocalPlayer;
import com.rnr.paperfootball.TestPlayer;
import com.rnr.paperfootball.base.BasePlayer;

public class PlayerBuilder {

	public static LinkedHashMap<String, String> getPlayerTypes() {
		LinkedHashMap<String, String> types = new LinkedHashMap<String, String>();
		
		types.put("local", "Локальный игрок");
		types.put("test", "Тестовый игрок");
		return types;
	}
	public static BasePlayer createInstance(String id, String name, int color, PathGetter pathGetter) {
		if (id.equals("test")) {
			return new TestPlayer(name, color, pathGetter);
		} else if (id.equals("local")) {
			return new LocalPlayer(name, color, pathGetter);
		} else {
			return null;
		}
		
	}
}
