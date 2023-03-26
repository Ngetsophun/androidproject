package com.example.systempos.Customer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Customer")
public class CustomerData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int  id;
    @ColumnInfo
    String name;

    @ColumnInfo
    String phone;

    @ColumnInfo
    String gender;

    @ColumnInfo
    String email;
    @ColumnInfo
    String address;
    @ColumnInfo
    String dob;
    @ColumnInfo
    String date;

    public CustomerData() {
    }

    public CustomerData(int id, String name, String phone, String gender, String email, String address, String dob, String date) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.dob = dob;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}