package com.vsevolodsvet.estates.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;

/**
 * Created by Vsevolod on 15.04.2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    // Параметры для БД
    //region
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

    private static final String[] Fields = {
            //region
            COLUMN_ID, ADRESS, X_COORD, Y_COORD,
            PRICE_M, PRICE_R, REGION, ROOMS,
            LEVEL, LEVEL_AMOUNT, S_LIVE, S_ALL,
            S_R, BALCONY, YEAR
            //endregion
    };
    //endregion

    // Скрипт создания таблицы
    //region
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
    //endregion

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // производит добавление в БД содержимое файла XLS
    //region
    public void AddXLSData (Workbook workbook) {
        // выбираем страницу (первую и единственную) документа с данными
        Sheet sheet = workbook.getSheetAt(0);
        // берем данные начиная со второй строки (первая строка - описание полей)
        /** Обязателен верный порядок столбцов таблицы **/
        ArrayList Estates = new ArrayList();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            //читаем столбцы
            for (short j = 0; j < Fields.length; j++) {

            }
        }
        //читаем первое поле (отсчет полей идет с нуля) т.е. по сути читаем второе
        Row row = sheet.getRow(1);
        //читаем столбцы
        Cell name = row.getCell(0);
        Cell age = row.getCell(1);
    }
    //endregion

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_ESTATES);
        /**
        // добавление в множество всех параметров класса
        //region
        fields.add("id");
        fields.add(COLUMN_ID);
        fields.add(ADRESS);
        fields.add(X_COORD);
        fields.add(Y_COORD);
        fields.add(PRICE_M);
        fields.add(PRICE_R);
        fields.add(REGION);
        fields.add(ROOMS);
        fields.add(LEVEL);
        fields.add(LEVEL_AMOUNT);
        fields.add(S_LIVE);
        fields.add(S_ALL);
        fields.add(S_R);
        fields.add(BALCONY);
        fields.add(YEAR);
        //endregion
         **/
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