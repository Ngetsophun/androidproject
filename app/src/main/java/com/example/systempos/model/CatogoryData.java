package com.example.systempos.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Catogory")
public class CatogoryData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int  id;
    @ColumnInfo
    String catoname;

    @ColumnInfo
    String catoname1;

    public CatogoryData() {
    }

    public CatogoryData(int id, String catoname, String catoname1) {
        this.id = id;
        this.catoname = catoname;
        this.catoname1 = catoname1;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatoname() {
        return catoname;
    }

    public void setCatoname(String catoname) {
        this.catoname = catoname;
    }

    public String getCatoname1() {
        return catoname1;
    }

    public void setCatoname1(String catoname1) {
        this.catoname1 = catoname1;
    }


}
