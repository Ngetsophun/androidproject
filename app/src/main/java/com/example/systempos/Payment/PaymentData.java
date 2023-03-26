package com.example.systempos.Payment;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Payment")
public class PaymentData implements Serializable {

  @PrimaryKey(autoGenerate = true)
    int payId;

    @ColumnInfo
    String PaymentType;

    @ColumnInfo
    String Decription;

    public PaymentData() {
    }

    public PaymentData(int payId, String paymentType, String decription) {
        this.payId = payId;
        PaymentType = paymentType;
        Decription = decription;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getDecription() {
        return Decription;
    }

    public void setDecription(String decription) {
        Decription = decription;
    }
}
