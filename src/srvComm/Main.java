package srvComm;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) {
		int port_serveur = 5000;
		try {
			new Thread(new Serveur(port_serveur)).start();
		} catch (SocketException e) {
			System.err.println("Erreur a l'initisation du serveur : ");
			e.printStackTrace();
		}
		InetAddress srv_adr = null;
		try {
			srv_adr = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			System.err.println("Impossible de creer l'adresse localhost : ");
			e.printStackTrace();
		}
		try {
			new Thread(new Client(srv_adr, port_serveur)).start();
		} catch (SocketException e) {
			System.err.println("Erreur a l'initialisation du client : ");
			e.printStackTrace();
		}
		
		try {
			new Thread(new Client(srv_adr, port_serveur, "Coucou, je suis un autre client")).start();
		} catch (SocketException e) {
			System.err.println("Erreur a l'initialisation du client : ");
			e.printStackTrace();
		}
	}

}
