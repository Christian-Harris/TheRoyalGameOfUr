package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameBoard extends GridPane{
	
	private ArrayList<CoordinatePair> blackPath = new ArrayList<CoordinatePair>();
	private ArrayList<CoordinatePair> whitePath = new ArrayList<CoordinatePair>();
	
	private Region highlighter = new Region();
	
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
		
		highlighter.setStyle("-fx-border-color: green; -fx-border-width: 5; -fx-min-height: 64; -fx-max-height: 64; -fx-min-width: 64; -fx-max-width: 64");
		this.add(highlighter, 0, 0);
		highlighter.setVisible(false);
		
		blackPath.add(new CoordinatePair(3,0));
		blackPath.add(new CoordinatePair(2,0));
		blackPath.add(new CoordinatePair(1,0));
		blackPath.add(new CoordinatePair(0,0));
		blackPath.add(new CoordinatePair(0,1));
		blackPath.add(new CoordinatePair(1,1));
		blackPath.add(new CoordinatePair(2,1));
		blackPath.add(new CoordinatePair(3,1));
		blackPath.add(new CoordinatePair(4,1));
		blackPath.add(new CoordinatePair(5,1));
		blackPath.add(new CoordinatePair(6,1));
		blackPath.add(new CoordinatePair(7,1));
		blackPath.add(new CoordinatePair(7,0));
		blackPath.add(new CoordinatePair(6,0));
		
		whitePath.add(new CoordinatePair(3,2));
		whitePath.add(new CoordinatePair(2,2));
		whitePath.add(new CoordinatePair(1,2));
		whitePath.add(new CoordinatePair(0,2));
		whitePath.add(new CoordinatePair(0,1));
		whitePath.add(new CoordinatePair(1,1));
		whitePath.add(new CoordinatePair(2,1));
		whitePath.add(new CoordinatePair(3,1));
		whitePath.add(new CoordinatePair(4,1));
		whitePath.add(new CoordinatePair(5,1));
		whitePath.add(new CoordinatePair(6,1));
		whitePath.add(new CoordinatePair(7,1));
		whitePath.add(new CoordinatePair(7,2));
		whitePath.add(new CoordinatePair(6,2));
		
		
		
	}
	
	public void addPiece(GamePiece gp){
		if(gp.getGamePieceCode() == GamePieceCode.BLACK){
			this.add(gp, this.blackPath.get(gp.getPathPosition()).getColumn(), this.blackPath.get(gp.getPathPosition()).getRow());
		}
		else{
			this.add(gp, this.whitePath.get(gp.getPathPosition()).getColumn(), this.whitePath.get(gp.getPathPosition()).getRow());
		}
		
	}
	
	public void updatePiece(GamePiece gp){
		if(gp.getGamePieceCode() == GamePieceCode.BLACK){
			this.setColumnIndex(gp, this.blackPath.get(gp.getPathPosition()).getColumn());
			this.setRowIndex(gp, this.blackPath.get(gp.getPathPosition()).getRow());
		}
		else{
			this.setColumnIndex(gp, this.whitePath.get(gp.getPathPosition()).getColumn());
			this.setRowIndex(gp, this.whitePath.get(gp.getPathPosition()).getRow());
		}
	}
	
	public void remove(GamePiece gp){
		this.getChildren().remove(gp);
	}
	
	public void highlight(int pathIndex, String path){
		if(pathIndex == 14){
			if(path.equals("black")){
				this.setColumnIndex(highlighter, 5);
				this.setRowIndex(highlighter, 0);
				highlighter.setVisible(true);
			}
			else{
				this.setColumnIndex(highlighter, 5);
				this.setRowIndex(highlighter, 2);
				highlighter.setVisible(true);
			}
		}
		else{
			if(path.equals("black")){
				this.setColumnIndex(highlighter, blackPath.get(pathIndex).getColumn());
				this.setRowIndex(highlighter, blackPath.get(pathIndex).getRow());
				highlighter.setVisible(true);
			}
			else{
				this.setColumnIndex(highlighter, whitePath.get(pathIndex).getColumn());
				this.setRowIndex(highlighter, whitePath.get(pathIndex).getRow());
				highlighter.setVisible(true);
			}
			
		}
	}
	
	public void unHighlight(){
		highlighter.setVisible(false);
	}
}