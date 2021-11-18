package com.example.enigmiam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "EnigMIAM.db";

    // Table name: Note.
    private static final String TABLE_CRITIQUE = "Critique";

    private static final String COLUMN_CRITIQUE_ID ="Note_Id";
    private static final String COLUMN_NOM_RESTAURANT ="Nom_resto";
    private static final String COLUMN_REPAS_DATE_HEURE = "Date_repas";
    private static final String COLUMN_NOTE_DECO = "Note_Deco";
    private static final String COLUMN_NOTE_SERVICE = "Note_Service";
    private static final String COLUMN_DESCRIPTION_CRITIQUE = "Critique_Repas";

    public void  createTable(){

    }

    public MyDataBaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Script to create table.
        String script = "CREATE TABLE " + TABLE_CRITIQUE + "("
                + COLUMN_CRITIQUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOM_RESTAURANT +','+ COLUMN_REPAS_DATE_HEURE+',' + COLUMN_NOTE_DECO +','+ COLUMN_NOTE_SERVICE +','+ COLUMN_DESCRIPTION_CRITIQUE
                + ")";
        // Execute script.
        db.execSQL(script);
        Log.i(TAG, "onCreate: table cree");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRITIQUE);
        // Recreate
        onCreate(db);
    }

    public List<Critique> getAllCritiques() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<Critique> noteList = new ArrayList<Critique>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CRITIQUE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Critique critique = new Critique();
                critique.setIdCritique(Integer.parseInt(cursor.getString(0)));
                critique.setNomResto(cursor.getString(1));
                critique.setDateEtHeure(cursor.getString(2));
                critique.setNoteDeco(cursor.getString(3));
                critique.setNoteService(cursor.getString(4));
                critique.setCritique(cursor.getString(5));

                noteList.add(critique);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }

    public void addCritique(Critique critique) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + critique.getNomResto());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM_RESTAURANT, critique.getNomResto());
        values.put(COLUMN_REPAS_DATE_HEURE, critique.getDateEtHeure());
        values.put(COLUMN_NOTE_DECO, critique.getNoteDeco());
        values.put(COLUMN_NOTE_SERVICE, critique.getNoteService());
        values.put(COLUMN_DESCRIPTION_CRITIQUE, critique.getCritique());

        // Inserting Row
        db.insert(TABLE_CRITIQUE, "0", values);

        // Closing database connection
        db.close();

    }
    public void deleteCritique(String critiqueId) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CRITIQUE, COLUMN_CRITIQUE_ID + " = ?",
                new String[] { String.valueOf(critiqueId) });
        db.close();
    }

    public Critique getCritique(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CRITIQUE, new String[] { COLUMN_CRITIQUE_ID,
                        COLUMN_NOM_RESTAURANT, COLUMN_REPAS_DATE_HEURE,COLUMN_NOTE_DECO, COLUMN_NOTE_SERVICE, COLUMN_DESCRIPTION_CRITIQUE }, COLUMN_CRITIQUE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Critique critique = new Critique();
        critique.setIdCritique(Integer.parseInt(cursor.getString(0)));
        critique.setNomResto(cursor.getString(1));
        critique.setDateEtHeure(cursor.getString(2));
        critique.setNoteDeco(cursor.getString(3));
        critique.setNoteService(cursor.getString(4));
        critique.setCritique(cursor.getString(5));
        // return critique
        return critique;
    }
    public int updateCritique(Critique critique) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM_RESTAURANT, critique.getNomResto());
        values.put(COLUMN_REPAS_DATE_HEURE, critique.getDateEtHeure());
        values.put(COLUMN_NOTE_DECO, critique.getNoteDeco());
        values.put(COLUMN_NOTE_SERVICE, critique.getNoteService());
        values.put(COLUMN_DESCRIPTION_CRITIQUE, critique.getCritique());
        // updating row
        return db.update(TABLE_CRITIQUE, values, COLUMN_CRITIQUE_ID + " = ?",
                new String[]{String.valueOf(critique.getIdCritique())});
    }


// ...



}

