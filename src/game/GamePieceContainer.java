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
		pieces.getChildren().add(new GamePiece("black"));
		lblPiecesCount = new Label(Integer.toString(this.piecesCount));
		this.getChildren().addAll(pieces, lblPiecesCount);
	}
	
	public void addPiece(){
		this.piecesCount++;
	}
	
	public int getPieces(){
		return this.piecesCount;
	}
}