package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * <b>LoggerServer est la classe qui permet de tracer l'éxecution d'une session d'exécution</b><br>
 * <p>
 * LoggerServer possède deux attributs et cinq constantes qui servent aux méthodes<br>
 * de la classe :<br>
 * </p>
 * <ul>
 * <li>Un entier qui symbolise le niveau minimum de priorité des messages à tracer<br></li>
 * <li>Un <b>PrintWriter</b> qui permet d'écrire les logs<br></li>
 * <li>Cinq constantes qui définissent les niveaux de priorité des messages<br></li>
 * </ul>
 * <p>
 * Cette classe possède plusieurs constructeurs, en fonction qu'elle doive récupérer<br>
 * sa <i>configuration</i> dans un fichier ou utiliser des valeurs par défaut.<br>
 * Elle possède également la méthode <i>log</i> de traçage.<br>
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
 * @see LoggerServer#LoggerServer(int)
 * @see LoggerServer#LoggerServer(int, int)
 * @see LoggerServer#LoggerServer(String, int)
 * @see LoggerServer#log(int, String)
 * @see LoggerServer#dispose()
 */
public class LoggerServer{
	
	/**
	 * <b>ALL, constante valant 0</b><br>
	 * <p>
	 * Définit le niveau de priorité le plus bas.<br>
	 * Constante définie de manière publique et statique pour être<br>
	 * accédée depuis les autres classes<br>
	 * </p>
	 */
	public static final int ALL = 0;
	
	/**
	 * <b>DEBUG, constante valant 100</b><br>
	 * <p>
	 * Définit le niveau de priorité par défaut.<br>
	 * Constante définie de manière publique et statique pour être<br>
	 * accédée depuis les autres classes<br>
	 * </p>
	 */
	public static final int DEBUG = 100;
	
	/**
	 * <b>INFO, constante valant 500</b><br>
	 * <p>
	 * Définit le niveau de priorité intermédiaire.<br>
	 * Constante définie de manière publique et statique pour être<br>
	 * accédée depuis les autres classes<br>
	 * </p>
	 */
	public static final int INFO = 500;
	
	/**
	 * <b>ALL, constante valant 900</b><br>
	 * <p>
	 * Définit le niveau de priorité haut.<br>
	 * Constante définie de manière publique et statique pour être<br>
	 * accédée depuis les autres classes<br>
	 * </p>
	 */
	public static final int IMPORTANT = 900;
	
	/**
	 * <b>ALL, constante valant Integer.MAX_VALUE</b><br>
	 * <p>
	 * Définit le niveau de priorité maximum.<br>
	 * Constante définie de manière publique et statique pour être<br>
	 * accédée depuis les autres classes<br>
	 * </p>
	 */
	public static final int OFF = Integer.MAX_VALUE;
	
	/**
	 * <b>Entier définissant le seuil minimum de priorité à tracer<br></b>
	 * <p>
	 * Est utilisé dans la méthode <b>log</b> où il est comparé au niveau de<br>
	 * priorité du message demandé à tracer. Sa valeur par défaut correspond<br>
	 * à la constante <i>DEBUG</i>, mais il peut être défini dans le fichier<br>
	 * passé en configuration d'un constructeur de <b>LoggerServer</b>.
	 * </p>
	 * @see #DEBUG
	 * @see LoggerServer#log(int, String)
	 */
	private int level;
	
	/**
	 * <b>Writer de sortie, correspond soit à un fichier soit à un flux<br></b>
	 * <p>
	 * Il est initialisé dans les constructeurs de <b>LoggerServer</b>, et est<br>
	 * utilisé dans la méthode <b>log</b> pour écrire dans un fichier/flux.<br>
	 * Par défaut, la sortie est <i>System.err</i>.
	 * </p>
	 * @see LoggerServer#log(int, String)
	 */
	private PrintWriter mypw;
	
	/**
	 * <b>String représentant le port du server dont on écrit les logs<br></b>
	 * <p>
	 * Il est initialisé dans les constructeurs de <b>LoggerServer</b>, et est<br>
	 * utilisé dans la méthode <b>log</b> pour écrire le nom du client.<br>
	 * </p>
	 * @see LoggerServer#log(int, String)
	 */
	private int server_port;
	
	/**
	 * <b>Constructeur de base de LoggerServer<br></b>
	 * <p>
	 * Ce constructeur initialise les attributs à leurs valeurs<br>
	 * par défaut (<i>DEBUG</i> pour <b>level</b> et <i>Systemm.err</i><br> pour <b>mypw</b><br>
	 * 
	 * @see LoggerServer#level
	 * @see LoggerServer#mypw
	 */
	public LoggerServer(int port) {
		this.server_port = port;
		this.level = LoggerServer.DEBUG;
		this.mypw = new PrintWriter(System.err);
		this.log(LoggerServer.DEBUG, "Création d'un nouveau LoggerServer sans fichier de configuration, level=" + this.level + ", flux de sortie : System.err");
	}
	
	/**
	 * <b>Constructeur de LoggerServer<br></b>
	 * <p>
	 * Ce constructeur initialise les attributs, la valeur du <b>level</b> est passée<br>
	 * en argument. <b>mypw</b> est initialisé avec pour sortie <i>Systemm.err</i>.
	 * 
	 * @param niveau
	 * 			Le niveau minimum qu'il faudra afficher, pas les priorités inférieures.
	 * @see LoggerServer#level
	 * @see LoggerServer#mypw
	 */
	public LoggerServer(int niveau, int port) {
		this.server_port = port;
		this.level = niveau;
		this.mypw = new PrintWriter(System.err);
		this.log(LoggerServer.DEBUG, "Création d'un nouveau LoggerServer sans fichier de configuration, level=" + this.level + ", flux de sortie : System.err");
	}
	
