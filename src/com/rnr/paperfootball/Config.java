package com.rnr.paperfootball;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;

public class Config {

	final public static String P1_TYPE = "player1.type";
	final public static String P1_NAME = "player1.name";
	final public static String P1_COLOR = "player1.color";

	final public static String P2_TYPE = "player2.type";
	final public static String P2_NAME = "player2.name";
	final public static String P2_COLOR = "player2.color";

	final public static String MAP_TYPE= "map.type";

	private static volatile Config instance;
	private String mTypePlayer1;
	private String mNamePlayer1;
	private int mColorPlayer1;

	private String mTypePlayer2;
	private String mNamePlayer2;
	private int mColorPlayer2;

	private String mMapType;

	private SharedPreferences mPreferences;

	public void setPreferences(SharedPreferences preferences) {
		this.mPreferences = preferences;
	}
	

	public void setPlayer1Type(String value) {
		this.mTypePlayer1 = value;
	}
	public String getPlayer1Type() {
		return this.mTypePlayer1;
	}

	public void setPlayer2Type(String value) {
		this.mTypePlayer2 = value;
	}

	public String getPlayer2Type() {
		return this.mTypePlayer2;
	}
	
	public void setPlayer1Name(String value) {
		this.mNamePlayer1 = value;
	}

	public String getPlayer1Name() {
		return this.mNamePlayer1;
	}

	public void setPlayer2Name(String value) {
		this.mNamePlayer2 = value;
	}

	public String getPlayer2Name() {
		return this.mNamePlayer2;
	}
	
	public int getPlayer1Color() {
		return this.mColorPlayer1;
	}

	public int getPlayer2Color() {
		return this.mColorPlayer2;
	}

	public String getMapType() {
		return this.mMapType;
	}
	
	public void setMapType(String value) {
		this.mMapType = value;
	}
	
	private Config() {
	}
	public static Config getInstance() {
		Config localInstance = instance;
		if (localInstance == null) {
			synchronized (Config.class) {
				localInstance = instance;
				if (localInstance == null) {
					Config.instance = localInstance = new Config();
				}
			}
		}
		return localInstance;
	}
	public void write() {
		synchronized (this) {
			Editor pref = this.mPreferences.edit(); 
			pref.putString(Config.P1_TYPE, this.mTypePlayer1);
			pref.putString(Config.P1_NAME, this.mNamePlayer1);
			pref.putInt(Config.P1_COLOR, this.mColorPlayer1);

			pref.putString(Config.P2_TYPE, this.mTypePlayer2);
			pref.putString(Config.P2_NAME, this.mNamePlayer2);
			pref.putInt(Config.P2_COLOR, this.mColorPlayer2);
			
			pref.putString(Config.MAP_TYPE, this.mMapType);

			pref.commit();
		}
	}
	
	public void read() {
		synchronized (this) {
			this.mTypePlayer1 = this.mPreferences.getString(Config.P1_TYPE, "local");
			this.mNamePlayer1 = this.mPreferences.getString(Config.P1_NAME, "Игрок 1");
			this.mColorPlayer1 = this.mPreferences.getInt(Config.P1_COLOR, Color.RED);

			this.mTypePlayer2 = this.mPreferences.getString(Config.P2_TYPE, "local");
			this.mNamePlayer2 = this.mPreferences.getString(Config.P2_NAME, "Игрок 2");
			this.mColorPlayer2 = this.mPreferences.getInt(Config.P2_COLOR, Color.GREEN);

			this.mMapType = this.mPreferences.getString(Config.MAP_TYPE, "my");
		}
	}
}
