package tools;

import java.io.*;

/**
 * Classe permettant de gérer les entrées clavier dans le projet (notamment en mode d'affichage
 * console), pour ne pas avoir à écrire de lourdes méthodes dans les contrôleurs déjà
 * suffisamment chargés
 * Elle efface aussi les erreurs
 * 
 * @author Ewald
 *
 */
public class KeyboardReader {
	private static BufferedReader mybr=new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Méthode permettant de récupérer le caractère saisi par l'utilisateur
	 * 
	 * @return le caractère en question
	 */
	public static char getChar() {
		return getString().charAt(0);
	}

	/**
	 * Méthode permettant de récupérer le tableau de caractères saisi par l'utilisateur
	 * Utile pour un code par exemple
	 * 
	 * @return le tableau de caractères en question
	 */
	public static char[] getChars() {
		String s = getString();
		return s.toCharArray();
	}
				
	/**
	 * Méthode permettant de récupérer la chaîne saisie par l'utilisateur
	 * 
	 * @return la chaîne en question
	 */
	public static String getString() {
		return getLine();
	}
		
	/**
	 * Méthode permettant de récupérer l'entier saisi par l'utilisateur
	 * 
	 * @return l'entier en question
	 */
	public static int getInt() {
		try{
			return Integer.parseInt(getLine());
		} catch(NumberFormatException e) { return -1;}
	}
	
	/**
	 * Permet de faire une pause dans la demande de caractère
	 */
	public static void pause() {
		try{
			mybr.read();
		} catch(IOException e) {}
	}

	/**
	 * Permet de récupérer la ligne complète saisie par l'utilisateur
	 * 
	 * @return toute la ligne
	 */
	private static String getLine() {
		String line;
		try{
			line = mybr.readLine();
		} catch(IOException e) { return "";}
		return line;
	}
}
