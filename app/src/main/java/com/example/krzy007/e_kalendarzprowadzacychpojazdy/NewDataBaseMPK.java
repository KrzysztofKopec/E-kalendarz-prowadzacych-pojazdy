package com.example.krzy007.e_kalendarzprowadzacychpojazdy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Krzyś007 on 2018-03-15.
 */

public class NewDataBaseMPK extends SQLiteOpenHelper {
    private static final String BAZA_DANYCH = "newmpk";
    private static final int BD_VERSION = 1;
    SQLiteDatabase db;

    public NewDataBaseMPK(Context context) {
        super(context, BAZA_DANYCH, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ECALENDAR (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "DATA TEXT, " + "LINIA TEXT, " +"POCZATEK TEXT, " +"KONIEC TEXT, " +"NRPOJAZDU TEXT, " + "GODZINY INTEGER, " +"MINUTY INTEGER, " + "NOTATKA TEXT);");

    }
    public void enteringWorkingDay(String data, String linia, String poczatek, String koniec, String nrPojazdu, int godziny, int minuty, String notatka){
        db = getWritableDatabase();
        ContentValues workDay = new ContentValues();
        workDay.put("DATA", data);
        workDay.put("LINIA", linia);
        workDay.put("POCZATEK", poczatek);
        workDay.put("KONIEC", koniec);
        workDay.put("NRPOJAZDU", nrPojazdu);
        workDay.put("GODZINY", godziny);
        workDay.put("MINUTY", minuty);
        workDay.put("NOTATKA", notatka);
        db.insert("ECALENDAR",null,workDay);
    }
    public void editWorkingDay(int id, String data, String linia, String poczatek, String koniec, String nrPojazdu, int godziny, int minuty, String notatka){
        db = getWritableDatabase();
        ContentValues eWorkDay = new ContentValues();
        eWorkDay.put("DATA", data);
        eWorkDay.put("LINIA", linia);
        eWorkDay.put("POCZATEK", poczatek);
        eWorkDay.put("KONIEC", koniec);
        eWorkDay.put("NRPOJAZDU", nrPojazdu);
        eWorkDay.put("GODZINY", godziny);
        eWorkDay.put("MINUTY", minuty);
        eWorkDay.put("NOTATKA", notatka);
        db.update("ECALENDAR",eWorkDay,"_id = ?",new String[]{Integer.toString(id)});
    }
    public Cursor getHoursMinutes(){
        String[] columns = {"DATA","GODZINY","MINUTY"};
        db = getReadableDatabase();
        return db.query("ECALENDAR", columns ,null,null,null,null,null);
    }
    public Cursor editWork(int nr){
        String[] columns = {"DATA","LINIA","POCZATEK","KONIEC","NRPOJAZDU","GODZINY","MINUTY","NOTATKA"};
        db = getReadableDatabase();
        return db.query("ECALENDAR", columns,"_id = ?",new String[]{Integer.toString(nr)},null,null,null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
