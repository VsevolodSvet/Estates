package com.vsevolodsvet.estates.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vsevolodsvet.estates.Objects.Estate;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

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

    public SQLiteDatabase mCurrentDB;
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

    // Скрипт заполнения таблицы (для теста)
    //region
    private static final String DATABASE_FILL_ESTATES = "create table "
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

    public List<Estate> getEstates() {
        return getEstates(null);
    }

    public synchronized List<Estate> getEstates(String where) {
        String columns[] = Estate.getFieldNames();

        List<Estate> tasks = new ArrayList<Estate>();

        Cursor cursor = this.getWritableDatabase().query(TABLE_ESTATES, columns, where,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Estate est = Estate.cursorToEstate(cursor);
                tasks.add(est);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return tasks;

    }

    public Estate getEstate(int estateId) {
        List<Estate> estates = getEstates("id=" + estateId);

        if (estates.size() > 0) {
            return estates.get(0);
        }

        return null;
    }

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // производит добавление в БД содержимое файла XLS
    public String AddXLSData (Workbook workbook, SQLiteDatabase database) {
        //region
        int added = 0;
        int updated = 0;
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
            long id = (long) Double.parseDouble(getXLSCellValueAsString(row.getCell(0)));
            String adress = getXLSCellValueAsString(row.getCell(1));
            Double x_coord = Double.parseDouble(getXLSCellValueAsString(row.getCell(2)));
            Double y_coord = Double.parseDouble(getXLSCellValueAsString(row.getCell(3)));
            Double price_m = Double.parseDouble(getXLSCellValueAsString(row.getCell(4)));
            Double price_r = Double.parseDouble(getXLSCellValueAsString(row.getCell(5)));
            String region = getXLSCellValueAsString(row.getCell(6));
            Integer rooms = (int) Double.parseDouble(getXLSCellValueAsString(row.getCell(7)));
            Integer level = (int) Double.parseDouble(getXLSCellValueAsString(row.getCell(8)));
            Integer level_amount = (int) Double.parseDouble(getXLSCellValueAsString(row.getCell(9)));
            Double s_live = Double.parseDouble(getXLSCellValueAsString(row.getCell(10)));
            Double s_all = Double.parseDouble(getXLSCellValueAsString(row.getCell(11)));
            Double s_r = Double.parseDouble(getXLSCellValueAsString(row.getCell(12)));
            Integer balcony = (int) Double.parseDouble(getXLSCellValueAsString(row.getCell(13)));
            String year = getXLSCellValueAsString(row.getCell(14));
            //endregion
            Estates.add(new Estate(id, adress, x_coord, y_coord, price_m, price_r,
                region, rooms, level, level_amount, s_live, s_all, s_r, balcony, year));
        }

        // добавляем объекты из заполненного массива в БД, при совпадающем id запись ЗАМЕНЯЕТСЯ
        for (Object estate : Estates) {
            //region
            // проверяем на совпадение id добавляемого объекта с id в БД
            Estate est = (Estate) estate;
            long currentId = est.getId();
            Cursor c = database.query(TABLE_ESTATES, null, COLUMN_ID+"='"+currentId+"'",
                    null, null, null, null);
            if (c.getCount() != 0){
                // обновляем запись с нужным id
                //region
                updated++;
                database.execSQL("UPDATE "+TABLE_ESTATES+" SET "
                        +ADRESS+" = '"+est.getAdress()+"', "
                        +X_COORD+" = "+est.getX_coord()+", "
                        +Y_COORD+" = "+est.getY_coord()+", "
                        +PRICE_M+" = "+est.getPrice_m()+", "
                        +PRICE_R+" = "+est.getPrice_r()+", "
                        +REGION+" = '"+est.getRegion()+"', "
                        +ROOMS+" = "+est.getRooms()+", "
                        +LEVEL+" = "+est.getLevel()+", "
                        +LEVEL_AMOUNT+" = "+est.getLevel_amount()+", "
                        +S_LIVE+" = "+est.getS_live()+", "
                        +S_ALL+" = "+est.getS_all()+", "
                        +S_R+" = "+est.getS_r()+", "
                        +BALCONY+" = "+est.getBalcony()+", "
                        +YEAR+" = '"+est.getYear()+"' "
                        +" WHERE "+COLUMN_ID+" = "+currentId);
                //endregion
            } else {
                // либо создаем новую
                //region
                database.execSQL("INSERT INTO "+TABLE_ESTATES+" VALUES ("
                        +currentId+", "
                        +"'"+est.getAdress()+"', "
                        +est.getX_coord()+", "
                        +est.getY_coord()+", "
                        +est.getPrice_m()+", "
                        +est.getPrice_r()+", "
                        +"'"+est.getRegion()+"', "
                        +est.getRooms()+", "
                        +est.getLevel()+", "
                        +est.getLevel_amount()+", "
                        +est.getS_live()+", "
                        +est.getS_all()+", "
                        +est.getS_r()+", "
                        +est.getBalcony()+", "
                        +"'"+est.getYear()+"'"
                        +")");
                added++;
                //endregion
            }
            //endregion
        }

        String message = "Успешно добавлено " + added;
        if (updated != 0){
            message = message + " и обновлено " + updated;
        }
        message += " записей!";
        return message;
        //endregion
    }

    // метод создан для подстраховки от неверного определения типа ячеек в документе
    public String getXLSCellValueAsString(Cell cell) {
        //region
        CellType cellType = cell.getCellTypeEnum();
        if (cellType.equals(CellType.STRING)) {
            return cell.getStringCellValue();
        } else if (cellType.equals(CellType.NUMERIC)) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return "ОШИБКА ОПРЕДЕЛЕНИЯ ТИПА ЯЧЕЙКИ!";
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