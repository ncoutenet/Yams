package yams.hightScores.pojos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Fixture de test : reproduit à l'identique l'ancienne forme de yams.hight_scores.pojos.Score
 * (paquet yams.hightScores et champs préfixés _) telle qu'elle existait avant le renommage,
 * afin de générer en mémoire des flux sérialisés au format historique et vérifier la
 * rétrocompatibilité de lecture des .dat déjà présents chez les joueurs.
 */
@SuppressWarnings({"java:S116", "java:S117"})
public class Score implements Serializable {
    private int _id;
    private String _name;
    private int _score;
    private Date _date;

    public Score(String name, int score) {
        this._name = name;
        this._score = score;
        this._date = new Date();
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getScore() {
        return _score;
    }

    public void setScore(int score) {
        this._score = score;
    }

    public String getDate(){
        try{
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            return formater.format(this._date);
        } catch(NullPointerException e){
            return new String();
        }
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }
}
