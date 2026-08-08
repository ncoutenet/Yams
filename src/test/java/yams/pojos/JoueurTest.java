package yams.pojos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JoueurTest {

    private Joueur joueur;

    @BeforeEach
    void setUp() {
        joueur = new Joueur("Nicolas", true);
    }

    @Test
    void testGetNom() {
        assertEquals("Nicolas", joueur.getNom());
    }

    @Test
    void testSetNom() {
        joueur.setNom("Autre");
        assertEquals("Autre", joueur.getNom());
    }

    @Test
    void testScoreInitialiseAZero() {
        for (int i = 0; i < 17; i++) {
            assertEquals(0, joueur.getScore(i));
        }
    }

    @Test
    void testSetScoreNEcritQuUneFois() {
        joueur.setScore(0, 3);
        joueur.setScore(0, 99);
        assertEquals(3, joueur.getScore(0));
    }

    @Test
    void testTotalTrancheHauteSansBonus() {
        joueur.setScore(0, 3);
        joueur.setScore(1, 4);
        joueur.setScore(2, 6);
        joueur.setScore(3, 8);
        joueur.setScore(4, 10);
        joueur.setScore(5, 12);

        assertEquals(43, joueur.getScore(6));
        assertEquals(0, joueur.getScore(7));
        assertEquals(43, joueur.getScore(8));
    }

    @Test
    void testTotalTrancheHauteAvecBonus() {
        joueur.setScore(0, 5);
        joueur.setScore(1, 10);
        joueur.setScore(2, 15);
        joueur.setScore(3, 12);
        joueur.setScore(4, 15);
        joueur.setScore(5, 18);

        assertEquals(75, joueur.getScore(6));
        assertEquals(35, joueur.getScore(7));
        assertEquals(110, joueur.getScore(8));
    }

    @Test
    void testTotalGeneralMaxiMini() {
        joueur.setScore(9, 20);
        joueur.setScore(10, 5);
        joueur.setScore(12, 1);
        joueur.setScore(13, 2);
        joueur.setScore(14, 3);
        joueur.setScore(15, 4);

        assertEquals(15, joueur.getScore(11));
        assertEquals(15 + 1 + 2 + 3 + 4, joueur.getScore(16));
    }

    @Test
    void testTotalGeneralSansMaxiMini() {
        Joueur joueurLibre = new Joueur("Autre", false);
        joueurLibre.setScore(9, 20);
        joueurLibre.setScore(10, 5);
        joueurLibre.setScore(12, 1);
        joueurLibre.setScore(13, 2);
        joueurLibre.setScore(14, 3);
        joueurLibre.setScore(15, 4);

        assertEquals(20 + 5 + 1 + 2 + 3 + 4, joueurLibre.getScore(16));
    }
}
