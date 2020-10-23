package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GamePiece extends ImageView{
	
	public GamePiece(String color){
		if(color.equalsIgnoreCase("white")){
			this.setImage(new Image("images//PieceWhite.png"));
		}
		else{
			this.setImage(new Image("images//PieceBlack.png"));
		}
	}
}