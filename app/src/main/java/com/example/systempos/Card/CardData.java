package com.example.systempos.Card;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Card")
public class CardData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int Cardid;

    @ColumnInfo
    String pro_cardNameEng;
    @ColumnInfo
    String Pro_cardNameKH;
    @ColumnInfo
    int Pro_cardQty;
    @ColumnInfo
    double Pro_cardPrice;

    @ColumnInfo
    double Pro_cardCost;

    double Pro_cardTax;
    String Pro_cardimg;

    String SubTotal_D;
    String SubToal_R;


    public CardData() {
    }

    public CardData(int cardid, String pro_cardNameEng, String pro_cardNameKH, int pro_cardQty, double pro_cardPrice, double pro_cardCost, double pro_cardTax, String pro_cardimg, String subTotal_D, String subToal_R) {
        Cardid = cardid;
        this.pro_cardNameEng = pro_cardNameEng;
        Pro_cardNameKH = pro_cardNameKH;
        Pro_cardQty = pro_cardQty;
        Pro_cardPrice = pro_cardPrice;
        Pro_cardCost = pro_cardCost;
        Pro_cardTax = pro_cardTax;
        Pro_cardimg = pro_cardimg;
        SubTotal_D = subTotal_D;
        SubToal_R = subToal_R;
    }

    public int getCardid() {
        return Cardid;
    }

    public void setCardid(int cardid) {
        Cardid = cardid;
    }

    public String getPro_cardNameEng() {
        return pro_cardNameEng;
    }

    public void setPro_cardNameEng(String pro_cardNameEng) {
        this.pro_cardNameEng = pro_cardNameEng;
    }

    public String getPro_cardNameKH() {
        return Pro_cardNameKH;
    }

    public void setPro_cardNameKH(String pro_cardNameKH) {
        Pro_cardNameKH = pro_cardNameKH;
    }

    public int getPro_cardQty() {
        return Pro_cardQty;
    }

    public void setPro_cardQty(int pro_cardQty) {
        Pro_cardQty = pro_cardQty;
    }

    public double getPro_cardPrice() {
        return Pro_cardPrice;
    }

    public void setPro_cardPrice(double pro_cardPrice) {
        Pro_cardPrice = pro_cardPrice;
    }

    public double getPro_cardCost() {
        return Pro_cardCost;
    }

    public void setPro_cardCost(double pro_cardCost) {
        Pro_cardCost = pro_cardCost;
    }

    public double getPro_cardTax() {
        return Pro_cardTax;
    }

    public void setPro_cardTax(double pro_cardTax) {
        Pro_cardTax = pro_cardTax;
    }

    public String getPro_cardimg() {
        return Pro_cardimg;
    }

    public void setPro_cardimg(String pro_cardimg) {
        Pro_cardimg = pro_cardimg;
    }

    public String getSubTotal_D() {
        return SubTotal_D;
    }

    public void setSubTotal_D(String subTotal_D) {
        SubTotal_D = subTotal_D;
    }

    public String getSubToal_R() {
        return SubToal_R;
    }

    public void setSubToal_R(String subToal_R) {
        SubToal_R = subToal_R;
    }
}
