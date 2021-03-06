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
import java.io.File;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.Date;

/**
 * <h2>Game</h2>
 * <p>This class is an implementation of a client/server system which plays "The Royal Game of Ur".</p>
 * <p>Created on 29 October 2020</p>
 * @author Christian Harris
 */

public class Game extends Application{
	/** An output stream for game data.*/
	private ObjectOutputStream dataOut;
	/** An input stream for game data.*/
	private ObjectInputStream dataIn;
	/** A server socket for connecting to a potential client.*/
	private ServerSocket serverSocket;
	/** A socket for connecting to a potential server.*/
	private Socket socket;
	
	/** A PlayerCode which defines this player as either the black player or the white player.*/
	private PlayerCode playerCode;
	
	/** A scene for a host/join screen.*/
	private Scene hostJoinScene;
	/** A pain for a host/join screen.*/
	private Pane hostJoinPane;
	/** A button to launch the application as a server.*/
	private Button btHost;
	/** A button to launch the application as a client.*/
	private Button btJoin;
	/** A TextField to enter the address to connect to.*/
	private TextField connectionAddress;
	/** A button to display a screen with instructions.*/
	private Button btInstructions;
	
	/** A stage for placing scenes related to the game.*/
	private Stage gameStage;
	/** A scene for placing game nodes.*/
	private Scene gameScene;
	/** A GridPane for organizing game nodes.*/
	private GridPane gamePane;
	/** A TextArea to display messages.*/
	private TextArea textOutput;
	/** A TextField to enter messages.*/
	private TextField textInput;
	/** An HBox which holds nodes defining player dice for the black player.*/
	private HBox blackPlayerDiceBox;
	/** An HBox which holds nodes defining player dice for the white player.*/
	private HBox whitePlayerDiceBox;
	
	/** A scene for displaying game instructions.*/
	private Scene instructionScene;
	
	/** An instance of a game board.*/
	private GameBoard gameBoard = new GameBoard();
	
	/** An array of dice to hold references to the black players dice.*/
	private Die[] blackPlayerDice;
	/** An array of dice to hold reerences to the white players dice.*/
	private Die[] whitePlayerDice;
		
	/** An array of GamePieces to hold references to the black players pieces.*/
	private GamePiece[] blackPlayerPieces;
	/** An array of GamePieces to hold references to the white players pieces.*/
	private GamePiece[] whitePlayerPieces;
	
	/** Defines the starting area for the black players GamePieces.*/
	private GamePieceContainer blackPlayerPiecesStart;
	/** Defines the ending area for the black players GamePieces.*/
	private GamePieceContainer blackPlayerPiecesEnd;
	/** Defines the starting area for the white players GamePieces.*/
	private GamePieceContainer whitePlayerPiecesStart;
	/** Defines the ending area for the white players GamePieces.*/
	private GamePieceContainer whitePlayerPiecesEnd;
	
	public void start(Stage primaryStage){
		//Instantiate elements for the hostJoinScene.
		hostJoinPane = new VBox();
		btHost = new Button("Host");
		btJoin = new Button("Join");
		//Add an action listener to the Host and Join buttons for making connections.
		HostJoinActionHandler hostJoinAction = new HostJoinActionHandler();
		btHost.setOnAction(hostJoinAction);
		btJoin.setOnAction(hostJoinAction);
		//The connectionAddress is initialized with localhost so if no address is entered application will automatically attempt to connect to a server running on the local machine.
		connectionAddress = new TextField("localhost");
		btInstructions = new Button("How To Play");
		btInstructions.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				try{
					File file = new File("files//instructions.txt");
					java.awt.Desktop.getDesktop().edit(file);
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
		});
		hostJoinPane.getChildren().addAll(btHost, btJoin,connectionAddress, btInstructions);
		hostJoinScene = new Scene(hostJoinPane, 500, 300);
		
		//Instantiate all the elements for the gameScene.
		gamePane = new GridPane();
		textOutput = new TextArea();
		textInput = new TextField();
		//Add an action listener to textInput for sending messages.
		textInput.setOnKeyPressed(new MessengerHandler());
		
		//Here we create the dice objects for both players and add them to the approprate containers.
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
		
		gamePane.add(blackPlayerDiceBox, 0, 0, 2, 1);
		gamePane.add(blackPlayerPiecesStart, 2, 0, 1, 2);
		gamePane.add(blackPlayerPiecesEnd, 3, 0, 1, 2);
		gamePane.add(textOutput, 5, 0, 5, 5);
		gamePane.add(gameBoard, 0, 2, 4, 5);
		gamePane.add(whitePlayerDiceBox, 0, 7, 2, 1);
		gamePane.add(whitePlayerPiecesStart, 2, 7, 1, 2);
		gamePane.add(whitePlayerPiecesEnd, 3, 7, 1, 2);
		gamePane.add(textInput, 0, 8, 8, 1);
		
