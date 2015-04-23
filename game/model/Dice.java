package game.model;
import java.util.Random;


public class Dice {
	private int value;
	
	public int roll(){
		Random rand = new Random();
		value = rand.nextInt((6 - 1) + 1) + 1;
		return value;
	}
	
	public int getValue(){
		return value;
	}
}
