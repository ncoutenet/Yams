package yams.table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ColorTabTest {

    private ColorTab colorTab;

    @BeforeEach
    void setUp() {
        colorTab = new ColorTab(3, 4);
    }

    @Test
    void testSetCouleursBornesValides() {
        assertDoesNotThrow(() -> colorTab.setCouleurs(0, 0, ColorTab.VERT));
        assertDoesNotThrow(() -> colorTab.setCouleurs(2, 3, ColorTab.ROUGE));
    }

    @Test
    void testClearNeLevePasException() {
        colorTab.setCouleurs(1, 1, ColorTab.BLEU);
        assertDoesNotThrow(() -> colorTab.clear());
    }

    @Test
    void testSetCouleursHorsBornesLeveException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> colorTab.setCouleurs(3, 0, ColorTab.GRIS));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> colorTab.setCouleurs(0, 4, ColorTab.GRIS));
    }
}
