package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

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

public class Die extends ImageView{
	
	/** Defines a value which determines both the image displayed and what the value of this die is.*/
	private int roll = 0;
	
	/** <p> Creates a Die with a random roll.</p>*/
	public Die(){
		this.roll();
	}
	
	/**
	*	<p>Rolls this Die to obtain a random roll between 1 and 4.</p>
	*/
	public void roll(){
		this.roll = (int)(Math.random() * 4) + 1;
		String image = "images//Roll" + this.roll + ".png";
		this.setImage(new Image(image));
		this.setFitWidth(64);
		this.setFitHeight(64);
	}
	
	/**
	*	<p>Returns the value of this Die based on its roll.</p>
	*	@return the value of this Die.
	*/
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