	/**
	 * <b>Constructeur de LoggerServer<br></b>
	 * <p>
	 * Ce constructeur initialise les attributs, la valeur du <b>level</b> est celle<br>
	 * par défaut. <b>mypw</b> est initialisé avec pour sortie ce qui est passé en <br>
	 * coniguration dans le fichier passé en argument, que ce soit un flux ou un fichier.<br>
	 * 
	 * @param file
	 * 			Le fichier de configuration dont les paramètres sont à extraire.
	 * @see LoggerServer#level
	 * @see LoggerServer#mypw
	 */
	public LoggerServer(String file, int port) {
		this.server_port = port;
		this.level = LoggerServer.DEBUG; //Niveau par défaut
		try {
			File config = new File(file);
			if(config.exists()) { //Si le fichier de configuration spécifié existe bien
				BufferedReader br = new BufferedReader(new FileReader(config));
				String lignelue = br.readLine();
				if(!(lignelue == null)) {	//SI le fichier de configuration n'est pas vide
					switch (lignelue) {		//Définition du niveau avec le fichier de configuration
					case "ALL":
						this.level = LoggerServer.ALL;
						break;
						
					case "DEBUG":
						this.level = LoggerServer.DEBUG;
						break;
						
					case "INFO":
						this.level = LoggerServer.INFO;
						break;
						
					case "IMPORTANT":
						this.level = LoggerServer.IMPORTANT;
						break;
						
					case "OFF":
						this.level = LoggerServer.OFF;
						break;				
					}
					
					lignelue = br.readLine();
					br.close();
					
					if(!(lignelue == null)) {	//Si la sortie a bien été spécifiée
						switch(lignelue) {		//Définition de la sortie
						case "System.err":
							this.mypw = new PrintWriter(System.err);
							break;
							
						case "System.out":
							this.mypw = new PrintWriter(System.out);
							break;
						
						default :	//Si ce n'est pas un flux, c'est un fichier
							try {
								File logs = new File(server_port + lignelue);
								logs.createNewFile();	//On tente de créer le fichier qui servira à enregistrer les logs
								this.mypw = new PrintWriter(new FileWriter(logs));
								this.log(LoggerServer.DEBUG, "Création d'un nouveau LoggerServer avec fichier de configuration, level = " + this.level + ", flux de sortie : " + lignelue);
							} catch (Exception e) {
								e.printStackTrace();
								this.mypw = new PrintWriter(System.err);
								this.log(LoggerServer.OFF, "ERREUR : Impossible de créer un LoggerServer avec la sortie spécifiée dans la configuration, création par défaut : level = " + this.level + ", flux de sortie : System.err");
							}
						}
					}
					else {	//Si la configuration de la sortie est nulle
						this.mypw = new PrintWriter(System.err);
						this.log(LoggerServer.OFF, "Impossible de créer un LoggerServer avec le fichier spécifié dans la configuration, flux de sortie : System.err");
					}
				}
				else {	//Si le fichier de configuration est vide
					this.level = LoggerServer.DEBUG;
					this.mypw = new PrintWriter(System.err);
					this.log(LoggerServer.OFF, "ERREUR : fichier config spécifié illisible, création LoggerServer défaut : level=" + this.level + ", flux de sortie : System.err");

				}
			}
			else {	//Si le fichier de configuration est inexistant
				this.level = LoggerServer.DEBUG;
				this.mypw = new PrintWriter(System.err);
				this.log(LoggerServer.OFF, "ERREUR : fichier config spécifié introuvable, création LoggerServer défaut : level=" + this.level + ", flux de sortie : System.err");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * <b>Méthode qui fait le logging des messages<br></b>
	 * <p>
	 * Si le niveau de priorité du message passé en argument est<br>
	 * supérieur ou égal au niveau minimum de priorité à afficher<br>
	 * défini dans le <b>LoggerServer</b>, alors le message est envoyé<br>
	 * sur la sortie choisie avec le <b>FileWriter</b> myfw de la classe.<br>
	 * </p>
	 * 
	 * @param level
	 * 			Niveau de priorité du message à tracer.
	 * @param message
	 * 			Nouveau message à tracer.
	 * 
	 * @see LoggerServer#level
	 * @see LoggerServer#mypw
	 */
	public void log(int level, String message) {
		try {
			if (level >= this.level) {	//Si le niveau de priorité autorise bien à tracer ce message
				switch(level) {	//On écrit le message sur la osrtie, avec un nombre de tabulations selon sa priorité, pour plus de lisibilité, et avec l'heure, pour plus de traçabilité
				case LoggerServer.ALL:
					mypw.println("				Serveur : " + server_port + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case LoggerServer.DEBUG:
					mypw.println("			Serveur : " + server_port + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case LoggerServer.INFO:
					mypw.println("		Serveur : " + server_port + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case LoggerServer.IMPORTANT:
					mypw.println("	Serveur : " + server_port + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case LoggerServer.OFF:
					mypw.println("Serveur : " + server_port + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
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
	 * <b>Méthode pour quitter une session<br></b>
	 * <p>
	 * Permet de fermer correctement le flux d'écriture.<br>
	 * </p>
	 */
	public void dispose() {
		this.log(LoggerServer.OFF, "Fermeture du Logger !");
		mypw.close();
	}

}
