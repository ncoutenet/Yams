/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.pojos;

/**
 *
 * @author nicolas
 */

/*
 * Classe définissant un joueur et son score
 */
public class Joueur {
    private String nom;
    private int[] score;
    private boolean[] util;
    private boolean maxiMini;

    public Joueur(String nom, boolean maxiMini) {
        this.nom = nom;
        this.maxiMini = maxiMini;
        this.score = new int[17];
        this.util = new boolean[17];
        for (int i = 0; i < this.score.length; i++){
            this.score[i] = 0;
            this.util[i] = false;
        }
    }
    
    /*
     * Retourne le pseudo du joueur
     */
    public String getNom() {
        return nom;
    }

    /*
     * définit le nom du joueur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /*
     * Retourne le score correspondant à l'index passé en paramètre
     */
    public int getScore(int index) {
        return score[index];
    }

    /*
     * Enregistre le score passé en paramètre dans la case donnée en paramètre
     */
    public void setScore(int index, int score) {
        if(!this.util[index]){
            this.score[index] = score;
            this.util[index] = true;
            if(index < 6){
                this.setTotal(6);
            }
            else if(this.maxiMini && index < 11){
                this.setTotal(11);
            }
            this.setTotal(16);
        }
    }
    
    /*
     * Calcule les differants totaux
     */
    private void setTotal(int index){
        switch(index){
            case 6:
                this.score[index] = 0;
                for(int i = 0; i < 6; i++){
                    this.score[index] += this.score[i];
                }
                if (score[index] > 62){
                    this.score[7] = 35;
                }
                this.score[8] = this.score[6] + this.score[7];
                break;
            case 11:
                this.score[index] = this.score[9] - this.score[10];
                break;
            case 16:
                if(this.maxiMini){
                    this.score[index] = this.score[12] + this.score[13] + this.score[14] + this.score[15] + this.score[8] + this.score[11];
                }
                else{
                    this.score[index] = this.score[9] + this.score[10] + this.score[12] + this.score[13] + this.score[14] + this.score[15] + this.score[8] + this.score[11];
                }
                break;
            default:
                break; //n'arrivera jamais
        }
    }
    
}
