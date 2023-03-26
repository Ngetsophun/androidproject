package com.example.systempos.location;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Location")
public class LocationData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int locID;
    @ColumnInfo
    String locName;
    @ColumnInfo
    String locName1;

    public LocationData() {
    }

    public LocationData(int locID, String locName, String locName1) {
        this.locID = locID;
        this.locName = locName;
        this.locName1 = locName1;
    }

    public int getLocID() {
        return locID;
    }

    public void setLocID(int locID) {
        this.locID = locID;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocName1() {
        return locName1;
    }

    public void setLocName1(String locName1) {
        this.locName1 = locName1;
    }
}
