package game;

public class CoordinatePair{
	int column;
	int row;
	
	public CoordinatePair(int column, int row){
		this.column = column;
		this.row = row;
	}
	
	public int getColumn(){
		return this.column;
	}
	
	public int getRow(){
		return this.row;
	}
}