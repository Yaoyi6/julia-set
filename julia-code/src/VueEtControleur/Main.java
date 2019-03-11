package VueEtControleur;

/** 
 * Classe contenant la fonction main
 * @author Solimani et Luo
 */  
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import javax.imageio.ImageIO;
import Modele.*;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
	private final int x=900,y=600;
	@Override
	public void start(Stage primaryStage) throws IOException { 
		try {
			// Chargement du fichier fxml de la fenetre de connexion 
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("IG.fxml"));
			loader.load();

			// Mise en place de la fenetre de connexion
			primaryStage.setTitle("Julia Ensemble");
			primaryStage.setScene(new Scene(loader.<VBox>getRoot(),x,y));
			
			//Affichage de l'interface graphique
			primaryStage.show();

		} catch (Exception ex) {
			System.err.println("Echec du démarrage");
			System.out.print(ex);
			System.exit(1);
		}
	}
	/**
	 * 
	 * @param message Chaine de caractères à afficher avant la saisie
	 * @param sc Objet Scanner pour lire l'entrée standard
	 * @return Entier saisi par l'utilisateur
	 */
	private static int saisieEntier(String message,Scanner sc){
		System.out.println(message);
		boolean finSaisie=false;
		int resultat=0;
		while(!finSaisie) {
			try{
				resultat=sc.nextInt();
				finSaisie=true;
			}catch (InputMismatchException e) {
				System.out.println("Saisie incorrecte,ce n'est pas 1 entier.");
				sc.next(); 
			}
		}
		return resultat;	
	}
	/**
	 * 
	 * @param message Chaîne de caractères à afficher avant la saisie
	 * @param sc Objet Scanner pour lire l'entrée standard
	 * @return Nombre saisi par l'utilisateur
	 */
	private static double saisieDouble(String message,Scanner sc){
		System.out.println(message);
		boolean finSaisie=false;
		double resultat=0;
		while(!finSaisie) {
			try{
				resultat=sc.nextDouble();
				finSaisie=true;
			}catch (InputMismatchException e) {
				System.out.println("Saisie incorrecte, ce n'est pas 1 nombre décimal.");
				sc.next(); 
			}
		}
		return resultat;	
	}
	/**
	 * 
	 * @param message Chaîne de caractères à afficher avant la saisie
	 * @param sc Objet Scanner pour lire l'entrée standard
	 * @return Chaîne de caractères saisi par l'utilisateur
	 */
	private static String saisieNomFichier(String message,Scanner sc){
		System.out.println(message);
		boolean finSaisie=false;
		String resultat="";
		while(!finSaisie){
			try{
				resultat=sc.next();
				finSaisie=true;
			}catch (InputMismatchException e) {
				System.out.println("Saisie incorrecte, vous devez entrez 1 nom de fichier valable.");
				sc.next(); 
			}
		}
		return resultat;	
	}

	public static void main(String[] args) {
		//choix du mode GUI ou console
		Scanner sc=new Scanner(System.in);
		int reponse=-1;
		while(reponse!=0 && reponse!=1)
			reponse=saisieEntier("Si vous voulez utiliser l'application en ligne de commande, tapez 0 sinon tapez 1.",sc);
		if(reponse==1)
			Application.launch(args);
		else {
			sc.useLocale(Locale.US);
			double partieRelle=saisieDouble("Entrez la partie réelle du nombre complexe de départ",sc),
			partieImaginaire=saisieDouble("Entrez la partie imaginaire du nombre complexe de départ",sc);
			
			int nbMaxIterations=saisieEntier("Entrez le nombre maximum d'itérations",sc),
			longueurImage=saisieEntier("Entrez la longueur de l'image",sc),
			hauteurImage=saisieEntier("Entrez la hauteur de l'image",sc);
			
			String nomFichier=saisieNomFichier("Entrez le nom du fichier",sc);
			
			EnsembleDeJulia ensemble=new EnsembleDeJulia(new Complexe(partieRelle,partieImaginaire),
					nbMaxIterations,longueurImage,hauteurImage);
			ForkJoinPool pool=new ForkJoinPool();
			pool.invoke(ensemble.new Dessin(longueurImage));
			BufferedImage bImage=SwingFXUtils.fromFXImage(ensemble.getImage(),null);
			try {
				ImageIO.write(bImage,"png",new File(nomFichier+".png"));
				System.out.println("Votre image est bien enregistrée dans le fichier "+nomFichier+".png");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
