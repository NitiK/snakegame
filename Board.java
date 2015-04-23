import java.util.ArrayList;


public class Board {
	private ArrayList<Square> s = new ArrayList<Square>();
	private int size;
	
	public Board(int size){
		this.size = size;
		for( int i = 0; i < size; i++ ){
			s.add(new Square(i));
		}
	}
	
	public int getSize(){
		return size;
	}
}
