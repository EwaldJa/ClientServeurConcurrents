package clientServeur;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

import tools.Communication;
import tools.LoggerServer;

public class Serveur implements Runnable {
	
	protected DatagramSocket ds_srv;
	protected String msg;
	protected LoggerServer mylogger;

	public Serveur(int srv_port) throws SocketException {
		this(srv_port, "Je suis un serveur intelligent qui repond");
	}
	
	public Serveur(int srv_port, String server_msg) throws SocketException {
		ds_srv = new DatagramSocket(srv_port);
		msg = server_msg;
		mylogger = new LoggerServer("defaultconfigServer.txt", srv_port);
	}

	protected DatagramPacket recevoir() {
		byte[] buffer = new byte[8192];
		DatagramPacket dp = new DatagramPacket(buffer, 8192);
		try {
			ds_srv.receive(dp);
		} catch (IOException e) {
			mylogger.log(LoggerServer.OFF, "Erreur lors de la reception d'un message client");
			mylogger.log(LoggerServer.IMPORTANT, e.getMessage());
			//System.err.println("SERVER :: Erreur lors de la reception d'un message client : ");
			//e.printStackTrace();
		}
		return dp;
	}
	
	protected void startComm(DatagramPacket dp) {
		try {
			new Thread(new Communication(dp.getAddress(), dp.getPort(), msg)).start();
		} catch (SocketException e) {
			mylogger.log(LoggerServer.OFF, "Erreur lors de l'initialisation d'une communication dediee client");
			mylogger.log(LoggerServer.IMPORTANT, e.getMessage());
			//System.err.println("SERVER :: Erreur lors de l'initialisation d'une communication dediee client : ");
			//e.printStackTrace();
		}
	}
	
	private boolean init_comm(DatagramPacket dp) {
		String msg = "";
		try {
			msg = new String(dp.getData(), "ascii");
			mylogger.log(LoggerServer.DEBUG, "Message recu : " + msg);
		} catch (UnsupportedEncodingException e) {
			mylogger.log(LoggerServer.OFF, "Erreur lors du decodage d'un message client");
			mylogger.log(LoggerServer.IMPORTANT, e.getMessage());
			//System.err.println("SERVER :: Erreur lors du decodage d'un message client : ");
			//e.printStackTrace();
		}
		if (msg.contains("CO")) {
			startComm(dp);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public void run() {
		while (true) {
			if(!init_comm(recevoir())) {
				System.out.println("Le client ne demande pas la connexion.");
			}
		}
	}
	
	public void finalize() throws Throwable {
		mylogger.dispose();
		super.finalize();
	}
	
	
}
