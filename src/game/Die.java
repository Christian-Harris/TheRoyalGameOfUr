package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Die extends ImageView{
	
	private int roll;
	
	public Die(){
		this.roll();
	}
	
	public void roll(){
		this.roll = (int)(Math.random() * 4) + 1;
		String image = "images//Roll" + this.roll + ".png";
		this.setImage(new Image(image));
	}
	
	public int getValue(){
		if(roll == 1 || roll == 2){
			return 1;
		}
		else{
			return 0;
		}
	}
}