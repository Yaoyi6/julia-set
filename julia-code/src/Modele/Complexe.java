package Modele;

/** 
 * Classe représentant les nombres complexes
 * @author Solimani et Luo
 */  
public class Complexe{
    private double partieReelle,partieImaginaire;
    
    public Complexe(double re,double im){
        partieReelle=re;
        partieImaginaire=im;
    }
    /**
     * @param c Seconde opérande de l'addition
     * @return 1 nombre complexe résultant de l'addition de this et de c
     */
    Complexe plus(Complexe c){
        return new Complexe(partieReelle+c.getPartieReelle(), partieImaginaire+c.getPartieImaginaire());
    }
    /**
     * @param c Seconde opérande de la multiplication
     * @return 1 nombre complexe résultant de la multiplication de this et de c
     */
    Complexe times(Complexe c){
        return new Complexe(partieReelle*c.getPartieReelle()-partieImaginaire*c.getPartieImaginaire(),
        		partieReelle*c.getPartieImaginaire()+partieImaginaire*c.getPartieReelle());
    }
    /**
     * 
     * @return Module du nombre complexe this
     */
    public double modulus(){
        return Math.sqrt(partieReelle*partieReelle+partieImaginaire*partieImaginaire);
    }
  //getters et setters
    public double getPartieReelle(){
        return partieReelle;
    }
    public double getPartieImaginaire(){
        return partieImaginaire;
    }
    public void setPartieReelle(double re){
        partieReelle=re;
    }
    public void setPartieImaginaire(double im){
        partieImaginaire=im;
    }
}