package Modele;

/** 
 * Classe représentant les ensembles de Julia
 * @author Solimani et Luo
 */  
import java.util.concurrent.RecursiveAction;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class EnsembleDeJulia {
	/**
	 * Nombre complexe associé à l'ensemble
	 */
    protected Complexe nombreComplexe;
    /**
     * Nombre d'itérations maximal pour déterminer si la suite correspondant 
     * à 1 point du plan complexe diverge ou non (utilisé dans indiceDivergence)
     */
    protected int nbIterationsMax;
    /**
     * Valeurs de zoom et de décalage en abscisse et en ordonnée de l'image à afficher
     */
    private static double zoom,moveX,moveY;
    /**
     * Dimensions de l'image contenant l'ensemble dans la GUI
     */
    protected final int longueurImage,hauteurImage;
    /**
     * Image à afficher dans la GUI
     */
    protected final WritableImage img;
    /**
     * Objet pour mettre à jour les pixels de img
     */
    protected final PixelWriter pixel;
    /**
     * Abscisse ou la zone de l'image à dessiner commence (utilisé dans la
     * classe imbriquée Dessin)
     */
    private int abscisseImage;

    public EnsembleDeJulia(Complexe c,int maxIterations,int longueur,int hauteur){
        nombreComplexe=c;
        nbIterationsMax=maxIterations;
        longueurImage=longueur;
        hauteurImage=hauteur;
        img=new WritableImage(longueur,hauteur);
        pixel=img.getPixelWriter(); 
    }
    // getters et setters
    public static double getZoom(){
    	return zoom;
    }
    public static double getMoveX(){
    	return moveX;
    }
    public static double getMoveY(){
    	return moveY;
    }
    public void setAbscisseImage(int x){
    	abscisseImage=x;
    }
    public static void setZoom(double z){
    	zoom=z;
    }
    public static void setMoveX(double x){
    	moveX=x;
    }
    public static void setMoveY(double y){
    	moveY=y;
    }
    public WritableImage getImage() {
    	return img;
    }
    /**
     * Calcule l'indice de divergence d'1 nombre complexe
     * @param x0 Nombre complexe de départ 
     * @return Nombre d'itérations pour que la suite commenceant par x0 diverge 
     */
    public int indiceDivergence(Complexe x0){
        int i=0; 
        while (i++ < nbIterationsMax && x0.modulus() < 2.)
            x0=nombreComplexe.plus(x0.times(x0));
        return i;
    }	
    /**
     * Classe imbriquée permettant de dessiner l'ensemble de Julia this
     * @author Solimani et Luo
     *
     */
    public class Dessin extends RecursiveAction{
    	protected static final long serialVersionUID = 1L;
    	/**
    	 * Longueur de la partie d'image à dessiner
    	 */
		protected int longueur;
		/**
		 * Decalage en pixels de l'image à l'appui d'1 bouton pour se déplacer dans l'image
		 */
    	public final static int decalageImage=60;
    	
    	/**
    	 * 
    	 * @param longueur Longueur (en pixels) de l'image à dessiner
    	 */
    	public Dessin(int longueur){
    		this.longueur=longueur;
    	}
    	/**
    	 * Dessine sur l'image la zone de l'ensemble courant commencant par abscisseImage 
    	 * et de longueur la valeur du champ longueur
    	 */
	    public void dessinerEnsemble(){
	        Color couleur;
	        int i;
	        for(int j=0;j<longueur;j++){
	            for(int y=0;y<hauteurImage;y++){
		            		i=EnsembleDeJulia.this.indiceDivergence(new Complexe(1.5*(abscisseImage-
		            				(longueurImage+moveX)/2)/(0.5*zoom*longueurImage),
								        1.5*(y-(hauteurImage+moveY)/2)/(0.5*zoom*hauteurImage)));
	            		couleur=Color.rgb((i%3*(i*20))%256,(i%3*(i*10))%256,(i%8*5*i)%256);
					pixel.setColor(abscisseImage,y,couleur);
	            }
	            abscisseImage++;
	        }
	    }
	    /**
	     * Divise le dessin de l'image en sous-tâches en fonction de la valeur du champ longueur
	     * (chaque sous-tâche dessine 1 partie de l'image avec dessinerEnsemble)
	     */
	    @Override
		protected void compute(){
			Dessin d1,d2;
			if(longueur<=40)
				dessinerEnsemble();
			else {
				if(longueur%2==1) {
					d1=new Dessin(longueur/2);
					d2=new Dessin(longueur/2+1);
				}
				else{
					d1=new Dessin(longueur/2);
					d2=new Dessin(longueur/2);
				}
				d1.compute();
				d2.fork();
				d2.join();
			}	
		}
    }	
}
   