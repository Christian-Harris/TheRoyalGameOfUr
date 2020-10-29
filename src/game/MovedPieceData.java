package game;

import java.io.Serializable;

/**
 * <h2>Game</h2>
 * <p>This class implements a MovedPieceData object which encodes the movement data for GamePieces.</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */

public class MovedPieceData implements Serializable{
	/** The GamePieceCode for this GamePiece.*/
	private GamePieceCode gamePieceCode;
	/** The path position where this GamePiece moved from.*/
	private int initialPathPosition;
	/** The path position where this GamePiece moved to.*/
	private int finalPathPosition;
	
	/**
	*	<p>Creates a MovedPieceData with the give GamePieceCode, initialPathPosition, and finalpathPosition.</p>
	*	@param gamePieceCode the GamePieceCode for this MovedPieceData.
	*	@param initialPathPosition the position where this GamePiece moved from.
	*	@param finalPathPosition the position where this GamePiece moved to.
	*/
	public MovedPieceData(GamePieceCode gamePieceCode, int initialPathPosition, int finalPathPosition){
		this.gamePieceCode = gamePieceCode;
		this.initialPathPosition = initialPathPosition;
		this.finalPathPosition = finalPathPosition;
	}
	
	/**
	*	<p>Gets the GamePieceCode for this MovedPieceData.</p>
	*	@return the GamePieceCode for this MovedPieceData.
	*/
	public GamePieceCode getGamePieceCode(){
		return this.gamePieceCode;
	}
	
	/**
	*	<p>Gets the initialpathPosition for this MovedPieceData.</p>
	*	@return the initialpathPosition for this MovedPieceData.
	*/
	public int getInitialPathPosition(){
		return this.initialPathPosition;
	}
	
	/**
	*	<p>Gets the finalPathPosition for this MovedPieceData.</p>
	*	@return the finalPathPosition for this MovedPieceData.
	*/
	public int getFinalPathPosition(){
		return this.finalPathPosition;
	}
}