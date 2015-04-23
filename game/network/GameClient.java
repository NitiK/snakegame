package game.network;

import java.io.IOException;
import java.util.Scanner;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameClient extends Listener{
	public static final int TIMEOUT=5000;
	private Client client;
	private NetworkNotifier notifier;
	public void startGameClient(){
		notifier =new NetworkNotifier();
		client =new Client();
		Kryo kryo=client.getKryo();
		kryo.register(String[].class);
		client.start();
		try {
			client.connect(TIMEOUT, "127.0.0.1", GameServer.TCP_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.addListener(this);
	}
	public NetworkNotifier getNotifier(){
		return notifier;

	}
	
	public void received(Connection connection, Object object) {
		
		super.received(connection, object);
		notifier.notify(object);
	}
	public static void main(String[] args) {
		GameClient gc =new GameClient();
		gc.startGameClient();
		new Scanner(System.in).nextLine();
	}
}
