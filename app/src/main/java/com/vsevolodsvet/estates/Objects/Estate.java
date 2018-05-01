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
    private Float prive_m;
    private Float prive_r;
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

    public void setPriveM(Float prive_m) {
        this.prive_m = prive_m;
    }

    public void setPriveR(Float prive_r) {
        this.prive_r = prive_r;
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
        this.prive_r = prive_r;
    }

    public void setS_All(Float s_all) {
        this.prive_r = prive_r;
    }

    public void setS_R(Float s_r) {
        this.prive_r = prive_r;
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

    public Float getPrive_m() {
        return prive_m;
    }

    public Float getPrive_r() {
        return prive_r;
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
        if (!Float.valueOf(this.prive_m).equals(null)) EstateMap.put("prive_m", this.prive_m);
        if (!Float.valueOf(this.prive_r).equals(null)) EstateMap.put("prive_r", this.prive_r);
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

    public Estate(String s){

    }

    @Override
    public String toString() {
        return this.id + ':' + this.adress;
    }
}