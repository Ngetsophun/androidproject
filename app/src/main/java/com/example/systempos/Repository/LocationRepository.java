package com.example.systempos.Repository;

import android.content.Context;
import android.security.identity.EphemeralPublicKeyNotFoundException;

import androidx.lifecycle.LiveData;

import com.example.systempos.database.UserDatabase;
import com.example.systempos.location.LocationData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LocationRepository {
    private UserDatabase userDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void InsertLocation (LocationData locationData){
        executor.execute(new Runnable() {
            @Override
            public void run() {

                userDatabase.userDAO().InsertLocation(locationData);
            }
        });
    }
    public LocationRepository(Context context){
        userDatabase = UserDatabase.getUserDatabase(context);
    }
    public List<LocationData> getAllLocationFuture() throws ExecutionException,InterruptedException{
        return userDatabase.userDAO().getAllLocationFuture();
    }
    public LiveData<List<LocationData>> getAllLocationLiveData(){
        return userDatabase.userDAO().getAllLocationLiveData();
    }

}
