package yams.folder;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyFileFilterTest {

    @Test
    void testConstructeurLeveExceptionSiDescriptionNull() {
        assertThrows(NullPointerException.class, () -> new MyFileFilter(null, "csv"));
    }

    @Test
    void testConstructeurLeveExceptionSiExtensionNull() {
        assertThrows(NullPointerException.class, () -> new MyFileFilter("Fichiers CSV", null));
    }

    @Test
    void testGetDescription() {
        MyFileFilter filtre = new MyFileFilter("Fichiers CSV", "csv");

        assertEquals("Fichiers CSV", filtre.getDescription());
    }

    @Test
    void testAcceptRepertoire() {
        MyFileFilter filtre = new MyFileFilter("Fichiers CSV", "csv");

        assertTrue(filtre.accept(new File(System.getProperty("java.io.tmpdir"))));
    }

    @Test
    void testAcceptExtensionCorrespondanteInsensibleALaCasse() {
        MyFileFilter filtre = new MyFileFilter("Fichiers CSV", "csv");

        assertTrue(filtre.accept(new File("scores.csv")));
        assertTrue(filtre.accept(new File("SCORES.CSV")));
    }

    @Test
    void testAcceptExtensionNonCorrespondante() {
        MyFileFilter filtre = new MyFileFilter("Fichiers CSV", "csv");

        assertFalse(filtre.accept(new File("scores.xml")));
        assertFalse(filtre.accept(new File("scores")));
    }
}
