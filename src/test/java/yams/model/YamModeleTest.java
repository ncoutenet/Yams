package yams.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yams.pojos.Joueur;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * myControler est passé à null : seules les méthodes qui ne le déréférencent
 * pas (sortJoueurs, majNbLances) sont testées ici. YamControl n'a pas de
 * constructeur léger utilisable en test (I/O disque + fenêtres Swing).
 */
class YamModeleTest {

    private YamModele yamModele;

    @BeforeEach
    void setUp() {
        yamModele = new YamModele(3, null);
    }

    @Test
    void testMajNbLancesRepartATrois() {
        assertEquals(3, yamModele.majNbLances(0));
    }

    @Test
    void testMajNbLancesDecremente() {
        assertEquals(2, yamModele.majNbLances(3));
        assertEquals(1, yamModele.majNbLances(2));
        assertEquals(0, yamModele.majNbLances(1));
    }

    @Test
    void testSortJoueursTrieParScoreCroissant() {
        Joueur joueur1 = new Joueur("Premier", true);
        Joueur joueur2 = new Joueur("Deuxieme", true);
        Joueur joueur3 = new Joueur("Troisieme", true);

        joueur1.setScore(12, 50);
        joueur2.setScore(12, 10);
        joueur3.setScore(12, 30);

        Joueur[] tries = yamModele.sortJoueurs(new Joueur[]{joueur1, joueur2, joueur3});

        assertEquals("Deuxieme", tries[0].getNom());
        assertEquals("Troisieme", tries[1].getNom());
        assertEquals("Premier", tries[2].getNom());
    }
}
