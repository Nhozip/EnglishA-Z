package com.it.nhozip.englisha_z.Model;

import java.io.Serializable;

/**
 * Created by huyen on 9/14/2016.
 */
public class You implements Serializable {
    private String id;
    private String name;
    private String avata;


    public You(String id, String name, String avata) {
        this.id = id;
        this.name = name;
        this.avata = avata;

    }

    public You() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "You{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avata='" + avata + '\'' +

                '}';
    }
}
