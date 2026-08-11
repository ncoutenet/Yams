package yams.hight_scores.pojos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreTest {

    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score("Nicolas", 250);
    }

    @Test
    void testGetName() {
        assertEquals("Nicolas", score.getName());
    }

    @Test
    void testSetName() {
        score.setName("Autre");
        assertEquals("Autre", score.getName());
    }

    @Test
    void testGetScore() {
        assertEquals(250, score.getScore());
    }

    @Test
    void testSetScore() {
        score.setScore(300);
        assertEquals(300, score.getScore());
    }

    @Test
    void testGetSetId() {
        score.setId(42);
        assertEquals(42, score.getId());
    }

    @Test
    void testGetDateFormat() {
        assertTrue(score.getDate().matches("\\d{2}/\\d{2}/\\d{4}"));
    }

    @Test
    void testSerialisationConserveLesChamps() throws Exception {
        score.setId(42);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(buffer)){
            oos.writeObject(score);
        }

        Score relu;
        try(ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))){
            relu = (Score) ois.readObject();
        }

        assertEquals(42, relu.getId());
        assertEquals("Nicolas", relu.getName());
        assertEquals(250, relu.getScore());
        assertEquals(score.getDate(), relu.getDate());
    }
}
