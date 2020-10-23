package game;

import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

public class GamePieceContainer extends VBox{
	
	private int piecesCount;
	private StackPane pieces;
	private Label lblPiecesCount;
	
	public GamePieceContainer(){
		this.piecesCount = 0;
		pieces = new StackPane();
		lblPiecesCount = new Label(Integer.toString(this.piecesCount));
		this.getChildren().addAll(lblPiecesCount, pieces);
		this.setMinSize(64, 128);
		this.setMaxSize(64, 128);
	}
	
	public void addPiece(GamePiece gp){
		this.pieces.getChildren().add(gp);
		this.piecesCount++;
		this.lblPiecesCount.setText(Integer.toString(this.piecesCount));
	}
	
	public void removePiece(GamePiece gp){
		this.pieces.getChildren().remove(gp);
		this.piecesCount--;
		this.lblPiecesCount.setText(Integer.toString(this.piecesCount));
	}
	
	public int getPieces(){
		return this.piecesCount;
	}
}