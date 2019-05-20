package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * <b>LoggerClient est la classe qui permet de tracer l'�xecution d'une session d'ex�cution</b><br>
 * <p>
 * LoggerClient poss�de deux attributs et cinq constantes qui servent aux m�thodes<br>
 * de la classe :<br>
 * </p>
 * <ul>
 * <li>Un entier qui symbolise le niveau minimum de priorit� des messages � tracer<br></li>
 * <li>Un <b>PrintWriter</b> qui permet d'�crire les logs<br></li>
 * <li>Cinq constantes qui d�finissent les niveaux de priorit� des messages<br></li>
 * </ul>
 * <p>
 * Cette classe poss�de plusieurs constructeurs, en fonction qu'elle doive r�cup�rer<br>
 * sa <i>configuration</i> dans un fichier ou utiliser des valeurs par d�faut.<br>
 * Elle poss�de �galement la m�thode <i>log</i> de tra�age.<br>
 * </p>
 * 
 * @author Ewald JANIN
 * 
 * @see #ALL
 * @see #DEBUG
 * @see #INFO
 * @see #IMPORTANT
 * @see #OFF
 * @see #level
 * @see #mypw
 * @see LoggerClient#LoggerClient(String)
 * @see LoggerClient#LoggerClient(int, String)
 * @see LoggerClient#LoggerClient(String, String)
 * @see LoggerClient#log(int, String)
 * @see LoggerClient#dispose()
 */
public class LoggerClient{
	
	/**
	 * <b>ALL, constante valant 0</b><br>
	 * <p>
	 * D�finit le niveau de priorit� le plus bas.<br>
	 * Constante d�finie de mani�re publique et statique pour �tre<br>
	 * acc�d�e depuis les autres classes<br>
	 * </p>
	 */
	public static final int ALL = 0;
	
	/**
	 * <b>DEBUG, constante valant 100</b><br>
	 * <p>
	 * D�finit le niveau de priorit� par d�faut.<br>
	 * Constante d�finie de mani�re publique et statique pour �tre<br>
	 * acc�d�e depuis les autres classes<br>
	 * </p>
	 */
	public static final int DEBUG = 100;
	
	/**
	 * <b>INFO, constante valant 500</b><br>
	 * <p>
	 * D�finit le niveau de priorit� interm�diaire.<br>
	 * Constante d�finie de mani�re publique et statique pour �tre<br>
	 * acc�d�e depuis les autres classes<br>
	 * </p>
	 */
	public static final int INFO = 500;
	
	/**
	 * <b>ALL, constante valant 900</b><br>
	 * <p>
	 * D�finit le niveau de priorit� haut.<br>
	 * Constante d�finie de mani�re publique et statique pour �tre<br>
	 * acc�d�e depuis les autres classes<br>
	 * </p>
	 */
	public static final int IMPORTANT = 900;
	
	/**
	 * <b>ALL, constante valant Integer.MAX_VALUE</b><br>
	 * <p>
	 * D�finit le niveau de priorit� maximum.<br>
	 * Constante d�finie de mani�re publique et statique pour �tre<br>
	 * acc�d�e depuis les autres classes<br>
	 * </p>
	 */
	public static final int OFF = Integer.MAX_VALUE;
	
	/**
	 * <b>Entier d�finissant le seuil minimum de priorit� � tracer<br></b>
	 * <p>
	 * Est utilis� dans la m�thode <b>log</b> o� il est compar� au niveau de<br>
	 * priorit� du message demand� � tracer. Sa valeur par d�faut correspond<br>
	 * � la constante <i>DEBUG</i>, mais il peut �tre d�fini dans le fichier<br>
	 * pass� en configuration d'un constructeur de <b>LoggerClient</b>.
	 * </p>
	 * @see #DEBUG
	 * @see LoggerClient#log(int, String)
	 */
	private int level;
	
