# TheRoyalGameOfUr
A networked application allowing two paries to play The Royal Game of Ur.

## Synopsis
This project allowed me to dive into a couple of areas I have not explored very deeply before. One was using extensive use of the network to pass information between two application. The other was implementing game logic. While I learned a lot an expert eye will be able to tell from my code that I still have a long ways to go.

## How to Run
The class files located in the bin directory are required. Place them on a drive and navigate to them in a command window. Once there run the command java game.Game. A window will open offering you two options of Host or Join with a TextField to enter in an IP-address as well as a button for How to Play which will open a text file on your machine with some brief instructions. In order to get a client/server system set up the first machine will need to hit Host. The server will start and will listen for a connection on a thread. Next another machine will have to enter the address of the host and hit join. A connection should then be established. If two instances of the appliation are on the same machine then the TextField defaults to connecting to the localhost so no address is needed.

## Code Example
While I know now that the following is far from an efficient method of network interaction I am rather proud of the solution I came up with. I needed to find a way to transfer different kinds of data between the applications and have a way to decode the information on the other side. I decided to package the data in an object called GameMove with encoded an integer type and an Object data. When this object was sent through a DataStream the other application was able to read the type and then cast the Object data to the appropriate type and then access its methods. Here is the class.
```
public class GameMove implements Serializable{
	private int type;
	private Object data;
	private PlayerCode playerCode;

	public GameMove(int type, Object data, PlayerCode playerCode){
		this.type = type;
		this.data = data;
		this.playerCode = playerCode;
	}

	public int getType(){
		return this.type;
	}

	public Object getData(){
		return this.data;
	}

	public PlayerCode getPlayerCode(){
		return this.playerCode;
	}
}
```

