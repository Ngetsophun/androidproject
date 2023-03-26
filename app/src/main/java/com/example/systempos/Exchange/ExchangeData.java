package com.example.systempos.Exchange;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Exchage")
public class ExchangeData implements Serializable {
    @PrimaryKey(autoGenerate = false)
    int id;

    @ColumnInfo
    String exchageMoney;
    @ColumnInfo
    String exchageDesc;

    public ExchangeData() {
    }

    public ExchangeData(int id, String exchageMoney, String exchageDesc) {
        this.id = id;
        this.exchageMoney = exchageMoney;
        this.exchageDesc = exchageDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExchageMoney() {
        return exchageMoney;
    }

    public void setExchageMoney(String exchageMoney) {
        this.exchageMoney = exchageMoney;
    }

    public String getExchageDesc() {
        return exchageDesc;
    }

    public void setExchageDesc(String exchageDesc) {
        this.exchageDesc = exchageDesc;
    }
}
