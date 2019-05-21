package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe permettant de gerer les entrees clavier dans le projet (notamment en mode d'affichage
 * console), pour ne pas avoir a ecrire de lourdes methodes dans les contr�oleurs deja
 * suffisamment charges
 * Elle efface aussi les erreurs
 * 
 * @author Ewald
 *
 */
public class KeyboardReader {
	private static BufferedReader mybr=new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Methode permettant de recuperer le caractere saisi par l'utilisateur
	 * 
	 * @return le caractere en question
	 */
	public static char getChar() {
		return getString().charAt(0);
	}

	/**
	 * Methode permettant de recuperer le tableau de caracteres saisi par l'utilisateur
	 * Utile pour un code par exemple
	 * 
	 * @return le tableau de caracteres en question
	 */
	public static char[] getChars() {
		String s = getString();
		return s.toCharArray();
	}
				
	/**
	 * Methode permettant de recuperer la chaine saisie par l'utilisateur
	 * 
	 * @return la cha�ne en question
	 */
	public static String getString() {
		return getLine();
	}
		
	/**
	 * Methode permettant de recuperer l'entier saisi par l'utilisateur
	 * 
	 * @return l'entier en question
	 */
	public static int getInt() {
		try{
			return Integer.parseInt(getLine());
		} catch(NumberFormatException e) { return -1;}
	}
	
	/**
	 * Permet de faire une pause dans la demande de caractere
	 */
	public static void pause() {
		try{
			mybr.read();
		} catch(IOException e) {}
	}

	/**
	 * Permet de recuperer la ligne complete saisie par l'utilisateur
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
