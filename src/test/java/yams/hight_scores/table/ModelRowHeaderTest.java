package yams.hight_scores.table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ModelRowHeaderTest {

    private ModelRowHeader modelRowHeader;

    @BeforeEach
    void setUp() {
        modelRowHeader = new ModelRowHeader();
    }

    @Test
    void testGetRowCount() {
        assertEquals(10, modelRowHeader.getRowCount());
    }

    @Test
    void testGetColumnCount() {
        assertEquals(1, modelRowHeader.getColumnCount());
    }

    @Test
    void testGetValueAtColonne0() {
        for (int i = 0; i < 10; i++) {
            assertEquals(String.valueOf(i + 1), modelRowHeader.getValueAt(i, 0));
        }
    }

    @Test
    void testGetValueAtColonneHorsLimites() {
        assertNull(modelRowHeader.getValueAt(0, 1));
    }
}
