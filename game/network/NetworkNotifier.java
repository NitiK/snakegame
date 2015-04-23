package game.network;

import java.util.Observable;

public class NetworkNotifier extends Observable{
	public void notify(Object somethingToNotify){
		setChanged();
		notifyObservers(somethingToNotify);
		
		
	}

}
