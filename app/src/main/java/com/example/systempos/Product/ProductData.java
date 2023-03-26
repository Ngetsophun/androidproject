package com.example.systempos.Product;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Product")
public class ProductData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int ProID;
    @ColumnInfo
    String CatoID;

    String LocID;

    @ColumnInfo
    String ProBarcode;

    @ColumnInfo
    String Proname;

    @ColumnInfo
    String PronameKh;

    @ColumnInfo
    int Proqty;

    @ColumnInfo
    double Procost;

    @ColumnInfo
    double Proprice;

    @ColumnInfo
    double Protax;

    @ColumnInfo
    String Image;
    @ColumnInfo
    String date;

    public ProductData() {
    }

    public ProductData( String catoID, String locID, String proBarcode, String proname, String pronameKh, int proqty, double procost, double proprice, double protax, String image, String date) {

        CatoID = catoID;
        LocID = locID;
        ProBarcode = proBarcode;
        Proname = proname;
        PronameKh = pronameKh;
        Proqty = proqty;
        Procost = procost;
        Proprice = proprice;
        Protax = protax;
        Image = image;
        this.date = date;
    }

    public int getProID() {
        return ProID;
    }

    public void setProID(int proID) {
        ProID = proID;
    }

    public String getCatoID() {
        return CatoID;
    }

    public void setCatoID(String catoID) {
        CatoID = catoID;
    }

    public String getLocID() {
        return LocID;
    }

    public void setLocID(String locID) {
        LocID = locID;
    }

    public String getProBarcode() {
        return ProBarcode;
    }

    public void setProBarcode(String proBarcode) {
        ProBarcode = proBarcode;
    }

    public String getProname() {
        return Proname;
    }

    public void setProname(String proname) {
        Proname = proname;
    }

    public String getPronameKh() {
        return PronameKh;
    }

    public void setPronameKh(String pronameKh) {
        PronameKh = pronameKh;
    }

    public int getProqty() {
        return Proqty;
    }

    public void setProqty(int proqty) {
        Proqty = proqty;
    }

    public double getProcost() {
        return Procost;
    }

    public void setProcost(double procost) {
        Procost = procost;
    }

    public double getProprice() {
        return Proprice;
    }

    public void setProprice(double proprice) {
        Proprice = proprice;
    }

    public double getProtax() {
        return Protax;
    }

    public void setProtax(double protax) {
        Protax = protax;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
