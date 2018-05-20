package com.vsevolodsvet.estates.DB;

import android.database.Cursor;

import java.lang.reflect.Field;

public abstract class DatabaseTable {

    public static String[] getFieldNames(Class cl) {

        String names[] = new String[15];

        // добавление всех полей в массив
        //region
        // тут костыль, getDeclaredFields работает некорректно, подлежит исправлению
        names[0] = SQLiteHelper.COLUMN_ID;
        names[1] = SQLiteHelper.ADRESS;
        names[2] = SQLiteHelper.X_COORD;
        names[3] = SQLiteHelper.Y_COORD;
        names[4] = SQLiteHelper.PRICE_M;
        names[5] = SQLiteHelper.PRICE_R;
        names[6] = SQLiteHelper.REGION;
        names[7] = SQLiteHelper.ROOMS;
        names[8] = SQLiteHelper.LEVEL;
        names[9] = SQLiteHelper.LEVEL_AMOUNT;
        names[10] = SQLiteHelper.S_LIVE;
        names[11] = SQLiteHelper.S_ALL;
        names[12] = SQLiteHelper.S_R;
        names[13] = SQLiteHelper.BALCONY;
        names[14] = SQLiteHelper.YEAR;
        //endregion

        return names;
    }

    public static <T> T fromCursor(Class<T> cl, Cursor cursor) {

        try {
            T target = cl.newInstance();

            Field[] fields = cl.getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);

                String fieldName = field.getName();
                int index = cursor.getColumnIndex(fieldName);

                if (index == -1)
                    continue;

                if (field.getType() == String.class) {
                    field.set(target, cursor.getString(index));
                } else if (field.getType() == int.class) {
                    field.set(target, cursor.getInt(index));
                } else if (field.getType() == double.class) {
                    field.set(target, cursor.getDouble(index));
                } else if (field.getType() == long.class) {
                    field.set(target, cursor.getLong(index));
                } else if (field.getType() == boolean.class) {
                    int val = cursor.getInt(index);
                    field.set(target, val == 1);
                } else if (field.getType() == byte[].class) {
                    field.set(target, cursor.getBlob(index));
                }
            }

            return target;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}