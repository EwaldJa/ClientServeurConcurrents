package tools;

import java.io.*;
import java.net.*;
import java.util.*;

public class Communication implements Runnable {
	
	private DatagramSocket ds_com;
	private DatagramPacket dp_clt;
	private ArrayList<String> hist_msg_clt;
	private String message;
	private LoggerServer mylogger;
	
	public Communication(InetAddress clt_adr, int clt_port, String server_message) throws SocketException {
		ds_com = new DatagramSocket();
		byte[] buffer = new byte[8192];
		dp_clt = new DatagramPacket(buffer, 8192, clt_adr, clt_port);
		hist_msg_clt = new ArrayList<String>();
		message = server_message;
		mylogger = new LoggerServer("defaultconfigServer.txt", ds_com.getLocalPort());
	}
	
	private boolean recevoir() {
		byte[] buffer = new byte[8192];
		DatagramPacket rec_dp = new DatagramPacket(buffer, 8192);
		try {
			ds_com.receive(rec_dp);
		} catch (IOException e) {
			mylogger.log(LoggerServer.OFF, "Erreur lors de la reception d'un message client");
			mylogger.log(LoggerServer.IMPORTANT, e.getMessage());
			//System.err.println("Erreur lors de la reception d'un message client : ");
			//e.printStackTrace();
		}
		String rec_msg = "";
		try {
			rec_msg = new String (rec_dp.getData(), "ascii");
			hist_msg_clt.add(rec_msg);
			mylogger.log(LoggerServer.DEBUG, "Message recu :" + rec_msg);
		} catch (UnsupportedEncodingException e) {
			mylogger.log(LoggerServer.OFF, "Erreur lors du decodage d'un message client");
			mylogger.log(LoggerServer.IMPORTANT, e.getMessage());
			//System.err.println("Erreur lors du decodage d'un message client : ");
			//e.printStackTrace();
		}
		if (rec_msg == "DE") {
			return false;
		}
		else {
			System.out.println("Message client : " + rec_msg);
			return true;
		}
	}
	
	private void envoyer (String message, DatagramPacket dp_client) {
		mylogger.log(LoggerServer.DEBUG, "Envoi du message '" + message + "' au client @" + dp_client.getAddress().toString() + ":" + dp_client.getPort());
		dp_client.setData(message.getBytes());
		try {
			ds_com.send(dp_client);
		} catch (IOException e) {
			mylogger.log(LoggerServer.OFF, "Erreur lors de la reponse au client");
			mylogger.log(LoggerServer.IMPORTANT, e.getMessage());
			//System.err.println("Erreur lors de la reponse au client : ");
			//e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		String init = "Initialisation de la communication";
		dp_clt.setData(init.getBytes());
		try {
			ds_com.send(dp_clt);
		} catch (IOException e) {
			mylogger.log(LoggerServer.OFF, "Erreur lors de l'initialisation de la communication");
			mylogger.log(LoggerServer.IMPORTANT, e.getMessage());
			//System.err.println("Erreur lors de l'initialisation de la communication : ");
			//e.printStackTrace();
		}
		
		while (recevoir()) {
			envoyer(message, dp_clt);
		}
		System.out.println("Fermeture de la communication, messages du client : ");
		mylogger.log(LoggerServer.INFO, "Fermeture de la communication");
		System.out.println(hist_msg_clt);
		mylogger.log(LoggerServer.DEBUG, "Messages du client + " + hist_msg_clt.toString());
		ds_com.close();
		mylogger.dispose();
	}
	
	public void finalize() throws Throwable{
		mylogger.dispose();
		super.finalize();
	}
}
