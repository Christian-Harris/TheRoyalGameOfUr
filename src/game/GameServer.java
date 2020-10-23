package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;


public class GameServer implements Runnable{
	private DataOutputStream toClient1 = null;
	private DataInputStream fromClient1 = null;
	private DataOutputStream toClient2 = null;
	private DataInputStream fromClient2 = null;
	
	private ServerSocket serverSocket;
	private Socket socket1;
	private Socket socket2;
	
	private String password;
	
	private static int gamesRunning;
	
	public static void main(String[] args){
		while(true){
			try{
				ServerSocket mainServerSocket = new ServerSocket(8000);
				Socket mainSocket = mainServerSocket.accept();
				Thread mainThread = new Thread(new GameServer(mainServerSocket, mainSocket));
				mainThread.start();
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
	
	public void run(){
		try{
			toClient1 = new DataOutputStream(socket1.getOutputStream());
			fromClient1 = new DataInputStream(socket1.getInputStream());
			this.password = fromClient1.readUTF();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
		/*while(true){
			try{
				socket2 = this.serverSocket.accept();
				toClient2 = new DataOutputStream(socket2.getOutputStream());
				fromClient2 = new DataInputStream(socket2.getInputStream());
				String client2Password = fromClient2.readUTF();
				if(client2Password.equals(this.password)){
					this.gamesRunning++;
					break;
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}*/
		
		/*while(true){
			//This is where the game logic will have to begin.
		}*/
		try{
			System.out.println("Printing Message to client 1.");
			toClient1.writeUTF("Server thread preparing to terminate password " + this.password + ".");
			toClient1.flush();
			//System.out.println("Printing Message to client 2.");
			//toClient2.writeUTF("Server thread preparing to terminate.");
			System.out.println("Server thread terminating.");
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public GameServer(ServerSocket serverSocket, Socket socket){
		this.serverSocket = serverSocket;
		this.socket1 = socket;
	}
	
	public int getGamesRunning(){
		return this.gamesRunning;
	}
}