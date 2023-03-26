package com.example.systempos.Sale;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Sale")
public class SaleData {
    @PrimaryKey(autoGenerate = true)
    int  ProductID;

    @ColumnInfo
    String CusName;
    @ColumnInfo
    String ProductName;
    @ColumnInfo
    String ProductName1;
    @ColumnInfo
    int Qty;
    @ColumnInfo
    double Price;
    @ColumnInfo
    double Discount;
    @ColumnInfo
    String Date;
    @ColumnInfo
    String Total;

    public SaleData() {
    }

    public SaleData(int productID, String cusName, String productName, String productName1, int qty, double price, double discount, String date, String total) {
        ProductID = productID;
        CusName = cusName;
        ProductName = productName;
        ProductName1 = productName1;
        Qty = qty;
        Price = price;
        Discount = discount;
        Date = date;
        Total = total;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductName1() {
        return ProductName1;
    }

    public void setProductName1(String productName1) {
        ProductName1 = productName1;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
