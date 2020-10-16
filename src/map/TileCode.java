package map;

public enum TileCode{
	EMPTY(0, "Empty"),
	NORMAL(1, "Normal"),
	ROSETTE(2, "Rosette");
	
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