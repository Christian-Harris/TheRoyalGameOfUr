package game;

/**
 * <h2>Game</h2>
 * <p>This class implements a CoordinatePair object which stores integer column/row coordinate data.</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */

public class CoordinatePair{
	/** The column of this CoordinatePair.*/
	int column;
	/** The row of this CoordinatePair.*/
	int row;
	
	/**
	*	<p>Creates a CoordinatePair with the given column and row values.</p>
	*	@param column the column of this CoordiantePair.
	*	@param row the row of this CoordinatePair.
	*/
	public CoordinatePair(int column, int row){
		this.column = column;
		this.row = row;
	}
	
	/**
	*	<p>Gets the column of this CoordinatePair.</p>
	*	@return the column of this CoordinatePair.
	*/
	public int getColumn(){
		return this.column;
	}
	
	/**
	*	<p>Gets the row of this CoordinatePair.</p>
	*	@return the row of this CoordinatePair.
	*/
	public int getRow(){
		return this.row;
	}
}