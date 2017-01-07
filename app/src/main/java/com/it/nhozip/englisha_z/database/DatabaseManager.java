package com.it.nhozip.englisha_z.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.it.nhozip.englisha_z.Model.Move;
import com.it.nhozip.englisha_z.Model.Note;

import java.util.ArrayList;

/**
 * Created by Nhozip on 4/25/2016.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "move.db";

    public static final String TABLE_MOVE = "tb_move";
    public static final String TABLE_NOTE = "tb_note";

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_IMG = "thumbi_move";
    public static final String COL_ACTOR = "actor";
    public static final String COL_TITLE = "title";
    public static final String COL_CONTENT = "content";


    public static final int DATA_VERSION = 1;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TB_MOVE =
                "CREATE TABLE " + TABLE_MOVE + "(" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                        ", " + COL_NAME + " TEXT NOT NULL" +
                        "," + COL_IMG + " TEXT NOT NULL" + "," + COL_ACTOR +
                        " TEXT NOT NULL" +
                        ")";

        String TB_NOTE =
                "CREATE TABLE " + TABLE_NOTE + "(" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                        ", " + COL_IMG + " TEXT " +
                        "," + COL_CONTENT + " TEXT " +
                        ")";
        db.execSQL(TB_MOVE);
        db.execSQL(TB_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void open() {
        try {
            database = getWritableDatabase();
        } catch (Exception e) {
            Log.e("open", e + "");
        }

    }

    public void close() {
        if (database != null && database.isOpen()) {
            try {
                database.close();
            } catch (Exception e) {
                Log.e("close", e + "");
            }
        }
    }
    public long insertNote(Note note) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_IMG, note.getImg_id());
        values.put(COL_CONTENT, note.getContent());
        long i = database.insert(TABLE_NOTE, null, values);
        close();
        return i;
    }


    public long insertMovve(Move food) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, food.getName());
        values.put(COL_IMG, food.getThumbi_move());
        values.put(COL_ACTOR, food.getActor());
        long i = database.insert(TABLE_MOVE, null, values);
        close();
        return i;
    }

    public void dellAll(){
        open();
        database.execSQL("delete from "+ TABLE_MOVE);
        close();
    }
    public long dellNote(String img) {
        open();
        long i = 0;
        try {
            i = database.delete(TABLE_MOVE, COL_IMG + "=?", new String[]{img + ""});
            close();
        } catch (Exception e) {
            Log.e("del", "obj ko ton tai");
        }

        return i;
    }

    public long dellNotee(String img) {
        open();
        long i = 0;
        try {
            i = database.delete(TABLE_NOTE, COL_IMG + "=?", new String[]{img + ""});
            close();
        } catch (Exception e) {
            Log.e("del", "obj ko ton tai");
        }

        return i;
    }


    public ArrayList<Object> getListMove() {
        open();
        ArrayList<Object> listFood = new ArrayList<>();
        Cursor cursor = database.query(TABLE_MOVE, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Move move = new Move();
            move.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
            move.setThumbi_move(cursor.getString(cursor.getColumnIndex(COL_IMG)));
            move.setActor(cursor.getString(cursor.getColumnIndex(COL_ACTOR)));
            listFood.add(move);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listFood;
    }
    public ArrayList<Object> getListNote() {
        open();
        ArrayList<Object> listFood = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NOTE, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note move = new Note();
            move.setImg_id(cursor.getString(cursor.getColumnIndex(COL_IMG)));
            move.setContent(cursor.getString(cursor.getColumnIndex(COL_CONTENT)));
            listFood.add(move);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listFood;
    }

    public Move getNot(String img) {
        open();
        Cursor cursor = database.rawQuery("SELECT *FROM " + TABLE_MOVE + " WHERE " + COL_IMG + " = " + img, null);
        cursor.moveToFirst();
        Move move = new Move();
        move.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
        move.setThumbi_move(cursor.getString(cursor.getColumnIndex(COL_IMG)));
        move.setActor(cursor.getString(cursor.getColumnIndex(COL_ACTOR)));
        close();
        return move;
    }

    public String getNotIMG(String img) {
        open();
        String[] args = {img};
        String link_img;
        Cursor cursor = database.rawQuery("SELECT * FROM tb_move WHERE thumbi_move = ?", args);
        try {
            cursor.moveToFirst();
            link_img = cursor.getString(2);
        } catch (Exception e) {
            link_img = "XXX";
        }
        close();
        return link_img;
    }
    public long updateNote(Note note) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, note.getImg_id());
        values.put(COL_CONTENT, note.getContent());

        long i = database.update(TABLE_NOTE, values, COL_IMG + "=?,", new String[]{note.getImg_id() + ""});
        close();
        return i;
    }
    public String getNote(String img) {
        open();
        String[] args = {img};
        String note;
        Cursor cursor = database.rawQuery("SELECT * FROM tb_note WHERE thumbi_move = ?", args);
        try {
            cursor.moveToFirst();
            note = cursor.getString(2);
        } catch (Exception e) {
            note = " ";
        }
        close();
        return note;
    }

}
