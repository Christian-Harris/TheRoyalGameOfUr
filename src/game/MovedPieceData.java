package game;

import java.io.Serializable;

public class MovedPieceData implements Serializable{
	private GamePieceCode gamePieceCode;
	private int initialPathPosition;
	private int finalPathPosition;
	
	public MovedPieceData(GamePieceCode gamePieceCode, int initialPathPosition, int finalPathPosition){
		this.gamePieceCode = gamePieceCode;
		this.initialPathPosition = initialPathPosition;
		this.finalPathPosition = finalPathPosition;
	}
	
	public GamePieceCode getGamePieceCode(){
		return this.gamePieceCode;
	}
	
	public int getInitialPathPosition(){
		return this.initialPathPosition;
	}
	
	public int getFinalPathPosition(){
		return this.finalPathPosition;
	}
}