	/**
	 * <b>Writer de sortie, correspond soit � un fichier soit � un flux<br></b>
	 * <p>
	 * Il est initialis� dans les constructeurs de <b>LoggerClient</b>, et est<br>
	 * utilis� dans la m�thode <b>log</b> pour �crire dans un fichier/flux.<br>
	 * Par d�faut, la sortie est <i>System.err</i>.
	 * </p>
	 * @see LoggerClient#log(int, String)
	 */
	private PrintWriter mypw;
	
	/**
	 * <b>String repr�sentant le nom du client dont on �crit les logs<br></b>
	 * <p>
	 * Il est initialis� dans les constructeurs de <b>LoggerClient</b>, et est<br>
	 * utilis� dans la m�thode <b>log</b> pour �crire le nom du client.<br>
	 * </p>
	 * @see LoggerClient#log(int, String)
	 */
	private String client_name;
	
	/**
	 * <b>Constructeur de base de LoggerClient<br></b>
	 * <p>
	 * Ce constructeur initialise les attributs � leurs valeurs<br>
	 * par d�faut (<i>DEBUG</i> pour <b>level</b> et <i>Systemm.err</i><br> pour <b>mypw</b><br>
	 * 
	 * @see LoggerClient#level
	 * @see LoggerClient#mypw
	 */
	public LoggerClient(String client) {
		this.client_name = client;
		this.level = LoggerClient.DEBUG;
		this.mypw = new PrintWriter(System.err);
		this.log(LoggerClient.DEBUG, "Cr�ation d'un nouveau LoggerClient sans fichier de configuration, level=" + this.level + ", flux de sortie : System.err");
	}
	
	/**
	 * <b>Constructeur de LoggerClient<br></b>
	 * <p>
	 * Ce constructeur initialise les attributs, la valeur du <b>level</b> est pass�e<br>
	 * en argument. <b>mypw</b> est initialis� avec pour sortie <i>Systemm.err</i>.
	 * 
	 * @param niveau
	 * 			Le niveau minimum qu'il faudra afficher, pas les priorit�s inf�rieures.
	 * @see LoggerClient#level
	 * @see LoggerClient#mypw
	 */
	public LoggerClient(int niveau, String client) {
		this.client_name = client;
		this.level = niveau;
		this.mypw = new PrintWriter(System.err);
		this.log(LoggerClient.DEBUG, "Cr�ation d'un nouveau LoggerClient sans fichier de configuration, level=" + this.level + ", flux de sortie : System.err");
	}
	
