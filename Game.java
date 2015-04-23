import java.util.Scanner;


public class Game {
	private Dice d;
	private Player p1,p2;
	private Board b;
	Scanner s = new Scanner(System.in);
	
	public Game(){
		d = new Dice();
		b = new Board(10);
		p1 = new Player("p1",d,b);
		p2 = new Player("p2",d,b);
	}
	
	public void start(){
		boolean gameOver = false;
		Player p = p2;
		while(!gameOver){
			if(p.equals(p1)){
				p = p2;
			}
			else{
				p = p1;
			}
			System.out.println( p.getName() + "'s turn" );
			System.out.println( p.getName() + " at square " + p.getPosition());
			System.out.print( "command : " );
			String command = s.nextLine();
			if(command.equals("roll")){
				int roll = p.roll();
				System.out.println(p.getName() + " get " + roll);
				System.out.println(p.getName() + " at square " + p.getPosition());
				if(p.getPosition() == b.getSize()){
					gameOver = true;
					System.out.println(p.getName() + " win! ");
				}
			}
			else{
				System.out.println("skip");
				System.out.println(p.getName() + " at square " + p.getPosition());
			}
			System.out.println("================================");
		}
	}
	public static void main(String[] args) {
		Game g = new Game();
		g.start();
	}
}
