package VueEtControleur;

/** 
 * Classe qui gère la GUI
 * @author Solimani et Luo
 */  
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import Modele.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Controleur implements EventHandler<ActionEvent>{
    @FXML
    private TextField textPR;
    @FXML
    private TextField textPI; 
    @FXML
    private TextField textIteMax;
    @FXML
    private Button btnValider;
    @FXML
    private Button btnUp;
    @FXML
    private Button btnDown;
    @FXML
    private Button btnLeft;
    @FXML
    private Button btnRight;
    @FXML
    private Pane pnlVue;
    @FXML
    private Button btnMandelbrot;
    
    private Alert alert = new Alert(AlertType.WARNING);
    
    private EnsembleDeJulia ensemble;
    
    private String ensembleCourant;
    
    private final int longueurImage=670,hauteurImage=600;
    
    /**
     *  Vérifie qu'aucun champ n'est vide pour afficher 1 ensemble de Julia
     * @return booléen vrai si tous les champs sont remplis et faux sinon
     */
    public boolean checkTextFieldJulia(){
	    if((textPR.getText().trim().isEmpty())
	            ||(textPI.getText().trim().isEmpty()) 
	            ||(textIteMax.getText().trim().isEmpty()))
	        return false;                
	    return true;
    }
    /**
     *  Vérifie que le nombre d'itérations maximum est indiqué pour afficher l'ensemble de Mandelbrot
     * @return booléen vrai si le champs est rempli et faux sinon
     */
    public boolean checkTextFieldMandelbrot() {
	    if(textIteMax.getText().trim().isEmpty())
	        return false;                
	    return true;
    }
    /**
     * Affiche l'image d'1 ensemblea près avoir cliqué sur le bouton de validation
     * @param typeEnsemble Le type de l'ensemble (soit Julia soit Mandelbrot)
     */
	public void afficherEnsemble(String typeEnsemble){
        if(typeEnsemble.equals("julia") && !checkTextFieldJulia()){
	        alert.setTitle("Paramètres erreurs");
	        alert.setContentText("Veuillez bien remplir tous les paramètres.");
	        alert.showAndWait();
        }
        else if(typeEnsemble.equals("mandelbrot") && !checkTextFieldMandelbrot()){
	        alert.setTitle("Paramètres erreurs");
	        alert.setContentText("Veuillez indiquer le nombre maximum d'itérations.");
	        alert.showAndWait();
        }
        else if(EnsembleDeJulia.getZoom()<=0.0)
        	EnsembleDeJulia.setZoom(0.0);
        else{
	        try {
	        	int iteMax = Integer.parseInt(textIteMax.getText());
	        	if(typeEnsemble.equals("mandelbrot"))
	        		ensemble=new EnsembleDeMandelbrot(new Complexe(0,0),iteMax,longueurImage,hauteurImage);
	        	else {
	        		double reel = Double.parseDouble(textPR.getText()),
			            imaginaire = Double.parseDouble(textPI.getText());
	        		ensemble=new EnsembleDeJulia(new Complexe(reel,imaginaire),iteMax,longueurImage,hauteurImage);
	        	}
	        	//initialisation de l'abscisse de l'image à dessiner
	        	ensemble.setAbscisseImage(0);
	            ForkJoinPool pool=new ForkJoinPool();
	            pool.invoke(ensemble.new Dessin(longueurImage));
	            ImageView imageView = new ImageView();
	            imageView.setImage(ensemble.getImage());
	            pnlVue.getChildren().add(imageView);   
	        } catch (NumberFormatException nfe){
	            alert.setTitle("Paramètres incorrect");
	            alert.setContentText("Types des paramètres incorrect");
	            alert.showAndWait();
	        } 
        }
    } 

	/**
	 * Affiche l'ensemble de Julia correspondant aux paramètres saisis à l'appui du bouton de validation
	 * @throws IOException Lance 1 exception si les valeurs saisies dans les champs sont incorrectes
	 */
	@FXML
    public void clickBtnValider() throws IOException {
		//initialization du zoom et du décalage de l'image
		EnsembleDeJulia.setZoom(1.0);
	    EnsembleDeJulia.setMoveX(0.0);
	    EnsembleDeJulia.setMoveY(0.0);
	    ensembleCourant="julia";
	    afficherEnsemble(ensembleCourant);
    }
	/**
	 * Décale l'image vers le haut à l'appui du bouton correspondant
	 */
   @FXML
   public void clickBtnUp() {
	   EnsembleDeJulia.setMoveY(EnsembleDeJulia.getMoveY()+EnsembleDeJulia.Dessin.decalageImage);
	   afficherEnsemble(ensembleCourant);
   }
   /**
	 * Décale l'image vers le haut à l'appui du bouton correspondant
	 */
   @FXML
   public void clickBtnDown() {
	   EnsembleDeJulia.setMoveY(EnsembleDeJulia.getMoveY()-EnsembleDeJulia.Dessin.decalageImage);
	   afficherEnsemble(ensembleCourant);
   }
   /**
	 * Décale l'image vers le haut à l'appui du bouton correspondant
	 */
   @FXML
   public void clickBtnLeft() {
	   EnsembleDeJulia.setMoveX(EnsembleDeJulia.getMoveX()+EnsembleDeJulia.Dessin.decalageImage);
	   afficherEnsemble(ensembleCourant);
   }
   /**
	 * Décale l'image vers le haut à l'appui du bouton correspondant
	 */
   @FXML
   public void clickBtnRight(){
	   EnsembleDeJulia.setMoveX(EnsembleDeJulia.getMoveX()-EnsembleDeJulia.Dessin.decalageImage);
	   afficherEnsemble(ensembleCourant);
   }
   /**
	 * Zoome sur l'image à l'appui du bouton correspondant
	 */
   @FXML
   public void clickBtnZoomer() {
	   EnsembleDeJulia.setZoom(EnsembleDeJulia.getZoom()+0.2);
	   afficherEnsemble(ensembleCourant);
   }
   /**
	 * Dé-zoome sur l'image à l'appui du bouton correspondant
	 */
   @FXML
   public void clickBtnDezoomer(){
	   EnsembleDeJulia.setZoom(EnsembleDeJulia.getZoom()-0.2);
       if(EnsembleDeJulia.getZoom()<=1) 
    	   EnsembleDeJulia.setZoom(1.0);  
       afficherEnsemble(ensembleCourant);
   }
	@Override
	public void handle(ActionEvent arg0) {
		
	}  
	/**
	 * Affiche l'ensemble de Mandelbrot correspondant aux paramètres saisis à l'appui du bouton de validation
	 * @throws IOException Lance 1 exception si les valeurs saisies dans les champs sont incorrectes
	 */
	@FXML
	public void clickBtnMandelbrot() throws IOException{
		//initialization du zoom et du décalage de l'image
		EnsembleDeJulia.setZoom(1.0);
	    EnsembleDeJulia.setMoveX(0.0);
	    EnsembleDeJulia.setMoveY(0.0);
		ensembleCourant="mandelbrot";
		afficherEnsemble(ensembleCourant);
	}
}
