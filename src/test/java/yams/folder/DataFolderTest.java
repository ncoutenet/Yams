package yams.folder;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class DataFolderTest {

    /*
     * Sérialise une liste de scores avec l'ancienne forme de la classe (paquet yams.hightScores.pojos,
     * champs _id/_name/_score/_date), pour reproduire fidèlement le contenu des .dat déjà présents
     * chez les joueurs avant le renommage de paquet et de champs.
     */
    private byte[] serialiserScoresAuFormatHistorique() throws IOException {
        yams.hightScores.pojos.Score ancienScore = new yams.hightScores.pojos.Score("Nicolas", 250);
        ancienScore.setId(42);

        List<yams.hightScores.pojos.Score> scores = new ArrayList<>();
        scores.add(ancienScore);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(buffer)){
            oos.writeObject(scores);
        }
        return buffer.toByteArray();
    }

    @Test
    void testLectureDunDatAuFormatHistoriqueRetrouveLesScores() throws Exception {
        byte[] flux = serialiserScoresAuFormatHistorique();

        List<yams.hight_scores.pojos.Score> scores;
        try(DataFolder.ScoreCompatibleObjectInputStream loader =
                new DataFolder.ScoreCompatibleObjectInputStream(new ByteArrayInputStream(flux))){
            @SuppressWarnings("unchecked")
            List<yams.hight_scores.pojos.Score> resultat = (List<yams.hight_scores.pojos.Score>) loader.readObject();
            scores = resultat;
        }

        assertEquals(1, scores.size());
        yams.hight_scores.pojos.Score score = scores.get(0);
        assertInstanceOf(yams.hight_scores.pojos.Score.class, score);
        assertEquals(42, score.getId());
        assertEquals("Nicolas", score.getName());
        assertEquals(250, score.getScore());
    }
}