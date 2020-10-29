package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Die extends ImageView{
	
	private int roll = 0;
	
	public Die(){
		this.roll();
	}
	
	public void roll(){
		this.roll = (int)(Math.random() * 4) + 1;
		String image = "images//Roll" + this.roll + ".png";
		this.setImage(new Image(image));
		this.setFitWidth(64);
		this.setFitHeight(64);
	}
	
	public int getValue(){
		if(roll == 1 || roll == 2){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	public void setRoll(int roll){
		this.roll = roll;
		String image = "images//Roll" + this.roll + ".png";
		this.setImage(new Image(image));
	}
	
	public int getRoll(){
		return this.roll;
	}
}