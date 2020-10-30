package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * <h2>Game</h2>
 * <p>This class defines a Tile object which is used to construct a GameBoard.</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */

public class Tile extends ImageView{
	/** The TileCode of this tile defining which kind of tile it is.*/
	private TileCode code;
	
	/** 
	*	<p>Creates a Tile with the given TileCode</p>
	*	@param code the TileCode for this Tile.
	*/
	public Tile(TileCode code){
		this.code = code;
		if(code == TileCode.EMPTY){
			this.setImage(new Image("images//TileEmpty.png"));
		}
		else if(code == TileCode.ROSETTE){
			this.setImage(new Image("images//TileRosette.png"));
		}
		else if(code == TileCode.NORMAL1){
			this.setImage(new Image("images//TileNormal1.png"));
		}
		this.setFitWidth(64);
		this.setFitHeight(64);
	}
	
	/**
	*	<p>Returns the TileCode of this Tile.
	*	@return the TileCode of this Tile.
	*/
	public TileCode getCode(){
		return this.code;
	}
}