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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Date;


 import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;

public class TheRoyalGameOfUr extends Application{
	private DataOutputStream dataOut = null;
	private DataInputStream dataIn = null;
	
	private Scene hostJoinScene;
	private Scene gameScene;
	private TextArea textOutput;
	
	private String player;
	
	private boolean running;
	
	public void start(Stage primaryStage){
		Pane hostJoin = new VBox();
		Button btHost = new Button("Host");
		Button btJoin = new Button("Join");
		Button btInstructions = new Button("How to Play");
		hostJoin.getChildren().addAll(btHost, btJoin, btInstructions);
		hostJoinScene = new Scene(hostJoin, 1000, 500);
		
		GridPane game = new GridPane();
		MenuBar menuBar = new MenuBar();
		Menu options = new Menu("Options");
		menuBar.getMenus().add(options);
		TextArea textOutput = new TextArea();
		TextField textInput = new TextField();
		
		HBox player1Dice = new HBox();
		HBox player2Dice = new HBox();
		
		Die p1D1 = new Die();
		Die p1D2 = new Die();
		Die p1D3 = new Die();
		Die p1D4 = new Die();
		
		Die p2D1 = new Die();
		Die p2D2 = new Die();
		Die p2D3 = new Die();
		Die p2D4 = new Die();
		
		player1Dice.getChildren().addAll(p1D1, p1D2, p1D3, p1D4);
		player2Dice.getChildren().addAll(p2D1, p2D2, p2D3, p2D4);
		
		GameBoard gameBoard = new GameBoard();
		
		GamePieceContainer piecesBlackStart = new GamePieceContainer();
		GamePieceContainer piecesBlackEnd = new GamePieceContainer();
		GamePieceContainer piecesWhiteStart = new GamePieceContainer();
		GamePieceContainer piecesWhiteEnd = new GamePieceContainer();
		
		game.add(menuBar, 0, 0, 6, 1);
		game.add(player1Dice, 0, 1, 2, 1);
		game.add(piecesBlackStart, 2, 1 ,1, 2);
		game.add(piecesBlackEnd, 3, 1, 1, 2);
		game.add(textOutput, 4, 1, 2, 5);
		game.add(gameBoard, 0, 3, 4, 3);
		game.add(player2Dice, 0, 6, 2, 1);
		game.add(piecesWhiteStart, 2, 6, 1, 2);
		game.add(piecesWhiteEnd, 3, 6, 1, 2);
		game.add(textInput, 0, 7, 6, 1);
		
		gameScene = new Scene(game, 1000, 500);
		
		
		primaryStage.setTitle("The Royal Game Of Ur");
		primaryStage.setScene(hostJoinScene);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent e){
				Platform.exit();
				System.exit(0);
			}
		});
		
		btHost.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				primaryStage.setScene(gameScene);
				new Thread(() ->{
					try{
						ServerSocket serverSocket = new ServerSocket(8000);
						textOutput.appendText("Server started at " + new Date() + "\n");
						Socket socket = serverSocket.accept();
						textOutput.appendText("Client connected at " + new Date() + "\n");
						dataOut = new DataOutputStream(socket.getOutputStream());
						dataIn = new DataInputStream(socket.getInputStream());
						player = "Player 1";
						while (true){
							String message = dataIn.readUTF();
							textOutput.appendText("Player 2 : " + message + "\n");
						}
					}
					catch(IOException ex){
						ex.printStackTrace();
					}
				}).start();
			}
		});
		
		btJoin.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				primaryStage.setScene(gameScene);
				new Thread(() ->{
					try{
						Socket socket = new Socket("localhost", 8000);
						textOutput.appendText("Connected to a server at " + new Date() + "\n");
						dataOut = new DataOutputStream(socket.getOutputStream());
						dataIn = new DataInputStream(socket.getInputStream());
						player = "Player 2";
						while(true){
							String message = dataIn.readUTF();
							textOutput.appendText("Player 1 : " + message + "\n");
						}
					}
					catch(IOException ex){
						ex.printStackTrace();
					}
				}).start();
			}
		});
		
		textInput.setOnKeyPressed(new EventHandler<KeyEvent>(){
		public void handle(KeyEvent e){
			if(e.getCode() == KeyCode.ENTER){
				try{
					String message = textInput.getText().trim();
					dataOut.writeUTF(message);
					dataOut.flush();
					textInput.setText("");	
					textOutput.appendText(player + " : " + message + "\n");
				}
				catch(IOException ex){
					System.err.println(ex);
				}
			}
		}
	});
	}
}