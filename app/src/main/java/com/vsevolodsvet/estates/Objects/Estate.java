package com.vsevolodsvet.estates.Objects;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vsevolodsvet.estates.DB.SQLiteHelper;
import com.vsevolodsvet.estates.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Estate {

    // Блок параметров объекта
    //region
    private long id;
    private String adress;
    private Float x_coord;
    private Float y_coord;
    private Float price_m;
    private Float price_r;
    private String region;
    private Integer rooms;
    private Integer level;
    private Integer level_amount;
    private Float s_live;
    private Float s_all;
    private Float s_r;
    private Integer balcony;
    private Date year;
    //endregion

    // Блок методов задания значений параметров
    //region

    public void setId(long id) {
        this.id = id;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setX_coord(Float x_coord) {
        this.x_coord = x_coord;
    }

    public void setY_coord(Float y_coord) {
        this.y_coord = y_coord;
    }

    public void setPriceM(Float price_m) {
        this.price_m = price_m;
    }

    public void setPriceR(Float price_r) {
        this.price_r = price_r;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setLevelAmount(Integer level_amount) {
        this.level_amount = level_amount;
    }

    public void setS_Live(Float s_live) {
        this.s_live = s_live;
    }

    public void setS_All(Float s_all) {
        this.s_all = s_all;
    }

    public void setS_R(Float s_r) {
        this.s_r = s_r;
    }

    public void setBalcony(Integer balcony) {
        this.rooms = rooms;
    }

    public void setYear(String year) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy.MM.dd");
        this.year = format.parse(year);
    }

    //endregion

    // Блок методов получения значений параметров
    //region
    public long getId() {
        return id;
    }
    public String getAdress() {
        return adress;
    }

    public Float getX_coord() {
        return x_coord;
    }

    public Float getY_coord() {
        return y_coord;
    }

    public Float getPrice_m() {
        return price_m;
    }

    public Float getPrice_r() {
        return price_r;
    }

    public String getRegion() {
        return region;
    }

    public Integer getRooms() {
        return rooms;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getLevel_amount() {
        return level_amount;
    }

    public Float getS_live() {
        return s_live;
    }

    public Float getS_all() {
        return s_all;
    }

    public Float getS_r() {
        return s_r;
    }

    public Integer getBalcony() {
        return balcony;
    }

    public Date getYear() {
        return year;
    }

    public HashMap<String, Object> getAll() {
        HashMap<String, Object> EstateMap = new HashMap<String, Object>();
        // получение параметров и добавление их в мапу при условии не null
        //region
        if (!Long.valueOf(this.id).equals(null)) EstateMap.put("id", this.id);
        if (!String.valueOf(this.adress).equals(null)) EstateMap.put("adress", this.adress);
        if (!Float.valueOf(this.x_coord).equals(null)) EstateMap.put("x_coord", this.x_coord);
        if (!Float.valueOf(this.y_coord).equals(null)) EstateMap.put("y_coord", this.y_coord);
        if (!Float.valueOf(this.price_m).equals(null)) EstateMap.put("prive_m", this.price_m);
        if (!Float.valueOf(this.price_r).equals(null)) EstateMap.put("prive_r", this.price_r);
        if (!String.valueOf(this.region).equals(null)) EstateMap.put("region", this.region);
        if (!Integer.valueOf(this.rooms).equals(null)) EstateMap.put("rooms", this.rooms);
        if (!Integer.valueOf(this.level).equals(null)) EstateMap.put("level", this.level);
        if (!Integer.valueOf(this.level_amount).equals(null)) EstateMap.put("level_amount", this.level_amount);
        if (!Float.valueOf(this.s_live).equals(null)) EstateMap.put("s_live", this.s_live);
        if (!Float.valueOf(this.s_all).equals(null)) EstateMap.put("s_all", this.s_all);
        if (!Float.valueOf(this.s_r).equals(null)) EstateMap.put("s_r", this.s_r);
        if (!Integer.valueOf(this.balcony).equals(null)) EstateMap.put("balcony", this.balcony);
        if (this.year != null) EstateMap.put("year", this.year);
        //endregion
        return EstateMap;
    }
    // endregion

    // Для создания объекта
    public Estate() {
        SQLiteDatabase db = MainActivity.getDBHelper().getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(_id) FROM " + SQLiteHelper.TABLE_ESTATES, null);
        id = c.getInt(0); // надо проверить, с 0 или 1 считаются столбцы
        id++;
        // создаем новый объект Estate с id на 1 больше максимального в БД (чтобы гарантировать его уникальность)
    }

    // Для создания объекта со всеми параметрами
    public Estate(long id, String adress, Float x_coord, Float y_coord, Float price_m, Float price_r,
                  String region, Integer rooms, Integer level, Integer level_amount, Float s_live,
                  Float s_all, Float s_r, Integer balcony, String year) {
        // все сеттеры
        //region
        this.setId(id);
        this.setAdress(adress);
        this.setX_coord(x_coord);
        this.setY_coord(y_coord);
        this.setPriceM(price_m);
        this.setPriceR(price_r);
        this.setRegion(region);
        this.setRooms(rooms);
        this.setLevel(level);
        this.setLevelAmount(level_amount);
        this.setS_Live(s_live);
        this.setS_All(s_all);
        this.setS_R(s_r);
        this.setBalcony(balcony);
        try {
            this.setYear(year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //endregion
    }

    @Override
    public String toString() {
        return this.id + ':' + this.adress;
    }
}