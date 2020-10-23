package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameBoard extends GridPane{
	
	public GameBoard(){
		this.add(new Tile(TileCode.ROSETTE), 0, 0);
		this.add(new Tile(TileCode.NORMAL1), 1, 0);
		this.add(new Tile(TileCode.NORMAL1), 2, 0);
		this.add(new Tile(TileCode.NORMAL1), 3, 0);
		this.add(new Tile(TileCode.EMPTY), 4, 0);
		this.add(new Tile(TileCode.EMPTY), 5, 0);
		this.add(new Tile(TileCode.ROSETTE), 6, 0);
		this.add(new Tile(TileCode.NORMAL1), 7, 0);
		this.add(new Tile(TileCode.NORMAL1), 0, 1);
		this.add(new Tile(TileCode.NORMAL1), 1, 1);
		this.add(new Tile(TileCode.NORMAL1), 2, 1);
		this.add(new Tile(TileCode.ROSETTE), 3, 1);
		this.add(new Tile(TileCode.NORMAL1), 4, 1);
		this.add(new Tile(TileCode.NORMAL1), 5, 1);
		this.add(new Tile(TileCode.NORMAL1), 6, 1);
		this.add(new Tile(TileCode.NORMAL1), 7, 1);
		this.add(new Tile(TileCode.ROSETTE), 0, 2);
		this.add(new Tile(TileCode.NORMAL1), 1, 2);
		this.add(new Tile(TileCode.NORMAL1), 2, 2);
		this.add(new Tile(TileCode.NORMAL1), 3, 2);
		this.add(new Tile(TileCode.EMPTY), 4, 2);
		this.add(new Tile(TileCode.EMPTY), 5, 2);
		this.add(new Tile(TileCode.ROSETTE), 6, 2);
		this.add(new Tile(TileCode.NORMAL1), 7, 2);
	}
}