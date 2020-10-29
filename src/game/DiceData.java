package game;

import java.io.Serializable;

/**
 * <h2>Game</h2>
 * <p>This class implements a DiceData object which encodes the roll data for a set of 4 dice.</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */

public class DiceData implements Serializable{
	/** The data for Die 1.*/
	private int roll1;
	/** The data for Die 2.*/
	private int roll2;
	/** The data for Die 3.*/
	private int roll3;
	/** The data for Die 4.*/
	private int roll4;
	
	/**
	*	<p>Creates a DiceData with the given roll values.</p>
	*	@param roll1 the first roll value.
	*	@param roll2 the second roll value.
	*	@param roll3 the third roll value.
	*	@param roll4 the fourth roll value.
	*/
	public DiceData(int roll1, int roll2, int roll3, int roll4){
		this.roll1 = roll1;
		this.roll2 = roll2;
		this.roll3 = roll3;
		this.roll4 = roll4;
	}
	
	/**
	*	<p>Gets the first roll value.</p>
	*	@return the first roll value.
	*/
	public int getRoll1(){
		return this.roll1;
	}
	
	/**
	*	<p>Gets the second roll value.</p>
	*	@return the second roll value.
	*/
	public int getRoll2(){
		return this.roll2;
	}
	
	/**
	*	<p>Gets the third roll value.</p>
	*	@return the third roll value.
	*/
	public int getRoll3(){
		return this.roll3;
	}
	
	/**
	*	<p>Gets the fourth roll value.</p>
	*	@return the fourth roll value.
	*/
	public int getRoll4(){
		return this.roll4;
	}
}