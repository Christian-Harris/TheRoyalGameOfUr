package game;

/**
 * <h2>Game</h2>
 * <p>Set of game piece codes for defining different kinds of game pieces.</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */
 
public enum GamePieceCode{
	/** Constant for a black GamePiece.*/
	BLACK(0, "Black"),
	/** Constant for a white GamePiece.*/
	WHITE(1, "White");
	
	
	final int code;
	/** A String representation for this GamePieceCode.*/
	final String name;
	
	private GamePieceCode(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	/**
	*	<p>Gets a String representation for this GamePieceCode.</p>
	*	@return a String representation for this GamePieceCode.
	*/
	public String getName(){
		return this.name;
	}
}