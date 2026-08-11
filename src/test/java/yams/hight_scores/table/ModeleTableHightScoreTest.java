package yams.hight_scores.table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yams.hight_scores.pojos.Score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModeleTableHightScoreTest {

    private ModeleTableHightScore modele;

    @BeforeEach
    void setUp() {
        modele = new ModeleTableHightScore();
    }

    @Test
    void testEtatInitial() {
        assertEquals(0, modele.getRowCount());
        assertEquals(3, modele.getColumnCount());
    }

    @Test
    void testAddScore() {
        Score score = new Score("Nicolas", 250);

        modele.addScore(score);

        assertEquals(1, modele.getRowCount());
        assertEquals("Nicolas", modele.getValueAt(0, 0));
        assertEquals(250, modele.getValueAt(0, 1));
        assertTrue(((String) modele.getValueAt(0, 2)).matches("\\d{2}/\\d{2}/\\d{4}"));
    }

    @Test
    void testGetValueAtColonneHorsLimites() {
        modele.addScore(new Score("Nicolas", 250));

        assertNull(modele.getValueAt(0, 3));
    }

    @Test
    void testDelScores() {
        modele.addScore(new Score("Nicolas", 250));
        modele.addScore(new Score("Autre", 100));

        modele.delScores();

        assertEquals(0, modele.getRowCount());
    }
}
