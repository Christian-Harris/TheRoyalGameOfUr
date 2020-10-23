package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GamePiece extends ImageView{
	
	private int pathPosition;
	private boolean moveable;
	private String color;
	
	public GamePiece(String color){
		this.color = color;
		if(color.equalsIgnoreCase("white")){
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
	
	public void setPathPosition(int p){
		this.pathPosition = p;
	}
	
	public int getPathPosition(){
		return this.pathPosition;
	}
	
	public void setMoveable(boolean moveable){
		this.moveable = moveable;
	}
	
	public boolean getMoveable(){
		return this.moveable;
	}
	
	public String getColor(){
		return this.color;
	}
}