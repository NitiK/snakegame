package game.model;
import java.util.ArrayList;
import java.util.Random;


public class Board {
	private ArrayList<Square> s = new ArrayList<Square>();
	private int size;
	private final int HOME_COUNT = 3;
	private final int FREEZE_COUNT = 3;
	
	public Board(int size){
		this.size = size;
		for( int i = 0; i < size; i++ ){
			s.add(new SquareNormal("s"+(i+1)));
		}
		
		Random random = new Random();
		for(int i = 0; i < HOME_COUNT; i++){
			int index = random.nextInt(size - 1) + 1;
			s.set(index, new SquareHome("HM"));
		}
		for(int i = 0; i < FREEZE_COUNT; i++){
			int index = random.nextInt(size - 1) + 1;
			s.set(index, new SquareFreeze("FZ"));
		}
	}
	
	public int getSize(){
		return size;
	}
	
	public Square getSquare(int i){
		return s.get(i);
	}
	public void setSquare(ArrayList<Square> s) {
		this.s=s;

	}
	
}
