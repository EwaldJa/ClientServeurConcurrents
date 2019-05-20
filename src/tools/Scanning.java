package tools;

import java.util.*;
import java.net.*;

public class Scanning {
	
	public static ArrayList<Integer> scan(int port_min, int port_max) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (int i = port_min; i <= port_max; i++) {
			try {
				DatagramSocket ds = new DatagramSocket(i);
				res.add(i);
				ds.close();
			} catch (SocketException e) {
				System.out.println("Le port numero " + i + " n'est pas disponible");
			}
		}
		return res;
	}
	
	
	public static void main(String[] args) {
		scan(0, 65535);
	}
}
