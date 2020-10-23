package game;

public enum TileCode{
	EMPTY(0, "Empty"),
	ROSETTE(1, "Rosette"),
	NORMAL1(2, "Normal1");
	
	
	final int code;
	final String name;
	
	private TileCode(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}