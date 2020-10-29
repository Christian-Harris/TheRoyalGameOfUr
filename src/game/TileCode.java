package game;

/**
 * <h2>Game</h2>
 * <p>Set of tile codes for defining different kinds of tiles.</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */
 
public enum TileCode{
	/** Empty tiles which are displayed as transparent.*/
	EMPTY(0, "Empty"),
	/** A Rosette tile has a special marker.*/
	ROSETTE(1, "Rosette"),
	/** A Normal tile has no special markers.*/
	NORMAL1(2, "Normal1");
	
	
	final int code;
	/** A string representation of this TileCode.*/
	final String name;
	
	private TileCode(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	/**
	*	<p>Returns a String representation of this TileCode.</p>
	*	@return a String representation of this TileCode.
	*/
	public String getName(){
		return this.name;
	}
}