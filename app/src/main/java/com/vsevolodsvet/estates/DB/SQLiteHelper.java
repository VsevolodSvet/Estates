package com.vsevolodsvet.estates.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vsevolodsvet.estates.Objects.Estate;

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
    public void AddXLSData (Workbook workbook) {
        //region
        // создаем массив для вытащенных из БД объектов недвижимости
        ArrayList Estates = new ArrayList();

        // выбираем страницу (первую и единственную) документа с данными
        Sheet sheet = workbook.getSheetAt(0);

        // берем данные начиная со второй строки (первая строка - описание полей)
        /** Обязателен верный порядок столбцов таблицы **/
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            // заполняем параметры в известном порядке
            //region
            // это ОГРОМНЫЙ костыль
            long id = Long.valueOf(row.getCell(0).getStringCellValue());
            String adress = row.getCell(1).getStringCellValue();
            Float x_coord = Float.valueOf(row.getCell(2).getStringCellValue());
            Float y_coord = Float.valueOf(row.getCell(3).getStringCellValue());
            Float prive_m = Float.valueOf(row.getCell(4).getStringCellValue());
            Float prive_r = Float.valueOf(row.getCell(5).getStringCellValue());
            String region = row.getCell(6).getStringCellValue();
            Integer rooms = Integer.valueOf(row.getCell(7).getStringCellValue());
            Integer level = Integer.valueOf(row.getCell(8).getStringCellValue());
            Integer level_amount = Integer.valueOf(row.getCell(9).getStringCellValue());
            Float s_live = Float.valueOf(row.getCell(10).getStringCellValue());
            Float s_all = Float.valueOf(row.getCell(11).getStringCellValue());
            Float s_r = Float.valueOf(row.getCell(12).getStringCellValue());
            Integer balcony = Integer.valueOf(row.getCell(13).getStringCellValue());
            String year = row.getCell(14).getStringCellValue();
            //endregion
            Estates.add(new Estate(id, adress, x_coord, y_coord, prive_m, prive_r,
                region, rooms, level, level_amount, s_live, s_all, s_r, balcony, year));
        }

        // добавляем объекты из заполненного массива в БД, при совпадающем id запись ЗАМЕНЯЕТСЯ
        for (Object estate : Estates) {
            estate = (Estate) estate;

        }
        //endregion
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