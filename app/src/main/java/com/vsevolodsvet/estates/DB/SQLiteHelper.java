package com.vsevolodsvet.estates.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by Vsevolod on 15.04.2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_ESTATES = "Estates";

    public static final String COLUMN_ID = "_id";
    public static final String ADRESS = "adress";
    public static final String X_COORD = "x_gcoordinate";
    public static final String Y_COORD = "y_gcoordinate";
    public static final String PRICE_M = "Price_m2";
    public static final String PRICE_R = "Price_rub";
    public static final String REGION = "Region";
    public static final String ROOMS = "Rooms";
    public static final String LEVEL = "Level";
    public static final String LEVEL_AMOUNT = "Level_amount";
    public static final String S_LIVE  = "S_live";
    public static final String S_ALL = "S_all";
    public static final String S_R  = "S_r";
    public static final String BALCONY = "Balcony";
    public static final String YEAR = "Year";

    private static final String DATABASE_NAME = "estates.db";
    private static final int DATABASE_VERSION = 1;

    // Скрипт создания таблицы
    private static final String DATABASE_CREATE_ESTATES = "create table "
            + TABLE_ESTATES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + ADRESS
            + " text, " + X_COORD
            + " real, " + Y_COORD
            + " real, " + PRICE_M
            + " real, " + PRICE_R
            + " real not null, " + REGION
            + " text not null, " + ROOMS
            + " integer not null, " + LEVEL
            + " integer not null, " + LEVEL_AMOUNT
            + " integer not null, " + S_LIVE
            + " real not null, " + S_ALL
            + " real not null, " + S_R
            + " real not null, " + BALCONY
            + " integer not null, " + YEAR // можно как количество, а можно просто наличие/отсутствие (в SQLite нету Boolean)
            + " text not null);"; // т.к. в SQLite нету типа даты. Сохранять в виде YYYY-MM-DD HH:MM:SS.SSS

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // производит добавление в БД содержимое файла XLS
    public void AddXLSData (Workbook workbook) {

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_ESTATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTATES);
        onCreate(db);
    }
}