		gameScene = new Scene(gamePane, 900, 500);
		
		gameStage = new Stage();
		gameStage.setTitle("The Royal Game Of Ur");
		gameStage.setScene(hostJoinScene);
		gameStage.setResizable(false);
		gameStage.show();
		
		//The following set on close request ensures that all threads are terminated when the main application closes.
		gameStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent e){
				Platform.exit();
				System.exit(0);
			}
		});
	}
	//HostJoinActionHandler is an inner class which controls the connection of server/clients as well as the main game loop.
	private class HostJoinActionHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e){			
			gameStage.setScene(gameScene);
			
			new Thread(() ->{
				try{
					if(((Button)(e.getSource())).getText().equals("Host")){
						whitePlayerDiceBox.setDisable(true);
						for(int i = 0; i < 7; i++){
							whitePlayerPieces[i].setDisable(true);
						}
						playerCode = PlayerCode.BLACK;
						serverSocket = new ServerSocket(8000);
						textOutput.appendText("Server started at " + new Date() + "\n");
						socket = serverSocket.accept();
						textOutput.appendText("Client connected at " + new Date() + "\n");
					}
					else{
						playerCode = PlayerCode.WHITE;
						blackPlayerDiceBox.setDisable(true);
						for(int i = 0; i < 7; i++){
							blackPlayerPieces[i].setDisable(true);
						}
						socket = new Socket(connectionAddress.getText().trim(), 8000);
						textOutput.appendText("Connected to a server at " + new Date() + "\n");
					}
					dataOut = new ObjectOutputStream(socket.getOutputStream());
					dataIn = new ObjectInputStream(socket.getInputStream());
	
					//The main game loop.
					while (true){
						GameMove move = (GameMove)(dataIn.readObject());
						if(move.getType() == 0){
							String message = (String)(move.getData());
							textOutput.appendText(move.getPlayerCode().getName() + " player : " + message + "\n");
							try{
								GameMove dataReceived = new GameMove(3, "Data Received", playerCode);
								dataOut.writeObject(dataReceived);
								dataOut.flush();
							}
							catch(IOException ex){
								ex.printStackTrace();
							}
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
							try{
								GameMove dataReceived = new GameMove(3, "Data Received", playerCode);
								dataOut.writeObject(dataReceived);
								dataOut.flush();
							}
							catch(IOException ex){
								ex.printStackTrace();
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
								else{
									if(data.getFinalPathPosition() != 14 && data.getFinalPathPosition() != -1){
										int index = 0;
										if(playerCode == PlayerCode.BLACK){
											for(int i = 0; i < 7; i++){
												if(whitePlayerPieces[i].getPathPosition() == data.getInitialPathPosition()){
													index = i;
													break;
												}
											}
											whitePlayerPieces[index].setPathPosition(data.getFinalPathPosition());
											gameBoard.updatePiece(whitePlayerPieces[index]);
										}
										else{
											for(int i = 0; i < 7; i++){
												if(blackPlayerPieces[i].getPathPosition() == data.getInitialPathPosition()){
													index = i;
													break;
												}
											}
											blackPlayerPieces[index].setPathPosition(data.getFinalPathPosition());
											gameBoard.updatePiece(blackPlayerPieces[index]);
										}
									}
									else if(data.getFinalPathPosition() == 14){
										int index = -1;
										if(playerCode == PlayerCode.BLACK){
											for(int i = 0; i < 7; i++){
												if(whitePlayerPieces[i].getPathPosition() == data.getInitialPathPosition()){
													index = i;
													break;
												}
											}
											whitePlayerPieces[index].setPathPosition(data.getFinalPathPosition());
											gameBoard.remove(whitePlayerPieces[index]);
											whitePlayerPiecesEnd.addPiece(whitePlayerPieces[index]);
										}
										else{
											for(int i = 0; i < 7; i++){
												if(blackPlayerPieces[i].getPathPosition() == data.getInitialPathPosition()){
													index = i;
													break;
												}
											}
											blackPlayerPieces[index].setPathPosition(data.getFinalPathPosition());
											gameBoard.remove(blackPlayerPieces[index]);
											blackPlayerPiecesEnd.addPiece(blackPlayerPieces[index]);
										}
									}
									else{
										int index = -1;
										if(playerCode == PlayerCode.BLACK){
												for(int i = 0; i < 7; i++){
													if(blackPlayerPieces[i].getPathPosition() == data.getInitialPathPosition()){
														index = i;
														break;
													}
												}
												blackPlayerPieces[index].setPathPosition(-1);
												gameBoard.remove(blackPlayerPieces[index]);
												blackPlayerPiecesStart.addPiece(blackPlayerPieces[index]);
										}
										else{
											for(int i = 0; i < 7; i++){
													if(whitePlayerPieces[i].getPathPosition() == data.getInitialPathPosition()){
														index = i;
														break;
													}
												}
												whitePlayerPieces[index].setPathPosition(-1);
												gameBoard.remove(whitePlayerPieces[index]);
												whitePlayerPiecesStart.addPiece(whitePlayerPieces[index]);
										}
									}
								}
								try{
								GameMove dataReceived = new GameMove(3, "Data Received", playerCode);
								dataOut.writeObject(dataReceived);
								dataOut.flush();
								}
								catch(IOException ex){
									ex.printStackTrace();
								}
							});
						}
						else if(move.getType() == 3){
							//Data was received.
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
	//MessageHandler is an inner class which handles the sending of messages between clients and servers.
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
	//HoverOverPieceEnteredHandler is an inner class which handles highlight the legal moves for players when they hover over a GamePiece.
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
	//HoverOverPieceExitedHandler is an inner class which handles removing the highlighting when a GamePiece is no longer being hovered over.
	private class HoverOverPieceExitedHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent e){
			gameBoard.unHighlight();
			((GamePiece)(e.getSource())).setMoveable(false);
		}
	}
	
	//DieRollHandler is an inner class which handles rolling dice for players when they click in the diceBox.
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
	
	//GamePieceClickedHandler is an inner class which handles moving game pieces as they are clicked on by players.
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
											try{
												whitePlayerPieces[i].setPathPosition(-1);
												gameBoard.remove(whitePlayerPieces[i]);
												whitePlayerPiecesStart.addPiece(whitePlayerPieces[i]);
												MovedPieceData data = new MovedPieceData(whitePlayerPieces[i].getGamePieceCode(), pathIndex, -1);
												GameMove move = new GameMove(2, data, playerCode);
												dataOut.writeObject(move);
												dataOut.flush();
											}
											catch(IOException ex){
												ex.printStackTrace();
											}
										}
									}
									else{
										if(blackPlayerPieces[i].getPathPosition() == pathIndex){
											try{
												blackPlayerPieces[i].setPathPosition(-1);
												gameBoard.remove(blackPlayerPieces[i]);
												blackPlayerPiecesStart.addPiece(blackPlayerPieces[i]);
												MovedPieceData data = new MovedPieceData(blackPlayerPieces[i].getGamePieceCode(), pathIndex, -1);
												GameMove move = new GameMove(2, data, playerCode);
												dataOut.writeObject(move);
												dataOut.flush();
											}
											catch(IOException ex){
												ex.printStackTrace();
											}
										}
									}
								}
							}
							try{
								((GamePiece)(e.getSource())).setPathPosition(pathIndex);
								gameBoard.updatePiece(((GamePiece)(e.getSource())));
								MovedPieceData data = new MovedPieceData(((GamePiece)(e.getSource())).getGamePieceCode(), pathPosition, pathIndex);
								GameMove move = new GameMove(2, data, playerCode);
								dataOut.writeObject(move);
								dataOut.flush();
							}
							catch(IOException ex){
								ex.printStackTrace();
							}
							((GamePiece)(e.getSource())).setPathPosition(pathIndex);
							gameBoard.updatePiece(((GamePiece)(e.getSource())));
						}
						else if(pathIndex == 14){
							try{
								((GamePiece)(e.getSource())).setPathPosition(pathIndex);
								gameBoard.remove(((GamePiece)(e.getSource())));
								if(playerCode == PlayerCode.BLACK){
									blackPlayerPiecesEnd.addPiece(((GamePiece)(e.getSource())));
								}
								else{
									whitePlayerPiecesEnd.addPiece(((GamePiece)(e.getSource())));
								}
								MovedPieceData data = new MovedPieceData(((GamePiece)(e.getSource())).getGamePieceCode(), pathPosition, pathIndex);
								GameMove move = new GameMove(2, data, playerCode);
								dataOut.writeObject(move);
								dataOut.flush();
							}
							catch(IOException ex){
								ex.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}