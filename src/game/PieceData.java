package game;

import java.io.Serializable;

public class PieceData implements Serializable{
	
	private int initialPathPosition;
	private int finalPathPosition;
	
	public PieceData(int index, int initialPathPosition, int finalPathPosition){
		this.initialPathPosition = initialPathPosition;
		this.finalPathPosition = finalPathPosition;
	}
	
	public int getInitialPathPosition(){
		return this.initialPathPosition;
	}
	
	public int getFinalPathPosition(){
		return this.finalPathPosition;
	}
}