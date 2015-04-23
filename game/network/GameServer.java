package game.network;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServer extends Listener{
	public static final int TCP_PORT=54555;
	private Server server;
	private Connection currentConnection;
	private NetworkNotifier notifier;
	public void startGameServer(){
		notifier = new NetworkNotifier();
		server =new Server();
		Kryo kryo=server.getKryo();
		kryo.register(String[].class);
		server.start();
		try {
			server.bind(TCP_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.addListener(this);

	}
	public void sendMessage(Object o){
		
		currentConnection.sendTCP(o);
		
	}
	public NetworkNotifier getNotifier(){
		return notifier;

	}
	@Override
	public void connected(Connection connection) {
		super.connected(connection);
		currentConnection=connection;
		notifier.notify(connection);
		//System.out.println("Connected!!!!!!!!!!!");
	}
	public static void main(String[] args) {
		GameServer gs=new GameServer();
		gs.startGameServer();
	}

}