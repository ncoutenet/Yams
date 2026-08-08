package yams.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yams.pojos.Joueur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * myControler est passé à null : seules les méthodes qui ne le déréférencent
 * pas (sortJoueurs, majNbLances, changerJoueur, getTour, lancer) sont testées
 * ici. YamControl n'a pas de constructeur léger utilisable en test (I/O
 * disque + fenêtres Swing), et les méthodes calc1..calcYam ont en plus
 * besoin d'un JeuVue réel (fenêtre Swing complète construite dans son seul
 * constructeur) donc restent hors périmètre.
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

    @Test
    void testGetTourInitialiseAZero() {
        assertEquals(0, yamModele.getTour());
    }

    @Test
    void testChangerJoueurTourneEtBoucle() {
        assertEquals(1, incrementeEtGetTour());
        assertEquals(2, incrementeEtGetTour());
        assertEquals(0, incrementeEtGetTour());
    }

    private int incrementeEtGetTour() {
        yamModele.changerJoueur();
        return yamModele.getTour();
    }

    @Test
    void testLancerRetourneCinqDesEntreUnEtSix() {
        int[] des = yamModele.lancer();

        assertEquals(5, des.length);
        for (int de : des) {
            assertTrue(de >= 1 && de <= 6);
        }
    }
}
