package game;

import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

/**
 * <h2>Game</h2>
 * <p>This class implements a container for GamePieces.</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */

public class GamePieceContainer extends VBox{
	/** An integer keeping track of how many GamePieces are in this container.*/
	private int piecesCount;
	/** A stack pane used to store the GamePieces allowing them to display properly.*/
	private StackPane pieces;
	/** A label for displaying piecesCount.*/
	private Label lblPiecesCount;
	
	/** <p>Creates an empty container.</p>*/
	public GamePieceContainer(){
		this.piecesCount = 0;
		pieces = new StackPane();
		lblPiecesCount = new Label(Integer.toString(this.piecesCount));
		this.getChildren().addAll(lblPiecesCount, pieces);
		this.setMinSize(64, 128);
		this.setMaxSize(64, 128);
	}
	
	/**
	*	<p>Adds GamePieces to this GamePieceContainer.</p>
	*	@param gp the GamePiece to be added.
	*/
	public void addPiece(GamePiece gp){
		this.pieces.getChildren().add(gp);
		this.piecesCount++;
		this.lblPiecesCount.setText(Integer.toString(this.piecesCount));
	}
	
	/**
	*	<p>Removes GamePieces from this GamePieceContainer.</p>
	*	@param gp the GamePiece to be removed.
	*/
	public void removePiece(GamePiece gp){
		this.pieces.getChildren().remove(gp);
		this.piecesCount--;
		this.lblPiecesCount.setText(Integer.toString(this.piecesCount));
	}
	
	/**
	*	<p>Gets this piecesCount.</p>
	*	@return this piecesCount.
	*/
	public int getPiecesCount(){
		return this.piecesCount;
	}
}