/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

/**
 *
 * @author nicolas
 */
public class Joueur {
    private String _nom;
    private int[] _score;
    private boolean[] _util;

    public Joueur(String nom) {
        this._nom = nom;
        this._score = new int[17];
        this._util = new boolean[17];
        for (int i = 0; i < this._score.length; i++){
            this._score[i] = 0;
            this._util[i] = false;
        }
    }

    public String getNom() {
        return _nom;
    }

    public void setNom(String nom) {
        this._nom = nom;
    }

    public int getScore(int index) {
        return _score[index];
    }

    public void setScore(int index, int score) {
        if(!this._util[index]){
            this._score[index] = score;
            this._util[index] = true;
            if(index < 6){
                this.setTotal(6);
                this.setTotal(16);
            }
            else if( index < 11){
                this.setTotal(11);
                this.setTotal(16);
            }
            else if( index < 16){
                this.setTotal(16);
            }
        }
    }
    
    private void setTotal(int index){
        switch(index){
            case 6:
                this._score[index] = 0;
                for(int i = 0; i < 6; i++){
                    this._score[index] += this._score[i];
                }
                if (_score[index] > 59){
                    this._score[7] = 30;
                }
                this._score[8] = this._score[6] + this._score[7];
                break;
            case 11:
                this._score[index] = this._score[9] - this._score[10];
                break;
            case 16:
                this._score[index] = this._score[12] + this._score[13] + this._score[14] + this._score[15] + this._score[8] + this._score[11];
                break;
            default:
                break; //n'arrivera jamais
        }
    }
    
}
