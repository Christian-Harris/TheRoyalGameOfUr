package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView{
	private TileCode code;
	
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
	}
	
	public TileCode getCode(){
		return this.code;
	}
}