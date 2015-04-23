
public class Player {
	
	private String name;
	private Dice d;
	private int position;
	private Board b;
	public Player(String name, Dice d, Board b){
		this.position = 1;
		this.name = name;
		this.d = d;
		this.b = b;
	}
	
	public int getPosition(){
		return position;
	}
	
	public int roll(){
		int c = d.roll();
		int remain = b.getSize() - position;
		if( c <= remain ){
			position += c;
		}
		else{
			position = b.getSize() - (c-remain);
		}
		return c;
	}
	
	public String getName(){
		return this.name;
	}
}
