package com.francescocommisso.sitemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class DBHandler extends SQLiteOpenHelper {

    private static DBHandler instance = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sites.db";
    private static ArrayList<Site> sites;
    private static ArrayList<Lot> lots;
    public static final String TABLE_SITES = "sites";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SITE_NAME = "siteName";
    public static final String COLUMN_SITE_FORMATTED_NAME = "formattedName";
    public static final String COLUMN_NUMBER_OF_LOTS = "numberOfLots";
    public static final String COLUMN_LOT_ID = "_id";
    public static final String COLUMN_STATUS = "status";

    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHandler getInstance(Context context){

        if(instance == null){
            instance = new DBHandler(context);
            sites = new ArrayList<>();
            lots = new ArrayList<>();
            instance.setSites(instance.getWritableDatabase());
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_SITES + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SITE_NAME + " TEXT, " + COLUMN_SITE_FORMATTED_NAME + " TEXT," +  COLUMN_NUMBER_OF_LOTS + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(Site s:this.getSites()){
            db.execSQL("DROP TABLE IF EXISTS " + s.getFormattedName());
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITES);
        onCreate(db);
    }

    public void addSite(Site site){
        //ADDS SITE TO TABLE OF SITES
        ContentValues values = new ContentValues();
        values.put(COLUMN_SITE_NAME,site.getName());
        values.put(COLUMN_SITE_FORMATTED_NAME,site.getFormattedName());
        values.put(COLUMN_NUMBER_OF_LOTS,site.getTotalLots());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SITES, null, values);

        //CREATES LOT TABLE FOR NEWLY ADDED SITE
        ContentValues values1 = new ContentValues();
        String query = "CREATE TABLE " + site.getFormattedName() + " ( " + COLUMN_LOT_ID + " INTEGER, " + COLUMN_STATUS + " INTEGER );";
        db.execSQL(query);
        for (Lot lot:site.getLots()){
            values1.put(COLUMN_LOT_ID,lot.getId());
            values1.put(COLUMN_STATUS,lot.getStatus());
            db.insert(site.getFormattedName(),null,values1);
        }
        db.close();
        setSites(this.getWritableDatabase());
    }

    public boolean updateLotStatus(String formattedName, int lotNumber,int status){
        ContentValues updatedValues = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        String lotNumberString = String.valueOf(lotNumber);
        updatedValues.put(COLUMN_STATUS,status);
        db.update(formattedName,updatedValues,COLUMN_ID + " = " + lotNumberString,null);
        db.close();
        System.out.println(dbToString(formattedName));
        return true;
    }

    public void deleteSite(Site site){
        String formattedName = site.getFormattedName();
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SITES + " WHERE " + COLUMN_SITE_FORMATTED_NAME + " = \"" + formattedName + "\"");
        db.execSQL("DROP TABLE IF EXISTS " + formattedName);
        setSites(this.getWritableDatabase());
    }

    public String dbToString(String siteName){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query;

        if(siteName == null) {
             query = "SELECT * FROM " + TABLE_SITES + " WHERE 1";
        }

        else{
             query = "SELECT * FROM " + siteName + " WHERE 1";
        }

        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst() ){
            String[] columnNames = c.getColumnNames();
            do {
                for (String name: columnNames) {
                    dbString += String.format("%s: %s\n", name,
                            c.getString(c.getColumnIndex(name)));
                }
                dbString += "\n";

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return dbString;
    }

    public void setSites(SQLiteDatabase db){
        String query = "SELECT * FROM " + TABLE_SITES + " WHERE 1";
        sites.clear();
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst()) {
            do {
                String name = c.getString(c.getColumnIndex(COLUMN_SITE_NAME));
                int numberOfLots = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_NUMBER_OF_LOTS)));
                sites.add(new Site(name, numberOfLots));

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        Collections.reverse(sites);
    }


    public ArrayList<Lot> getLots(SQLiteDatabase db,String formattedName){
        String query = "SELECT * FROM " + formattedName + " WHERE 1";
        lots.clear();

        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()) {
            do {
                int id = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_ID)));
                int status = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_STATUS)));
                lots.add(new Lot(id, status));

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return lots;
    }

    public ArrayList<Site> getSites(){
        return sites;
    }

    public boolean checkExists(String name){
        setSites(this.getWritableDatabase());

        for(Site s:sites){
            if(s.getName().equalsIgnoreCase(name)) return true;
        }

        return false;
    }


    public void getTables(DBHandler dbh,Context context){
        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                Toast.makeText(context, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
                c.moveToNext();
            }
        }
        c.close();
        db.close();
    }

}
