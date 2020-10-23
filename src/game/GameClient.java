package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class GameClient extends Application{
	private DataOutputStream toServer = null;
	private DataInputStream fromServer = null;
	
	private Scene hostJoinScene;
	private Scene hostScene;
	private Scene gameScene;
	
	//private TextArea taServer = new TextArea();
	
	public void start(Stage primaryStage){
		Pane hostJoin = new VBox();
		Button btHost = new Button("Host");
		Button btJoin = new Button("Join");
		hostJoin.getChildren().addAll(btHost, btJoin);
		hostJoinScene = new Scene(hostJoin, 200, 200);
		
		Pane host = new VBox();
		Label lblAddress = new Label("Address");
		TextField tfAddress = new TextField();
		Label lblPassword = new Label("Password");
		TextField tfPassword = new TextField();
		Button btHostConnect = new Button("Connect");
		Button btHostBack = new Button("Back");
		host.getChildren().addAll(lblAddress, tfAddress, lblPassword, tfPassword, btHostConnect, btHostBack);
		hostScene = new Scene(host, 200, 200);
		
		Pane game = new Pane();
		TextArea temporaryOutput = new TextArea();
		game.getChildren().add(temporaryOutput);
		gameScene = new Scene(game, 200, 200);
		
		
		primaryStage.setTitle("The Royal Game Of Ur");
		primaryStage.setScene(hostJoinScene);
		primaryStage.show();
		
		btHost.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				primaryStage.setScene(hostScene);
			}
		});
		
		btHostBack.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				primaryStage.setScene(hostJoinScene);
			}
		});
		
		btHostConnect.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				try{
					Socket socket = new Socket(tfAddress.getText(), 8000);
					toServer = new DataOutputStream(socket.getOutputStream());
					fromServer = new DataInputStream(socket.getInputStream());
					toServer.writeUTF(tfPassword.getText());
					String message = fromServer.readUTF();
					primaryStage.setScene(gameScene);
					temporaryOutput.appendText(message);
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
		});
	}
}