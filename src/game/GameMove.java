package game;

import java.io.Serializable;

/**
 * <h2>Game</h2>
 * <p>This class implements a GameMove object which is used to send game information between clients and servers.</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */

public class GameMove implements Serializable{
	/** An integer type code defining which kind of information has been sent.*/
	private int type;
	/** The data to be sent.*/
	private Object data;
	/** The player code of the player sending the data.*/
	private PlayerCode playerCode;
	
	/**
	*	<p>Creates a GameMove with the given type, data, and playerCode.</p>
	*	@param type the code for this GameMove.
	*	@param data the data to be sent by this GameMove.
	*	@param playerCode the PlayerCode of the player sending this GameMove.
	*/
	public GameMove(int type, Object data, PlayerCode playerCode){
		this.type = type;
		this.data = data;
		this.playerCode = playerCode;
	}
	
	/**
	*	<p>Gets the type of this GameMove.</p>
	*	@return the type of this GameMove.
	*/
	public int getType(){
		return this.type;
	}
	
	/**
	*	<p>Gets the data of this GameMove.</p>
	*	@return the data of this GAmeMove.
	*/
	public Object getData(){
		return this.data;
	}
	
	/**
	*	<p>Gets the PlayerCode of this GameMove.</p>
	*	@return the PlayerCode of this GameMove.
	*/
	public PlayerCode getPlayerCode(){
		return this.playerCode;
	}
}