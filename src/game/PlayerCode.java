package game;

import java.io.Serializable;

/**
 * <h2>Game</h2>
 * <p>Set of player codes for distinguishing between the black and white players .</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */

public enum PlayerCode implements Serializable{
	/** Constant for the black player.*/
	BLACK(0, "Black"),
	/** Constant for the white player.*/
	WHITE(1, "White");
	
	
	final int code;
	/** A String representation of this PlayerCode.*/
	final String name;
	
	private PlayerCode(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}