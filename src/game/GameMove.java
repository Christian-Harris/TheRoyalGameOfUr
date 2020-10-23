package game;

import java.io.Serializable;

public class GameMove implements Serializable{
	private int type;
	private Object data;
	
	public GameMove(int type, Object data){
		this.type = type;
		this.data = data;
	}
	
	public int getType(){
		return this.type;
	}
	
	public Object getData(){
		return this.data;
	}
}