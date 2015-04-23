package game.model;

public class Player {
	
	private String name;
	private Dice d;
	private int position;
	private Board b;
	private boolean isFreeze = false;
	public Player(String name, Dice d, Board b){
		this.position = 0;
		this.name = name;
		this.d = d;
		this.b = b;
	}
	
	public int getPosition(){
		return position;
	}
	
	public int roll(){
		int c = d.roll();
		int remain = b.getSize() - position - 1;
		if( c <= remain ){
			position += c;
		}
		else{
			position = b.getSize() - (c-remain);
		}
		if(b.getSquare(position) instanceof SquareHome){
			position = 0;
			return 0;
		}
		if(b.getSquare(position) instanceof SquareFreeze){
			isFreeze = true;
		}
		
		return c;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean isFreeze(){
		return isFreeze;
	}
	
	public void melt(){
		isFreeze = false;
	}
}
