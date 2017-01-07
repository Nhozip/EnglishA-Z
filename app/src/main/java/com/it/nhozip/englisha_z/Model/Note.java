package com.it.nhozip.englisha_z.Model;

/**
 * Created by huyen on 1/3/2017.
 */

public class Note {
    private  String img_id;
    private String content;

    public Note() {
    }

    public Note(String img_id, String content) {
        this.img_id = img_id;
        this.content = content;
    }


    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Note{" +
                "img_id='" + img_id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
