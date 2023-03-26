package com.example.systempos.Check_out;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataConvert {

    @TypeConverter
    public static ArrayList<String> fromString(String value){
        Gson gson = new Gson();
        Type type=new TypeToken<ArrayList<String>>(){}.getType();


        return gson.fromJson(value,type);
    }
    @TypeConverter
    public static String fromArrayList(ArrayList<String> list){
        Gson gson = new Gson();
        return gson.toJson(list);
        }
    }





//@TypeConverters({ DataConverts.class, DateConvert.class})
//public abstract class BlueTeachnology_Database extends RoomDatabase {
//
//    public abstract BlueTeachnology_Dao blueTeachnology_dao();
//
//    public static volatile BlueTeachnology_Database INSTRANCE;
//
//    public static BlueTeachnology_Database getInstance(Context context){
//        if(INSTRANCE == null){
//            INSTRANCE = Room.databaseBuilder(context.getApplicationContext(), BlueTeachnology_Database.class, "BlueTeachnology_databaase")
//                    .allowMainThreadQueries()
//
//                    .build();
//        }
//        return INSTRANCE;
//    }
//}