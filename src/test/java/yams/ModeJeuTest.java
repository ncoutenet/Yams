package yams;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModeJeuTest {

    @Test
    void testGetNombreLances() {
        assertEquals(3, ModeJeu.LIBRE.getNombreLances());
        assertEquals(3, ModeJeu.MONTANT.getNombreLances());
        assertEquals(3, ModeJeu.DESCENDANT.getNombreLances());
        assertEquals(1, ModeJeu.SEC.getNombreLances());
    }

    @Test
    void testToStringRetourneLeLibelle() {
        assertEquals("Libre", ModeJeu.LIBRE.toString());
        assertEquals("Montant", ModeJeu.MONTANT.toString());
        assertEquals("Descendant", ModeJeu.DESCENDANT.toString());
        assertEquals("Sec", ModeJeu.SEC.toString());
    }
}
