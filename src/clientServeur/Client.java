package clientServeur;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client implements Runnable {

	public static final int DEFAULT_MAX_MSG = 4;
	public static final String DEFAULT_CLIENT_MSG = "Bonjour, je suis un client.";
	protected DatagramSocket ds_com;
	protected DatagramPacket dp_srv;
	protected int max_msg;
	protected String message;
	
	public Client(InetAddress srv_adr, int srv_port) throws SocketException {
		this(srv_adr, srv_port, DEFAULT_MAX_MSG, DEFAULT_CLIENT_MSG);
	}
	
	public Client(InetAddress srv_adr, int srv_port, int maximum_nb_msg) throws SocketException {
		this(srv_adr, srv_port, maximum_nb_msg, DEFAULT_CLIENT_MSG);
	}
	
	public Client(InetAddress srv_adr, int srv_port, String client_message) throws SocketException {
		this(srv_adr, srv_port, DEFAULT_MAX_MSG, client_message);
	}
	
	public Client(InetAddress srv_adr, int srv_port, int maximum_nb_msg, String client_message) throws SocketException {
		ds_com = new DatagramSocket();
		byte[] buffer = new byte[8192];
		dp_srv = new DatagramPacket(buffer, 8192, srv_adr, srv_port);
		max_msg = maximum_nb_msg;
		message = client_message;
	}
	
	protected int recevoir() {
		byte[] buffer = new byte[8192];
		DatagramPacket rec_dp = new DatagramPacket(buffer, 8192);
		int port_srv = dp_srv.getPort();
		try {
			ds_com.receive(rec_dp);
			port_srv = rec_dp.getPort();
		} catch (IOException e) {
			System.err.println("Erreur lors de la reception d'un message serveur : ");
			e.printStackTrace();
		}
		String rec_msg = "";
		try {
			rec_msg = new String (rec_dp.getData(), "ascii");
		} catch (UnsupportedEncodingException e) {
			System.err.println("Erreur lors du decodage d'un message serveur : ");
			e.printStackTrace();
		}
		System.out.println("Reponse serveur : " + rec_msg);
		return port_srv;
	}
	
	protected void envoyer(String message, DatagramPacket dp_serveur, int port_serveur) {
		dp_serveur.setData(message.getBytes());
		dp_serveur.setPort(port_serveur);
		try {
			ds_com.send(dp_serveur);
		} catch (IOException e) {
			System.err.println("Erreur lors de la reponse au serveur : ");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("Initialisation de la communication, cote client");
		String init = "CO";
		dp_srv.setData(init.getBytes());
		try {
			ds_com.send(dp_srv);
		} catch (IOException e) {
			System.err.println("Erreur lors de l'initialisation de la communication : ");
			e.printStackTrace();
		}
		int nb_msg_send = 0;
		boolean fin_com = false;
		while (!fin_com) {
			int srv_port = recevoir();
			envoyer(message, dp_srv, srv_port);
			nb_msg_send++;
			if (nb_msg_send == max_msg) {
				System.out.println("Fermeture de la communication cote client.");
				envoyer("DE", dp_srv, srv_port);
				fin_com = true;
			}
		}
	}

}
