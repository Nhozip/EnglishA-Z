package com.it.nhozip.englisha_z.Model;

import java.io.Serializable;

/**
 * Created by huyen on 12/23/2016.
 */

public class Move implements Serializable{
    private  String thumbi_move;
    private  String name;

    public Move(String thumbi_move, String name, String note, String actor) {
        this.thumbi_move = thumbi_move;
        this.name = name;
        this.note = note;
        this.actor = actor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String note;

    private  String actor;


    public Move() {
    }

    public String getThumbi_move() {
        return thumbi_move;
    }

    public void setThumbi_move(String thumbi_move) {
        this.thumbi_move = thumbi_move;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "Move{" +
                "thumbi_move='" + thumbi_move + '\'' +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", actor='" + actor + '\'' +
                '}';
    }
}
