/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

/**
 *
 * @author nicolas
 */
public enum ModeJeu {
    LIBRE("Libre", 3),
    MONTANT("Montant", 3),
    DESCENDANT("Descendant", 3),
    SEC("Sec", 1);

    private final String libelle;
    private final int nombreLances;

    ModeJeu(String libelle, int nombreLances) {
        this.libelle = libelle;
        this.nombreLances = nombreLances;
    }

    @Override
    public String toString() {
        return libelle;
    }

    public int getNombreLances() {
        return nombreLances;
    }
}
