package peerToPeer;

import java.net.*;

public class CreerMary {

	public static void main(String[] args) {
		try {
			new Thread(new P2PClient("Mary")).start();
		} catch (SocketException e) {
			System.err.println("Erreur à l'initisation de Mary : ");
			e.printStackTrace();
		}
		
	}

}
