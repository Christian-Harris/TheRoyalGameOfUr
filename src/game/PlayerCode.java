package game;

import java.io.Serializable;

public enum PlayerCode implements Serializable{
	BLACK(0, "Black"),
	WHITE(1, "White");
	
	
	final int code;
	final String name;
	
	private PlayerCode(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}