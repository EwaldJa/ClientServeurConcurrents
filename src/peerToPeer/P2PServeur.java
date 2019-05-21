package peerToPeer;

import tools.LoggerServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.ArrayList;

public class P2PServeur extends clientServeur.Serveur {
	
	public static final String DEFAULT_P2PSERVER_MSG = "Je suis un serveur P2P qui repond.";
	
	private ArrayList<String> client_history;
	
	public P2PServeur(int srv_port) throws SocketException {
		this(srv_port, DEFAULT_P2PSERVER_MSG);
	}
	
	public P2PServeur(int srv_port, String srv_msg) throws SocketException {
		super(srv_port, srv_msg);
		client_history = new ArrayList<String>();
	}
	
	@Override
	public DatagramPacket recevoir() {
		byte[] buffer = new byte[8192];
		DatagramPacket dp = new DatagramPacket(buffer, 8192);
		try {
			ds_srv.receive(dp);
			String client_info = "Adresse : " + dp.getAddress().toString() + ", Port : " + dp.getPort();
			client_history.add(client_info);
		} catch (IOException e) {
			mylogger.log(LoggerServer.OFF, "SERVER :: Erreur lors de la reception d'un message client");
			mylogger.log(LoggerServer.IMPORTANT, e.getMessage());
			//System.err.println("SERVER :: Erreur lors de la réception d'un message client : ");
			//e.printStackTrace();
		}
		return dp;
	}
	
	@Override
	public void startComm(DatagramPacket dp) {
		String clients_info = "Client precedemment connectes au serveur : " + client_history.toString();
		dp.setData(clients_info.getBytes());
		try {
			ds_srv.send(dp);
		} catch (IOException e) {
			mylogger.log(LoggerServer.OFF, "SERVER :: Erreur lors de l'envoi de la liste des clients");
			mylogger.log(LoggerServer.IMPORTANT, e.getMessage());
			//System.err.println("SERVER :: Erreur lors de l'envoi de la liste des clients : ");
			//e.printStackTrace();
		}
		super.startComm(dp);
	}
	
	public void finalize() throws Throwable{
		super.finalize();
	}

	

}
