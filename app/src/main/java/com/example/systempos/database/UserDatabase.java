package com.example.systempos.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.systempos.Card.CardData;
import com.example.systempos.Check_out.CheckOutData;
import com.example.systempos.Check_out.DataConvert;
import com.example.systempos.Customer.CustomerData;
import com.example.systempos.Exchange.ExchangeData;
import com.example.systempos.Payment.PaymentData;
import com.example.systempos.Product.ProductData;
import com.example.systempos.Sale.SaleData;
import com.example.systempos.Setting.SettingData;
import com.example.systempos.User.UserData;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.location.LocationData;
import com.example.systempos.model.CatogoryData;

@Database(entities = {CatogoryData.class, UserData.class, ProductData.class, CustomerData.class, PaymentData.class, LocationData.class, SaleData.class, CardData.class, ExchangeData.class, SettingData.class, CheckOutData.class}, version = 13)
@TypeConverters({DataConvert.class})
public abstract class UserDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "bluedb";
    private static UserDatabase userDatabase;

    public static synchronized UserDatabase getUserDatabase(Context context) {

        if (userDatabase == null) {
            userDatabase = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

        }
        return userDatabase;
    }

    public abstract UserDAO userDAO();


}
