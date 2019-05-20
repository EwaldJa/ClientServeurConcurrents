package peerToPeer;

import java.net.*;

public class CreerServer {

	public static void main(String[] args) {
		int port_serveur = 5000;
		try {
			new Thread(new P2PServeur(port_serveur)).start();
		} catch (SocketException e) {
			System.err.println("Erreur à l'initisation du serveur P2P : ");
			e.printStackTrace();
		}
		
	}

}
