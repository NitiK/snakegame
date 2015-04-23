package game.model;

import game.network.GameClient;
import game.network.GameServer;
import game.network.NetworkNotifier;
import game.ui.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;


/**
 * Game model which acts as a controller. I knows about everything inside the
 * game such as Board, Die, Player1, Player2 and delegates most of the work to
 * those objects.
 * 
 * @author Keeratipong <kee@fameworks.co>
 * 
 */
public class Game implements Observer{

	// TODO: Declare all you models here
	private Dice d;
	private Player p1,p2;
	private Board b;
	private Player currentPlayer;

	//Network related stuff

	private GameServer gameServer;
	private GameClient gameClient;

	public Game() {
		// TODO : Initialize all objects here
		d = new Dice();
		b = new Board(64);
		p1 = new Player("p1",d,b);
		p2 = new Player("p2",d,b);
		currentPlayer = p1;
	}
	public NetworkNotifier getServerNotifier(){

		return gameServer.getNotifier();
	}
	public NetworkNotifier getClientNotifier(){

		return gameClient.getNotifier();
	}
	public void startServer(){
		gameServer =new GameServer();
		gameServer.startGameServer();
		getServerNotifier().addObserver(this);


	}
	public void startClient(){
		gameClient =new GameClient();
		gameClient.startGameClient();
		getClientNotifier().addObserver(this);
	}

	/**
	 * Make the current player take his/her turn Taking turn can be roll a dice
	 * + move to that square
	 */
	public void currentPlayerTakeTurn() {
		// TODO: Implement this
		if(!currentPlayer.isFreeze()){
			currentPlayer.roll();
		}
		else{
			currentPlayer.melt();
			nextTurn();
			currentPlayer.roll();
		}
	}

	/**
	 * Change the state of the game to the next turn
	 */
	public void nextTurn() {
		// TODO: Implement this
		if(currentPlayer.equals(p1)){
			currentPlayer = p2;
		}
		else{
			currentPlayer = p1;
		}
	}

	/**
	 * Return total number of squares in the board
	 * 
	 * @return the number of squares
	 */
	public int getBoardSize() {
		// TODO: Replace this dummy return value with actual implementation
		return b.getSize();
	}

	/**
	 * Return the name of the square
	 * 
	 * @param i
	 *            The index of the square in board's collection
	 * @return The name of the square
	 */
	public String getSquareNameAtIndex(int i) {
		// TODO: Replace this dummy return value with actual implementation
		return b.getSquare(i).getName();
	}

	public Color getSquareColorCodeAtIndex(int i) {
		Color c = new Color(0);
		if(b.getSquare(i) instanceof SquareHome){
			return c.green;
		}
		else if(b.getSquare(i) instanceof SquareFreeze){
			return c.blue;
		}
		else{
			return c.BLACK;
		}
	}

	/**
	 * Return the index of the square that player1 is on
	 * 
	 * @return The index of the square
	 */
	public int getPlayer1SquareIndex() {
		// TODO: Replace this dummy return value with actual implementation
		return p1.getPosition();
	}

	/**
	 * Return the name of the square that the player1 is on
	 * 
	 * @return The index of the square
	 */
	public String getPlayer1SquareName() {
		// TODO: Replace this dummy return value with actual implementation
		return b.getSquare(getPlayer1SquareIndex()).getName();
	}

	/**
	 * Return the index of the square that player2 is on
	 * 
	 * @return The index of the square
	 */
	public int getPlayer2SquareIndex() {
		// TODO: Replace this dummy return value with actual implementation
		return p2.getPosition();
	}

	/**
	 * Return the name of the square that the player1 is on
	 * 
	 * @return The index of the square
	 */
	public String getPlayer2SquareName() {
		// TODO: Replace this dummy return value with actual implementation
		return b.getSquare(getPlayer2SquareIndex()).getName();
	}

	/**
	 * Return the face value of a die
	 * 
	 * @return Die value
	 */
	public int getDieValue() {
		// TODO: Replace this dummy return value with actual implementation
		return d.getValue();
	}

	/**
	 * Return the name of the current player who is in this turn
	 * 
	 * @return The name of the current player
	 */
	public String getCurrentPlayerName() {
		// TODO: Replace this dummy return value with actual implementation
		return currentPlayer.getName();
	}

	/**
	 * Return the name of the square that the current player is on
	 * 
	 * @return The name of the square that the player is on
	 */
	public String getCurrentPlayerSquareName() {
		// TODO: Replace this dummy return value with actual implementation
		return b.getSquare(currentPlayer.getPosition()).getName();
	}
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Connection){
			gameServer.sendMessage("Hello");
			String [] boardString =new String[b.getSize()];
			for(int i=0;i<b.getSize();i++){
				boardString[i] = b.getSquare(i).getClass().toString();
				
			}
			gameServer.sendMessage(boardString);
		}
		if(arg.getClass().equals(String[].class)){
			String [] boardString = (String[]) arg;
			ArrayList<Square> list=new ArrayList<Square>();
			for(int i=0;i<boardString.length;i++){
				if(boardString[i].equals(SquareNormal.class.toString())){
					list.add(new SquareNormal("s"+(i+1)));
					
				}else if(boardString[i].equals(SquareHome.class.toString())){
					list.add(new SquareHome("HM"));
					
				}else if(boardString[i].equals(SquareFreeze.class.toString())){
					list.add(new SquareFreeze("FZ"));
					
				}
				
				
			}
			b.setSquare(list);
			
			
			
		}
		
	}

}
