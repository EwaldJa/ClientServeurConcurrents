package peerToPeer;

import java.net.SocketException;

public class CreerClientLambda {
	
	public static void main(String[] args) {
		try {
			new Thread(new P2PClient("Client")).start();
		} catch (SocketException e) {
			System.err.println("Erreur � l'initisation de Client");
			e.printStackTrace();
		}
		
	}

}
