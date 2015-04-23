package game.ui;

import game.model.Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.esotericsoftware.kryonet.Connection;

/**
 * A stupid & ugly GUI. It depends on only a game object. You can modify it to
 * fit your need, although the no modification is needed to make it work.
 * 
 * @author Keeratipong <kee@fameworks.co>
 * 
 */
public class GUI extends JFrame implements Observer{

	// Constants
	public static final int BOARD_SIZE = 640;
	public static final Color BOARD_COLOR = new Color(253, 227, 167);
	public static final Color LINE_COLOR = Color.WHITE;
	public static final int PLAYER_SIZE = 20;
	public static final Color PLAYER_1_COLOR = Color.RED;
	public static final Color PLAYER_2_COLOR = Color.BLUE;
	public static final int PLAYER_1_OFFSET_X = 10;
	public static final int PLAYER_1_OFFSET_Y = 15;
	public static final int PLAYER_2_OFFSET_X = 10;
	public static final int PLAYER_2_OFFSET_Y = 45;

	// GUI componenents
	private JButton rollButton;
	private BoardUI boardUI;

	// Controller
	private Game game;

	public GUI() {
		super("Snake and Ladder Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		//setAlwaysOnTop(true);
		initGame();
		initComponents();
		initGameMenu();
	}
	private void initGameMenu(){
		Object[] gameOptions={"Single Mode","Create Game","Join Game"};
		Object choice =JOptionPane.showInputDialog(this,"Select game mode","Snake and ladder game",JOptionPane.QUESTION_MESSAGE,null,gameOptions,gameOptions[0]);
		if(choice==null){
			System.exit(0);
			
		}else if(choice==gameOptions[0]){
			//Single mode
			
			
		}else if(choice==gameOptions[1]){
			//Create game
			rollButton.setText("Waitting for someone");
			rollButton.setEnabled(false);
				
			game.startServer();
			game.getServerNotifier().addObserver(this);
			
			
		}else if(choice==gameOptions[2]){
			//Join game
			rollButton.setText("Joining someone");
			rollButton.setEnabled(false);
			game.startClient();
			game.getClientNotifier().addObserver(this);
			
		}
	}

	private void initGame() {
		game = new Game();
		
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		// Create components
		rollButton = new JButton("Roll a dice!");
		rollButton.addActionListener(new ActionListener() {

			// Current player take a turn when clicked
			public void actionPerformed(ActionEvent e) {
				game.currentPlayerTakeTurn();
				JOptionPane.showMessageDialog(GUI.this,
						"Player: " + game.getCurrentPlayerName() + "\n"
								+ "Dice: " + game.getDieValue() + "\n"
								+ "Go to: " + game.getCurrentPlayerSquareName());
				boardUI.repaint();
				game.nextTurn();
			}
		});
		boardUI = new BoardUI();
		boardUI.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
		// Add components to the window
		add(rollButton, BorderLayout.NORTH);
		add(boardUI, BorderLayout.CENTER);
		pack();
	}

	// This BoardUI is just for rendering stuff
	// Please feel free to fix it if you want to change looks and feels,
	// otherwise just leave it alone.
	class BoardUI extends JPanel {

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			// Draw board background
			g.setColor(BOARD_COLOR);
			g.fillRect(0, 0, BOARD_SIZE, BOARD_SIZE);

			// Draw lines
			g.setColor(LINE_COLOR);
			int squareXCount = (int) Math.sqrt(game.getBoardSize());
			int squareWidth = BOARD_SIZE / squareXCount;
			for (int i = 0; i < squareXCount; i++) {
				g.drawLine(i * squareWidth, 0, i * squareWidth, BOARD_SIZE);
			}
			int squareYCount = (int) Math.sqrt(game.getBoardSize());
			int squareHeight = BOARD_SIZE / squareYCount;
			for (int i = 0; i < squareYCount; i++) {
				g.drawLine(0, i * squareHeight, BOARD_SIZE, i * squareHeight);
			}

			// Draw square numbers
			for (int i = 0; i < game.getBoardSize(); i++) {
				int col = i % squareYCount;
				int row = i / squareXCount;
				g.setColor(game.getSquareColorCodeAtIndex(i));
				g.drawString(game.getSquareNameAtIndex(i), col * squareWidth
						+ squareWidth / 2, row * squareHeight + squareHeight
						/ 2);
			}

			// Draw players
			int player1SquareIndex = game.getPlayer1SquareIndex();
			int player1Col = player1SquareIndex % squareYCount;
			int player1Row = player1SquareIndex / squareXCount;
			g.setColor(PLAYER_1_COLOR);
			g.fillOval(player1Col * squareWidth + PLAYER_1_OFFSET_X, player1Row
					* squareHeight + PLAYER_1_OFFSET_Y, PLAYER_SIZE,
					PLAYER_SIZE);
			int player2SquareIndex = game.getPlayer2SquareIndex();
			int player2Col = player2SquareIndex % squareYCount;
			int player2Row = player2SquareIndex / squareXCount;
			g.setColor(PLAYER_2_COLOR);
			g.fillOval(player2Col * squareWidth + PLAYER_2_OFFSET_X, player2Row
					* squareHeight + PLAYER_2_OFFSET_Y, PLAYER_SIZE,
					PLAYER_SIZE);

		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Connection){
			rollButton.setText("Roll the dice");
			rollButton.setEnabled(true);
			
		}
		if(arg1 instanceof String){
			if(arg1.equals("Hello")){
				rollButton.setText("Roll the dice");
				rollButton.setEnabled(true);
				
			}
			
		}
		
	}
}
