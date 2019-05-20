package peerToPeer;

import java.io.IOException;
import java.net.*;
import java.util.*;

import tools.LoggerServer;

public class P2PServeur extends srvComm.Serveur {
	
	public static final String DEFAULT_P2PSERVER_MSG = "Je suis un serveur P2P qui r�pond.";
	
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
			mylogger.log(LoggerServer.OFF, "SERVER :: Erreur lors de la r�ception d'un message client");
			mylogger.log(LoggerServer.IMPORTANT, e.getMessage());
			//System.err.println("SERVER :: Erreur lors de la r�ception d'un message client : ");
			//e.printStackTrace();
		}
		return dp;
	}
	
	@Override
	public void startComm(DatagramPacket dp) {
		String clients_info = "Client pr�c�demment connect�s au serveur : " + client_history.toString();
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