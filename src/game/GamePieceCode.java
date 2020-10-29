package game;

public enum GamePieceCode{
	BLACK(0, "Black"),
	WHITE(1, "White");
	
	
	final int code;
	final String name;
	
	private GamePieceCode(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}