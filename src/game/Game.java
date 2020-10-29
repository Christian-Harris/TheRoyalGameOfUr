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
import java.net.SocketException;

import java.util.Date;
import java.util.ArrayList;

public class Game extends Application{
	private ObjectOutputStream dataOut;
	private ObjectInputStream dataIn;
	private ServerSocket serverSocket;
	private Socket socket;
	
	private PlayerCode playerCode;
	
	private Scene hostJoinScene;
	private Pane hostJoinPane;
	private Button btHost;
	private Button btJoin;
	private TextField connectionAddress;
	private Button btInstructions;
	
	
	private Stage gameStage;
	private Scene gameScene;
	private GridPane gamePane;
	private MenuBar menuBar;
	private Menu optionsMenu;
	private TextArea textOutput;
	private TextField textInput;
	private HBox blackPlayerDiceBox;
	private HBox whitePlayerDiceBox;
	
	private boolean running;
	
	private GameBoard gameBoard = new GameBoard();
	
	private Die[] blackPlayerDice;
	private Die[] whitePlayerDice;
		
	private GamePiece[] blackPlayerPieces;
	private GamePiece[] whitePlayerPieces;
	
	private GamePieceContainer blackPlayerPiecesStart;
	private GamePieceContainer blackPlayerPiecesEnd;
	private GamePieceContainer whitePlayerPiecesStart;
	private GamePieceContainer whitePlayerPiecesEnd;
	
