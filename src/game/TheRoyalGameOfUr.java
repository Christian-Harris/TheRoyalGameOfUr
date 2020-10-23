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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Date;
import java.util.ArrayList;

public class TheRoyalGameOfUr extends Application{
	private DataOutputStream dataOut = null;
	private DataInputStream dataIn = null;
	
	private Scene hostJoinScene;
	private Scene gameScene;
	private TextArea textOutput;
	
	private String player;
	
	private boolean running;
	
	private final GameBoard gameBoard = new GameBoard();
		
	private final GamePiece[] blackPieces = new GamePiece[7];
	private final GamePiece[] whitePieces = new GamePiece[7];
	
	public void start(Stage primaryStage){
		Pane hostJoin = new VBox();
		Button btHost = new Button("Host");
		Button btJoin = new Button("Join");
		Button btInstructions = new Button("How to Play");
		hostJoin.getChildren().addAll(btHost, btJoin, btInstructions);
		hostJoinScene = new Scene(hostJoin, 500, 300);
		
		GridPane game = new GridPane();
		MenuBar menuBar = new MenuBar();
		menuBar.setPrefWidth(900);
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
		
		for(int i = 0; i < 7; i++){
			blackPieces[i] = new GamePiece("black");
			whitePieces[i] = new GamePiece("white");
		}
		
		GamePieceContainer piecesBlackStart = new GamePieceContainer();
		GamePieceContainer piecesBlackEnd = new GamePieceContainer();
		GamePieceContainer piecesWhiteStart = new GamePieceContainer();
		GamePieceContainer piecesWhiteEnd = new GamePieceContainer();
		
		for(int i = 0; i < 7; i++){
			blackPieces[i].setOnMouseEntered(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e){
					int pathPosition = ((GamePiece)(e.getSource())).getPathPosition();
					int rollValue = 0;
					rollValue += p1D1.getValue();
					rollValue += p1D2.getValue();
					rollValue += p1D3.getValue();
					rollValue += p1D4.getValue();
					
					int pathIndex = pathPosition + rollValue;
					
					boolean occupied = false;
					for(int j = 0; j < 7; j++){
						if(blackPieces[j].getPathPosition() == pathIndex && pathIndex != 14){
							occupied = true;
						}
					}
					if(rollValue != 0){
						if(!occupied){
							if(pathIndex >= 0 && pathIndex <= 14){
								gameBoard.highlight(pathIndex, "black");
								((GamePiece)(e.getSource())).setMoveable(true);
							}
						}
					}
				}
			});
			
