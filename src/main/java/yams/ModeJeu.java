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
    LIBRE("Libre"),
    MONTANT("Montant"),
    DESCENDANT("Descendant");

    private final String libelle;

    ModeJeu(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
