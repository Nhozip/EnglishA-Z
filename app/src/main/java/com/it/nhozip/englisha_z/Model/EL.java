package com.it.nhozip.englisha_z.Model;

/**
 * Created by huyen on 1/1/2017.
 */

public class EL {
    private String key;
    private  String name;

    public EL(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EL{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