			blackPieces[i].setOnMouseExited(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e){
					gameBoard.unHighlight();
					((GamePiece)(e.getSource())).setMoveable(false);
				}
			});
			
			blackPieces[i].setOnMouseClicked(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e){
					if(e.getButton() == MouseButton.PRIMARY){
						if(((GamePiece)(e.getSource())).getMoveable() == true){
							int pathPosition = ((GamePiece)(e.getSource())).getPathPosition();
							int rollValue = 0;
							rollValue += p1D1.getValue();
							rollValue += p1D2.getValue();
							rollValue += p1D3.getValue();
							rollValue += p1D4.getValue();
							int pathIndex = pathPosition + rollValue;
							if(pathPosition == -1){
								((GamePiece)(e.getSource())).setPathPosition(pathIndex);
								piecesBlackStart.removePiece(((GamePiece)(e.getSource())));
								gameBoard.addPiece(((GamePiece)(e.getSource())));
							}
							if(pathPosition >= 0 && pathPosition <= 13){
								if(pathIndex < 14){
									if(pathIndex >= 4 && pathIndex <= 11){
										for(int j = 0; j < 7; j++){
											if(whitePieces[j].getPathPosition() == pathIndex){
												whitePieces[j].setPathPosition(-1);
												gameBoard.remove(whitePieces[j]);
												piecesWhiteStart.addPiece(whitePieces[j]);
											}
										}
									}
									((GamePiece)(e.getSource())).setPathPosition(pathIndex);
									gameBoard.updatePiece(((GamePiece)(e.getSource())));
								}
								if(pathIndex == 14){
									((GamePiece)(e.getSource())).setPathPosition(pathIndex);
									gameBoard.remove(((GamePiece)(e.getSource())));
									piecesBlackEnd.addPiece(((GamePiece)(e.getSource())));
								}
							}
						}
					}
				}
			});
			piecesBlackStart.addPiece(blackPieces[i]);
		}
		
		
		
		for(int i = 0; i < 7; i++){
			whitePieces[i].setOnMouseEntered(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e){
					int pathPosition = ((GamePiece)(e.getSource())).getPathPosition();
					int rollValue = 0;
					rollValue += p2D1.getValue();
					rollValue += p2D2.getValue();
					rollValue += p2D3.getValue();
					rollValue += p2D4.getValue();
					
					int pathIndex = pathPosition + rollValue;
					
					boolean occupied = false;
					for(int j = 0; j < 7; j++){
						if(whitePieces[j].getPathPosition() == pathIndex && pathIndex != 14){
							occupied = true;
						}
					}
					if(rollValue != 0){
						if(!occupied){
							if(pathIndex >= 0 && pathIndex <= 14){
								gameBoard.highlight(pathIndex, "white");
								((GamePiece)(e.getSource())).setMoveable(true);
							}
						}
					}
				}
			});
			
			whitePieces[i].setOnMouseExited(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e){
					gameBoard.unHighlight();
					((GamePiece)(e.getSource())).setMoveable(false);
				}
			});
			
			whitePieces[i].setOnMouseClicked(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e){
					if(e.getButton() == MouseButton.PRIMARY){
						if(((GamePiece)(e.getSource())).getMoveable() == true){
							int pathPosition = ((GamePiece)(e.getSource())).getPathPosition();
							int rollValue = 0;
							rollValue += p2D1.getValue();
							rollValue += p2D2.getValue();
							rollValue += p2D3.getValue();
							rollValue += p2D4.getValue();
							int pathIndex = pathPosition + rollValue;
							if(pathPosition == -1){
								((GamePiece)(e.getSource())).setPathPosition(pathIndex);
								piecesWhiteStart.removePiece(((GamePiece)(e.getSource())));
								gameBoard.addPiece(((GamePiece)(e.getSource())));
							}
							if(pathPosition >= 0 && pathPosition <= 13){
								if(pathIndex < 14){
									if(pathIndex >= 4 && pathIndex <= 11){
										for(int j = 0; j < 7; j++){
											if(blackPieces[j].getPathPosition() == pathIndex){
												blackPieces[j].setPathPosition(-1);
												gameBoard.remove(blackPieces[j]);
												piecesBlackStart.addPiece(blackPieces[j]);
											}
										}
									}
									((GamePiece)(e.getSource())).setPathPosition(pathIndex);
									gameBoard.updatePiece(((GamePiece)(e.getSource())));
								}
								if(pathIndex == 14){
									((GamePiece)(e.getSource())).setPathPosition(pathIndex);
									gameBoard.remove(((GamePiece)(e.getSource())));
									piecesWhiteEnd.addPiece(((GamePiece)(e.getSource())));
								}
							}
						}
					}
				}
			});
			
			piecesWhiteStart.addPiece(whitePieces[i]);
		}
		
		
		game.add(menuBar, 0, 0, 8, 1);
		game.add(player1Dice, 0, 1, 2, 1);
		game.add(piecesBlackStart, 2, 1 ,1, 2);
		game.add(piecesBlackEnd, 3, 1, 1, 2);
		game.add(textOutput, 5, 1, 3, 5);
		game.add(gameBoard, 0, 3, 4, 5);
		game.add(player2Dice, 0, 8, 2, 1);
		game.add(piecesWhiteStart, 2, 8, 1, 2);
		game.add(piecesWhiteEnd, 3, 8, 1, 2);
		game.add(textInput, 0, 9, 8, 1);
		
		gameScene = new Scene(game, 900, 500);
		
		
		primaryStage.setTitle("The Royal Game Of Ur");
		primaryStage.setScene(hostJoinScene);
		primaryStage.setResizable(false);
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
						player = "Player Black";
						while (true){
							String message = dataIn.readUTF();
							textOutput.appendText("Player White : " + message + "\n");
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
						player = "Player White";
						while(true){
							String message = dataIn.readUTF();
							textOutput.appendText("Player Black : " + message + "\n");
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
		
		player1Dice.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				if(e.getButton() == MouseButton.PRIMARY){
					p1D1.roll();
					p1D2.roll();
					p1D3.roll();
					p1D4.roll();
				}
			}
		});
		
		player2Dice.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				if(e.getButton() == MouseButton.PRIMARY){
					p2D1.roll();
					p2D2.roll();
					p2D3.roll();
					p2D4.roll();
				}
			}
		});
	}
}