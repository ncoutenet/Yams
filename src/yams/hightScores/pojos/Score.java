/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hightScores.pojos;

/**
 *
 * @author Nicolas
 */
public class Score {
    private String _name;
    private int _score;

    public Score(String name, int score) {
        this._name = name;
        this._score = score;
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
}
