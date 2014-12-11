/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hightScores.pojos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Nicolas
 */
public class Score implements Serializable{
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
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        return formater.format(this._date);
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }
}
