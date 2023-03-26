package com.example.systempos.User;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tbUser")
public class UserData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo
    String name;
    @ColumnInfo
    String password;

    @ColumnInfo
    String gender;

    @ColumnInfo
    String address;

    @ColumnInfo
    String role;

    @ColumnInfo
    String dob;
    @ColumnInfo
    String permission;

    @ColumnInfo
    String image;

    @ColumnInfo
    String date;
    @ColumnInfo
    boolean male;
    @ColumnInfo
    boolean female;
    @ColumnInfo
    boolean admin;
    @ColumnInfo
    boolean manager;
//    @ColumnInfo
//    boolean cashier;
    @ColumnInfo
    boolean insert;
    @ColumnInfo
    boolean update;
    @ColumnInfo
    boolean delete;
    @ColumnInfo
    boolean view;
    @ColumnInfo
    boolean allow;

    public UserData() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public Boolean getFemale() {
        return female;
    }

    public void setFemale(Boolean female) {
        this.female = female;
    }


    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    public boolean isAdmin(){

        return admin;
    }



    public void setManager(Boolean manager) {

        this.manager = manager;
    }
    public boolean isManager(){

        return isManager();
    }



//
//    public void setCashier(Boolean cashier) {
//
//        this.cashier = cashier;
//    }
    public boolean isChashier(){

        return isChashier();
    }


    public void setInsert(Boolean insert) {
        this.insert = insert;
    }
    public boolean isInsert(){

        return isInsert();
    }

    public void setUpdate(Boolean update) {

        this.update = update;
    }
    public boolean isUpdate(){

        return isUpdate();
    }

    public boolean isDelete(){
        return isDelete();
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }


    public void setView(Boolean view) {
        this.view = view;
    }
    public boolean isView(){
        return isView();
    }

    public void setAllow(Boolean allow) {
        this.allow = allow;
    }
    public boolean isAllow(){
        return isAllow();
    }
}
