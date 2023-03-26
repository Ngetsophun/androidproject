package com.example.systempos.Check_out;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "CheckOUT")
public class CheckOutData {
    @PrimaryKey(autoGenerate = true)
    int checkOutID;
    @ColumnInfo
    String cashierName;
    @ColumnInfo
    String customerName;
    @ColumnInfo
    String paymentType;
    @ColumnInfo
    ArrayList<String> qty;
    @ColumnInfo
    ArrayList<String> price;
    @ColumnInfo
    ArrayList<String>  ProductNames;
    @ColumnInfo
    ArrayList<String> amount;
    @ColumnInfo
    Double subTotal;
    @ColumnInfo
    Double discountAmount;
    @ColumnInfo
    Double totalAfterDollar;
    @ColumnInfo
    Double totalAfterKhmer;
    @ColumnInfo
    Double receive_D;
    @ColumnInfo
    Double change_D;
    @ColumnInfo
    Double change_R;
    @ColumnInfo
    Double discount;
    @ColumnInfo
    Double exchageToKH;

    @ColumnInfo
    String date;


    public CheckOutData() {
    }

    public CheckOutData(int checkOutID, String cashierName, String customerName, String paymentType, ArrayList<String> qty, ArrayList<String> price, ArrayList<String> productNames, ArrayList<String> amount, Double subTotal, Double discountAmount, Double totalAfterDollar, Double totalAfterKhmer, Double receive_D, Double change_D, Double change_R, Double discount, Double exchageToKH, String date) {
        this.checkOutID = checkOutID;
        this.cashierName = cashierName;
        this.customerName = customerName;
        this.paymentType = paymentType;
        this.qty = qty;
        this.price = price;
        ProductNames = productNames;
        this.amount = amount;
        this.subTotal = subTotal;
        this.discountAmount = discountAmount;
        this.totalAfterDollar = totalAfterDollar;
        this.totalAfterKhmer = totalAfterKhmer;
        this.receive_D = receive_D;
        this.change_D = change_D;
        this.change_R = change_R;
        this.discount = discount;
        this.exchageToKH = exchageToKH;
        this.date = date;
    }

    public int getCheckOutID() {
        return checkOutID;
    }

    public void setCheckOutID(int checkOutID) {
        this.checkOutID = checkOutID;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public ArrayList<String> getQty() {
        return qty;
    }

    public void setQty(ArrayList<String> qty) {
        this.qty = qty;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<String> price) {
        this.price = price;
    }

    public ArrayList<String> getProductNames() {
        return ProductNames;
    }

    public void setProductNames(ArrayList<String> productNames) {
        ProductNames = productNames;
    }

    public ArrayList<String> getAmount() {
        return amount;
    }

    public void setAmount(ArrayList<String> amount) {
        this.amount = amount;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getTotalAfterDollar() {
        return totalAfterDollar;
    }

    public void setTotalAfterDollar(Double totalAfterDollar) {
        this.totalAfterDollar = totalAfterDollar;
    }

    public Double getTotalAfterKhmer() {
        return totalAfterKhmer;
    }

    public void setTotalAfterKhmer(Double totalAfterKhmer) {
        this.totalAfterKhmer = totalAfterKhmer;
    }

    public Double getReceive_D() {
        return receive_D;
    }

    public void setReceive_D(Double receive_D) {
        this.receive_D = receive_D;
    }

    public Double getChange_D() {
        return change_D;
    }

    public void setChange_D(Double change_D) {
        this.change_D = change_D;
    }

    public Double getChange_R() {
        return change_R;
    }

    public void setChange_R(Double change_R) {
        this.change_R = change_R;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getExchageToKH() {
        return exchageToKH;
    }

    public void setExchageToKH(Double exchageToKH) {
        this.exchageToKH = exchageToKH;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
