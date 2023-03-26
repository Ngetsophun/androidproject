package com.example.systempos.Setting;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Setting")
public class SettingData implements Serializable {
    @PrimaryKey(autoGenerate = false)
    int storeID;
    @ColumnInfo
    String storeName;
    @ColumnInfo
    String storeAddress;
    @ColumnInfo
    String storeNumber;
    @ColumnInfo
    double storrExchage;

    @ColumnInfo
    String storeImage;

    public SettingData() {
    }

    public SettingData(int storeID, String storeName, String storeAddress, String storeNumber, double storrExchage, String storeImage) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeNumber = storeNumber;
        this.storrExchage = storrExchage;
        this.storeImage = storeImage;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public double getStorrExchage() {
        return storrExchage;
    }

    public void setStorrExchage(double storrExchage) {
        this.storrExchage = storrExchage;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }
}
