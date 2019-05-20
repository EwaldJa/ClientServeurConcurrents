package peerToPeer;

import java.io.IOException;
import java.net.*;

import srvComm.Client;
import srvComm.Serveur;
import tools.KeyboardReader;
import tools.LoggerClient;

public class P2PClient extends Client implements Runnable {
	
	private String client_name;
	private static int port_server_libre = 6000;
	private LoggerClient mylogger;
	
	
	public P2PClient(String name) throws SocketException {
		super(null, 0, 0, "");
		mylogger = new LoggerClient("defaultconfigClient.txt", name);
		client_name = name;
		try {
			new Thread(new Serveur(port_server_libre, "Je suis le serveur du client P2P " + client_name)).start();
			port_server_libre++;
		} catch (SocketException e) {
			mylogger.log(LoggerClient.OFF, "Erreur à l'initisation du serveur du client P2P ");
			mylogger.log(LoggerClient.IMPORTANT, e.getMessage());
			//System.err.println("Erreur à l'initisation du serveur du client P2P " + client_name + " : ");
			//e.printStackTrace();
		}
		System.out.println("Bonjour " + client_name + ", votre port local est " + ds_com.getLocalPort());
	}
	
	
	private void sendMessage() {
		InetAddress adr = getAddress();
		int port = getPort();
		String message = getMessage();
		DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), adr, port);
		mylogger.log(LoggerClient.OFF, "Envoi du message '" + message + "' au serveur adresse : " + adr.toString() + " et port : " + port);
		try {
			ds_com.send(dp);
		} catch (IOException e) {
			mylogger.log(LoggerClient.OFF, "Erreur lors de l'envoi d'un message à l'hôte depuis le client : ");
			mylogger.log(LoggerClient.IMPORTANT, e.getMessage());
			//System.err.println("Erreur lors de l'envoi d'un message à l'hôte depuis le client : " + client_name);
			//e.printStackTrace();
		}
	}
	
	private InetAddress getAddress() {
		System.out.println("Veuillez taper l'adresse de l'hôte auquel vous voulez vous connecter : ");
		try {
			return InetAddress.getByName(KeyboardReader.getString());
		} catch (UnknownHostException e) {
			mylogger.log(LoggerClient.IMPORTANT, "Impossible de récupérer l'adresse saisie par le client : ");
			mylogger.log(LoggerClient.INFO, e.getMessage());
			System.err.println("Impossible de récupérer l'adresse, veuillez vérifier votre saisie : ");
			return getAddress();
		}
	}
	
	private int getPort() {
		System.out.println("Veuillez taper le port de l'hôte auquel vous voulez vous connecter : ");
		int port = KeyboardReader.getInt();
		if (port == -1) {
			mylogger.log(LoggerClient.IMPORTANT, "Impossible de récupérer le port saisi par le client");
			System.err.println("Impossible de récupérer le port, veuillez vérifier votre saisie : ");
			return getPort();
		}
		return port;
	}
	
	private String getMessage() {
		System.out.println("Veuillez taper le message que vous voulez envoyer : ");
		String message = KeyboardReader.getString();
		if (message == "") {
			mylogger.log(LoggerClient.IMPORTANT, "Impossible de récupérer le message saisi par le client");
			System.err.println("Impossible de récupérer le message, veuillez vérifier votre saisie : ");
			return getMessage();
		}
		return message;
	}

	@Override
	public void run() {
		String init = "CO";
		dp_srv = new DatagramPacket(init.getBytes(), init.length(), getAddress(), getPort());
		try {
			ds_com.send(dp_srv);
		} catch (IOException e) {
			mylogger.log(LoggerClient.IMPORTANT, "Erreur lors de l'initialisation de la communication pour le client ");
			mylogger.log(LoggerClient.INFO, e.getMessage());
			//System.err.println("Erreur lors de l'initialisation de la communication pour le client " + client_name + " : ");
			//e.printStackTrace();
		}
		super.recevoir();
		int port_server = super.recevoir();
		System.out.println("Le port du server pour communiquer est : " + port_server);
		mylogger.log(LoggerClient.DEBUG, "Le port du server pour communiquer est : " + port_server);
		boolean quitter = false;
		int choix;
		while (!quitter) {
			System.out.println("Tapez 1 pour envoyer un message, 0 pour fermer le client");
			choix = KeyboardReader.getInt();
			switch (choix) {
				case 0:
					quitter = true;
					ds_com.close();
					break;
				case 1:
					sendMessage();
					int port = super.recevoir();
					System.out.println("Le serveur répond depuis le port :" + port);
					break;
				default:
					System.out.println("Choix erroné");
					mylogger.log(LoggerClient.ALL, "Choix erroné dans le menu : " + choix);
					break;
			}
		}
		System.out.println("Vous avez bien quitté.");
		mylogger.dispose();
	}
	
	public void finalize() throws Throwable{
		mylogger.dispose();
		super.finalize();
	}

}
