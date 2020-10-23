package game;

import java.io.Serializable;

public class DiceData implements Serializable{
	private int roll1;
	private int roll2;
	private int roll3;
	private int roll4;
	
	public DiceData(int roll1, int roll2, int roll3, int roll4){
		this.roll1 = roll1;
		this.roll2 = roll2;
		this.roll3 = roll3;
		this.roll4 = roll4;
	}
	
	public int getRoll1(){
		return this.roll1;
	}
	
	public int getRoll2(){
		return this.roll2;
	}
	
	public int getRoll3(){
		return this.roll3;
	}
	public int getRoll4(){
		return this.roll4;
	}
}