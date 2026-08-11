package yams.table;

import org.junit.jupiter.api.Test;
import yams.pojos.Joueur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ModeleTableScoreTest {

    @Test
    void testGetColumnCountEtNomsAvecRegles() {
        ModeleTableScore modele = new ModeleTableScore(true);

        assertEquals(18, modele.getColumnCount());
        assertEquals("Joueur", modele.getColumnName(0));
        assertEquals("suite", modele.getColumnName(13));
        assertEquals("carré", modele.getColumnName(15));
        assertEquals("yam's", modele.getColumnName(16));
    }

    @Test
    void testGetColumnCountEtNomsSansRegles() {
        ModeleTableScore modele = new ModeleTableScore(false);

        assertEquals(18, modele.getColumnCount());
        assertEquals("brelan", modele.getColumnName(10));
        assertEquals("petite suite", modele.getColumnName(11));
        assertEquals("grande suite", modele.getColumnName(12));
        assertEquals("chance", modele.getColumnName(16));
    }

    @Test
    void testAddJoueurEtGetJoueur() {
        ModeleTableScore modele = new ModeleTableScore(true);
        Joueur joueur = new Joueur("Nicolas", true);

        modele.addJoueur(joueur);

        assertEquals(1, modele.getRowCount());
        assertEquals(joueur, modele.getJoueur(0));
    }

    @Test
    void testGetValueAt() {
        ModeleTableScore modele = new ModeleTableScore(true);
        Joueur joueur = new Joueur("Nicolas", true);
        joueur.setScore(0, 5);
        modele.addJoueur(joueur);

        assertEquals("Nicolas", modele.getValueAt(0, 0));
        assertEquals(5, modele.getValueAt(0, 1));
        assertNull(modele.getValueAt(0, 18));
    }

    @Test
    void testSetScoreJoueur() {
        ModeleTableScore modele = new ModeleTableScore(true);
        Joueur joueur = new Joueur("Nicolas", true);
        modele.addJoueur(joueur);

        modele.setScoreJoueur(0, 2, 6);

        assertEquals(6, modele.getJoueur(0).getScore(2));
    }
}