	public void start(Stage primaryStage){
		hostJoinPane = new VBox();
		btHost = new Button("Host");
		btJoin = new Button("Join");
		HostJoinActionHandler hostJoinAction = new HostJoinActionHandler();
		btHost.setOnAction(hostJoinAction);
		btJoin.setOnAction(hostJoinAction);
		connectionAddress = new TextField("localhost");
		btInstructions = new Button("How To Play");
		hostJoinPane.getChildren().addAll(btHost, btJoin,connectionAddress, btInstructions);
		hostJoinScene = new Scene(hostJoinPane, 500, 300);
		
		gamePane = new GridPane();
		menuBar = new MenuBar();
		menuBar.setPrefWidth(900);
		optionsMenu = new Menu("Options");
		menuBar.getMenus().add(optionsMenu);
		textOutput = new TextArea();
		textInput = new TextField();
		textInput.setOnKeyPressed(new MessengerHandler());
		
		
		blackPlayerDiceBox = new HBox();
		whitePlayerDiceBox = new HBox();
		DieRollHandler dieRollHandler = new DieRollHandler();
		blackPlayerDiceBox.setOnMouseClicked(dieRollHandler);
		whitePlayerDiceBox.setOnMouseClicked(dieRollHandler);
		
		blackPlayerDice = new Die[4];
		whitePlayerDice = new Die[4];
		for(int i = 0; i < 4; i++){
			blackPlayerDice[i] = new Die();
			blackPlayerDiceBox.getChildren().add(blackPlayerDice[i]);
			whitePlayerDice[i] = new Die();
			whitePlayerDiceBox.getChildren().add(whitePlayerDice[i]);
		}
		
		blackPlayerPiecesStart = new GamePieceContainer();
		blackPlayerPiecesEnd = new GamePieceContainer();
		whitePlayerPiecesStart = new GamePieceContainer();
		whitePlayerPiecesEnd = new GamePieceContainer();
		blackPlayerPieces = new GamePiece[7];
		whitePlayerPieces = new GamePiece[7];
		HoverOverPieceEnteredHandler hoverOverPieceEnteredHandler = new HoverOverPieceEnteredHandler();
		HoverOverPieceExitedHandler hoverOverPieceExitedHandler = new HoverOverPieceExitedHandler();
		GamePieceClickedHandler gamePieceClickedHandler = new GamePieceClickedHandler();
		for(int i = 0; i < 7; i++){
			blackPlayerPieces[i] = new GamePiece(GamePieceCode.BLACK);
			blackPlayerPieces[i].setOnMouseEntered(hoverOverPieceEnteredHandler);
			blackPlayerPieces[i].setOnMouseExited(hoverOverPieceExitedHandler);
			blackPlayerPieces[i].setOnMouseClicked(gamePieceClickedHandler);
			blackPlayerPiecesStart.addPiece(blackPlayerPieces[i]);
			
			whitePlayerPieces[i] = new GamePiece(GamePieceCode.WHITE);
			whitePlayerPieces[i].setOnMouseEntered(hoverOverPieceEnteredHandler);
			whitePlayerPieces[i].setOnMouseExited(hoverOverPieceExitedHandler);
			whitePlayerPieces[i].setOnMouseClicked(gamePieceClickedHandler);
			whitePlayerPiecesStart.addPiece(whitePlayerPieces[i]);
		}
		
		gamePane.add(menuBar, 0, 0, 8, 1);
		gamePane.add(blackPlayerDiceBox, 0, 1, 2, 1);
		gamePane.add(blackPlayerPiecesStart, 2, 1 ,1, 2);
		gamePane.add(blackPlayerPiecesEnd, 3, 1, 1, 2);
		gamePane.add(textOutput, 5, 1, 3, 5);
		gamePane.add(gameBoard, 0, 3, 4, 5);
		gamePane.add(whitePlayerDiceBox, 0, 8, 2, 1);
		gamePane.add(whitePlayerPiecesStart, 2, 8, 1, 2);
		gamePane.add(whitePlayerPiecesEnd, 3, 8, 1, 2);
		gamePane.add(textInput, 0, 9, 8, 1);
		
		gameScene = new Scene(gamePane, 900, 500);
		
		gameStage = new Stage();
		gameStage.setTitle("The Royal Game Of Ur");
		gameStage.setScene(hostJoinScene);
		gameStage.setResizable(false);
		gameStage.show();
		
		gameStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent e){
				Platform.exit();
				System.exit(0);
			}
		});
	}
	
	private class HostJoinActionHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e){			
			gameStage.setScene(gameScene);
			
			new Thread(() ->{
				try{
					if(((Button)(e.getSource())).getText().equals("Host")){
						playerCode = PlayerCode.BLACK;
						whitePlayerDiceBox.setDisable(true);
						for(int i = 0; i < 7; i++){
							whitePlayerPieces[i].setDisable(true);
						}
						serverSocket = new ServerSocket(8000);
						textOutput.appendText("Server started at " + new Date() + "\n");
						socket = serverSocket.accept();
						textOutput.appendText("Client connected at " + new Date() + "\n");
					}
					else{
						blackPlayerDiceBox.setDisable(true);
						for(int i = 0; i < 7; i++){
							blackPlayerPieces[i].setDisable(true);
						}
						playerCode = PlayerCode.WHITE;
						socket = new Socket(connectionAddress.getText().trim(), 8000);
						textOutput.appendText("Connected to a server at " + new Date() + "\n");
					}
					dataOut = new ObjectOutputStream(socket.getOutputStream());
					dataIn = new ObjectInputStream(socket.getInputStream());
					
					while (true){
						GameMove move = (GameMove)(dataIn.readObject());
						if(move.getType() == 0){
							String message = (String)(move.getData());
							textOutput.appendText(move.getPlayerCode().getName() + " player : " + message + "\n");
						}
						else if(move.getType() == 1){
							DiceData data = (DiceData)(move.getData());
							if(playerCode == PlayerCode.BLACK){
								whitePlayerDice[0].setRoll(data.getRoll1());
								whitePlayerDice[1].setRoll(data.getRoll2());
								whitePlayerDice[2].setRoll(data.getRoll3());
								whitePlayerDice[3].setRoll(data.getRoll4());
							}
							else{
								blackPlayerDice[0].setRoll(data.getRoll1());
								blackPlayerDice[1].setRoll(data.getRoll2());
								blackPlayerDice[2].setRoll(data.getRoll3());
								blackPlayerDice[3].setRoll(data.getRoll4());
							}
						}
						else if(move.getType() == 2){
							Platform.runLater(()->{
								MovedPieceData data = (MovedPieceData)(move.getData());
								if(data.getInitialPathPosition() == -1){
									if(playerCode == PlayerCode.BLACK){
										int index = -1;
										for(int i = 0; i < 7; i++){
											if(whitePlayerPieces[i].getPathPosition() == -1){
												index = i;
												break;
											}
										}
										whitePlayerPiecesStart.removePiece(whitePlayerPieces[index]);
										whitePlayerPieces[index].setPathPosition(data.getFinalPathPosition());
										gameBoard.addPiece(whitePlayerPieces[index]);
									}
									else{
										int index = -1;
										for(int i = 0; i < 7; i++){
											if(blackPlayerPieces[i].getPathPosition() == -1){
												index = i;
												break;
											}
										}
										blackPlayerPiecesStart.removePiece(blackPlayerPieces[index]);
										blackPlayerPieces[index].setPathPosition(data.getFinalPathPosition());
										gameBoard.addPiece(blackPlayerPieces[index]);
									}
								}
								//else if(){
									
								//}
							});
						}
					}
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
				catch(ClassNotFoundException ex){
					ex.printStackTrace();
				}
			}).start();
		}
	}
	
	private class MessengerHandler implements EventHandler<KeyEvent>{
		@Override
		public void handle(KeyEvent e){
			if(e.getCode() == KeyCode.ENTER){
				try{
					String message = textInput.getText().trim();
					GameMove move = new GameMove(0, message, playerCode);
					dataOut.writeObject(move);
					dataOut.flush();
					textInput.setText("");
					textOutput.appendText(playerCode.getName() + " player : " + message + "\n");
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class HoverOverPieceEnteredHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent e){
			int pathPosition = ((GamePiece)(e.getSource())).getPathPosition();
			int rollValue = 0;
			for(int i = 0; i < 4; i++){
				if(((GamePiece)(e.getSource())).getGamePieceCode() == GamePieceCode.BLACK){
					rollValue += blackPlayerDice[i].getValue();
				}
				else{
					rollValue += whitePlayerDice[i].getValue();
				}
			}			
			int pathIndex = pathPosition + rollValue;
			
			boolean occupied = false;
			for(int i = 0; i < 7; i++){
				if(((GamePiece)(e.getSource())).getGamePieceCode() == GamePieceCode.BLACK){
					if(blackPlayerPieces[i].getPathPosition() == pathIndex && pathIndex != 14){
						occupied = true;
					}
				}
				else{
					if(whitePlayerPieces[i].getPathPosition() == pathIndex && pathIndex != 14){
						occupied = true;
					}
				}
			}
			if(rollValue != 0){
				if(!occupied){
					if(pathIndex >= 0 && pathIndex <= 14){
						gameBoard.highlight(pathIndex, ((GamePiece)(e.getSource())).getGamePieceCode().getName().toLowerCase());
						((GamePiece)(e.getSource())).setMoveable(true);
					}
				}
			}
		}
	}
	
	private class HoverOverPieceExitedHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent e){
			gameBoard.unHighlight();
			((GamePiece)(e.getSource())).setMoveable(false);
		}
	}
	
	private class DieRollHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent e){
			if(e.getButton() == MouseButton.PRIMARY){
				for(int i = 0; i < 4; i++){
					if(playerCode == PlayerCode.BLACK){
						blackPlayerDice[i].roll();
					}
					else{
						whitePlayerDice[i].roll();
					}
				}
				try{
					DiceData data;
					if(playerCode == PlayerCode.BLACK){
						data = new DiceData(blackPlayerDice[0].getRoll(), blackPlayerDice[1].getRoll(), blackPlayerDice[2].getRoll(), blackPlayerDice[3].getRoll());
					}
					else{
						data = new DiceData(whitePlayerDice[0].getRoll(), whitePlayerDice[1].getRoll(), whitePlayerDice[2].getRoll(), whitePlayerDice[3].getRoll());
					}
					GameMove move = new GameMove(1, data, playerCode);
					dataOut.writeObject(move);
					dataOut.flush();
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}			
	}
	
	private class GamePieceClickedHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent e){
			if(e.getButton() == MouseButton.PRIMARY){
				if(((GamePiece)(e.getSource())).getMoveable() == true){
					int pathPosition = ((GamePiece)(e.getSource())).getPathPosition();
					int rollValue = 0;
					for(int i = 0; i < 4; i++){
						if(playerCode == PlayerCode.BLACK){
							rollValue += blackPlayerDice[i].getValue();
						}
						else{
							rollValue += whitePlayerDice[i].getValue();
						}
					}
					int pathIndex = pathPosition + rollValue;
					if(pathPosition == -1){
						((GamePiece)(e.getSource())).setPathPosition(pathIndex);
						if(playerCode == PlayerCode.BLACK){
							blackPlayerPiecesStart.removePiece(((GamePiece)(e.getSource())));
						}
						else{
							whitePlayerPiecesStart.removePiece(((GamePiece)(e.getSource())));
						}
						try{
							gameBoard.addPiece(((GamePiece)(e.getSource())));
							MovedPieceData data = new MovedPieceData(((GamePiece)(e.getSource())).getGamePieceCode(), pathPosition, pathIndex);
							GameMove move = new GameMove(2, data, playerCode);
							dataOut.writeObject(move);
							dataOut.flush();
						}
						catch(IOException ex){
							ex.printStackTrace();
						}
					}
					else if(pathPosition >= 0 && pathPosition <= 13){
						if(pathIndex < 14){
							if(pathIndex >= 4 && pathIndex <= 11){
								for(int i = 0; i < 7; i++){
									if(playerCode == PlayerCode.BLACK){
										if(whitePlayerPieces[i].getPathPosition() == pathIndex){
											whitePlayerPieces[i].setPathPosition(-1);
											gameBoard.remove(whitePlayerPieces[i]);
											whitePlayerPiecesStart.addPiece(whitePlayerPieces[i]);
										}
									}
									else{
										if(blackPlayerPieces[i].getPathPosition() == pathIndex){
										blackPlayerPieces[i].setPathPosition(-1);
										gameBoard.remove(blackPlayerPieces[i]);
										blackPlayerPiecesStart.addPiece(blackPlayerPieces[i]);
										}
									}
								}
							}
							((GamePiece)(e.getSource())).setPathPosition(pathIndex);
							gameBoard.updatePiece(((GamePiece)(e.getSource())));
						}
						else if(pathIndex == 14){
							((GamePiece)(e.getSource())).setPathPosition(pathIndex);
							gameBoard.remove(((GamePiece)(e.getSource())));
							if(playerCode == PlayerCode.BLACK){
								blackPlayerPiecesEnd.addPiece(((GamePiece)(e.getSource())));
							}
							else{
								whitePlayerPiecesEnd.addPiece(((GamePiece)(e.getSource())));
							}
						}
					}
				}
			}
		}
	}
}