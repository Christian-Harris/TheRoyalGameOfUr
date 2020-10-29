package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * <h2>Game</h2>
 * <p>
 *	This class implements a Die object. These objects are associated with images and function as four sided dice.
 *	The dice have four points two of which are marked with white points. If a white point is facing up when rolled the
 *	die value is 1, 0 otherwise.
 *	</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */

public class GamePiece extends ImageView{
	
	/** An integer defining the current position along a path the GamePiece is on.*/
	private int pathPosition;
	/** Defines whether or not the main application is allowed to move this GamePiece.*/
	private boolean moveable;
	/** Defines whether this is a black or a white GamePiece.*/
	private GamePieceCode code;
	
	/**
	*	<p> Creates a GamePiece with the given GamePieceCode.</p>
	*	@param the GamePieceCode for this GamePiece.
	*/
	public GamePiece(GamePieceCode code){
		this.code = code;
		if(code == GamePieceCode.WHITE){
			this.setImage(new Image("images//PieceWhite.png"));
		}
		else{
			this.setImage(new Image("images//PieceBlack.png"));
		}
		this.setFitWidth(64);
		this.setFitHeight(64);
		this.pathPosition = -1;
		this.moveable = false;
	}
	
	/**
	*	<p>Sets the pathPosition of this GamePiece.</p>
	*	@param p the pathPosition for this GamePiece.
	*/
	public void setPathPosition(int p){
		this.pathPosition = p;
	}
	
	/**
	*	<p>Gets the pathPosition for this GamePiece.</p>
	*	@return the pathPosition for this GamePiece.</p>
	*/
	public int getPathPosition(){
		return this.pathPosition;
	}
	
	/**
	*	<p>Sets whether this GamePiece is moveable by the main application.</p>
	*	@param moveable defines whether or not this GamePiece is moveable.
	*/
	public void setMoveable(boolean moveable){
		this.moveable = moveable;
	}
	
	/**
	*	<p>Gets the moveable field for this GamePiece.</p>
	*	@return the moveable field for this GamePiece.
	*/
	public boolean getMoveable(){
		return this.moveable;
	}
	
	/**
	*	<p>Gets the GamePieceCode for this GamePiece.</p>
	*	@return the GamePieceCode for this GamePiece.
	*/
	public GamePieceCode getGamePieceCode(){
		return this.code;
	}
}