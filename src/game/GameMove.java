package game;

import java.io.Serializable;

public class GameMove implements Serializable{
	private int type;
	private Object data;
	private PlayerCode playerCode;
	
	public GameMove(int type, Object data, PlayerCode playerCode){
		this.type = type;
		this.data = data;
		this.playerCode = playerCode;
	}
	
	public int getType(){
		return this.type;
	}
	
	public Object getData(){
		return this.data;
	}
	
	public PlayerCode getPlayerCode(){
		return this.playerCode;
	}
}