	/**
	 * <b>Constructeur de LoggerClient<br></b>
	 * <p>
	 * Ce constructeur initialise les attributs, la valeur du <b>level</b> est celle<br>
	 * par d�faut. <b>mypw</b> est initialis� avec pour sortie ce qui est pass� en <br>
	 * coniguration dans le fichier pass� en argument, que ce soit un flux ou un fichier.<br>
	 * 
	 * @param file
	 * 			Le fichier de configuration dont les param�tres sont � extraire.
	 * @see LoggerClient#level
	 * @see LoggerClient#mypw
	 */
	public LoggerClient(String file, String client) {
		this.client_name = client;
		this.level = LoggerClient.DEBUG; //Niveau par d�faut
		try {
			File config = new File(file);
			if(config.exists()) { //Si le fichier de configuration sp�cifi� existe bien
				BufferedReader br = new BufferedReader(new FileReader(config));
				String lignelue = br.readLine();
				if(!(lignelue == null)) {	//SI le fichier de configuration n'est pas vide
					switch (lignelue) {		//D�finition du niveau avec le fichier de configuration
					case "ALL":
						this.level = LoggerClient.ALL;
						break;
						
					case "DEBUG":
						this.level = LoggerClient.DEBUG;
						break;
						
					case "INFO":
						this.level = LoggerClient.INFO;
						break;
						
					case "IMPORTANT":
						this.level = LoggerClient.IMPORTANT;
						break;
						
					case "OFF":
						this.level = LoggerClient.OFF;
						break;				
					}
					
					lignelue = br.readLine();
					br.close();
					
					if(!(lignelue == null)) {	//Si la sortie a bien �t� sp�cifi�e
						switch(lignelue) {		//D�finition de la sortie
						case "System.err":
							this.mypw = new PrintWriter(System.err);
							break;
							
						case "System.out":
							this.mypw = new PrintWriter(System.out);
							break;
						
						default :	//Si ce n'est pas un flux, c'est un fichier
							try {
								File logs = new File(client_name + lignelue);
								logs.createNewFile();	//On tente de cr�er le fichier qui servira � enregistrer les logs
								this.mypw = new PrintWriter(new FileWriter(logs));
								this.log(LoggerClient.DEBUG, "Cr�ation d'un nouveau LoggerClient avec fichier de configuration, level = " + this.level + ", flux de sortie : " + lignelue);
							} catch (Exception e) {
								e.printStackTrace();
								this.mypw = new PrintWriter(System.err);
								this.log(LoggerClient.OFF, "ERREUR : Impossible de cr�er un LoggerClient avec la sortie sp�cifi�e dans la configuration, cr�ation par d�faut : level = " + this.level + ", flux de sortie : System.err");
							}
						}
					}
					else {	//Si la configuration de la sortie est nulle
						this.mypw = new PrintWriter(System.err);
						this.log(LoggerClient.OFF, "Impossible de cr�er un LoggerClient avec le fichier sp�cifi� dans la configuration, flux de sortie : System.err");
					}
				}
				else {	//Si le fichier de configuration est vide
					this.level = LoggerClient.DEBUG;
					this.mypw = new PrintWriter(System.err);
					this.log(LoggerClient.OFF, "ERREUR : fichier config sp�cifi� illisible, cr�ation LoggerClient d�faut : level=" + this.level + ", flux de sortie : System.err");

				}
			}
			else {	//Si le fichier de configuration est inexistant
				this.level = LoggerClient.DEBUG;
				this.mypw = new PrintWriter(System.err);
				this.log(LoggerClient.OFF, "ERREUR : fichier config sp�cifi� introuvable, cr�ation LoggerClient d�faut : level=" + this.level + ", flux de sortie : System.err");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * <b>M�thode qui fait le logging des messages<br></b>
	 * <p>
	 * Si le niveau de priorit� du message pass� en argument est<br>
	 * sup�rieur ou �gal au niveau minimum de priorit� � afficher<br>
	 * d�fini dans le <b>LoggerClient</b>, alors le message est envoy�<br>
	 * sur la sortie choisie avec le <b>FileWriter</b> myfw de la classe.<br>
	 * </p>
	 * 
	 * @param level
	 * 			Niveau de priorit� du message � tracer.
	 * @param message
	 * 			Nouveau message � tracer.
	 * 
	 * @see LoggerClient#level
	 * @see LoggerClient#mypw
	 */
	public void log(int level, String message) {
		try {
			if (level >= this.level) {	//Si le niveau de priorit� autorise bien � tracer ce message
				switch(level) {	//On �crit le message sur la osrtie, avec un nombre de tabulations selon sa priorit�, pour plus de lisibilit�, et avec l'heure, pour plus de tra�abilit�
				case LoggerClient.ALL:
					mypw.println("				Client : " + client_name + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case LoggerClient.DEBUG:
					mypw.println("			Client : " + client_name + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case LoggerClient.INFO:
					mypw.println("		Client : " + client_name + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case LoggerClient.IMPORTANT:
					mypw.println("	Client : " + client_name + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case LoggerClient.OFF:
					mypw.println("Client : " + client_name + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <b>M�thode pour quitter une session<br></b>
	 * <p>
	 * Permet de fermer correctement le flux d'�criture.<br>
	 * </p>
	 */
	public void dispose() {
		this.log(LoggerClient.OFF, "Fermeture du Logger !");
		mypw.close();
	}

}
