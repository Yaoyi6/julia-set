package Modele;
/** 
 * Classe représentant les ensembles de Mandelbrot
 * @author Solimani et Luo
 */  

public final class EnsembleDeMandelbrot extends EnsembleDeJulia{
	/**
	 * Complexe nul utilisé dans indiceDivergence 
	 */
	private final static Complexe zero=new Complexe(0,0);

	public EnsembleDeMandelbrot(Complexe c,int maxIterations, int longueur, int hauteur) {
		super(zero,maxIterations,longueur,hauteur);
	}
	/**
     * Redéfinition d'indiceDivergence de la classe EnsembleDeJulia pour partir du complexe zero
     * @param x0 Nombre complexe de départ 
     * @return Nombre d'itérations pour que la suite diverge 
     */
    public int indiceDivergence(Complexe x0){
        int i=0; 
        Complexe c=zero;
        while (i++ < nbIterationsMax && c.modulus() < 2.)
        	c=x0.plus(c.times(c));
        return i;
    }
}
