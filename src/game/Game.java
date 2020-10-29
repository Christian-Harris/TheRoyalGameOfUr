package game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.WindowEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Date;
import java.util.ArrayList;

public class Game extends Application{
	private ObjectOutputStream dataOut = null;
	private ObjectInputStream dataIn = null;
	
	private Scene hostJoinScene;
	private Pane hostJoinPane;
	private Button btHost;
	private Button btJoin;
	private Button btInstructions;
	
	private Scene gameScene;
	private GridPane gamePane;
	private MenuBar menuBar;
	private Menu optionsMenu;
	private TextArea textOutput;
	private TextField textInput;
	private HBox blackPlayerDiceBox;
	private HBox whitePlayerDiceBox;
	
	private String playerId;
	
	private boolean running;
	
	private GameBoard gameBoard = new GameBoard();
	
	private Die[] blackPlayerDice;
	private Die[] whitePlayerDice;
		
	private GamePiece[] blackPieces;
	private GamePiece[] whitePieces;
	
	private GamePieceContainer blackPiecesStart;
	private GamePieceContainer blackPiecesEnd;
	private GamePieceContainer whitePiecesStart;
	private GamePieceContainer whitePiecesEnd;
	
	public void start(Stage primaryStage){
		hostJoinPane = new VBox();
		btHost = new Button("Host");
		btJoin = new Button("Join");
		btInstructions = new Button("How To Play");
		hostJoinPane.getChildren().addAll(btHost, btJoin, btInstructions);
		hostJoinScene = new Scene(hostJoin, 500, 300);
		
		gamePane = new GridPane();
		menuBar = new MenuBar();
		menuBar.setPrefWidth(900);
		optionsMenu = new Menu("Options");
		menuBar.getMenus().add(options);
		textOutput = new TextArea();
		textInput = new TextField();
		
		blackPlayerDiceBox = new HBox();
		whitePlayerDiceBox = new HBox();
		for(int i = 0; i < 4; i++){
			blackPlayerDice[i] = new Die();
			blackPlayerDiceBox.getChildren().add(blackPlayerDice[i]);
			whitePlayerDice[i] = new Die();
			whitePlayerDiceBox.getChildren().add(whitePlayerDice[i]);
		}
		
	}
}