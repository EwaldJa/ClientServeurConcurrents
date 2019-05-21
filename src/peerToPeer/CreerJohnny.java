package peerToPeer;

import java.net.SocketException;

public class CreerJohnny {

	public static void main(String[] args) {
		try {
			new Thread(new P2PClient("Johnny")).start();
		} catch (SocketException e) {
			System.err.println("Erreur à l'initisation de Johnny : ");
			e.printStackTrace();
		}
		
	}

}
