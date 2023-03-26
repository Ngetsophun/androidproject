package com.example.systempos.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.systempos.Check_out.CheckOutData;
import com.example.systempos.database.UserDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CheckOutRepository {
    private UserDatabase userDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void InsertCheckOut(CheckOutData checkOutData){
        executor.execute(new Runnable() {
            @Override
            public void run() {

                userDatabase.userDAO().InsertCheckOut(checkOutData);
            }
        });
    }
    public CheckOutRepository(Context context){
        userDatabase = UserDatabase.getUserDatabase(context);
    }
    public List<CheckOutData> getAllCheckOutFuture()throws ExecutionException,InterruptedException{
        return userDatabase.userDAO().getAllCheckOutFuture();
    }
    public LiveData<List<CheckOutData>> getAllCheckOutLiveData(){
        return userDatabase.userDAO().getAllCheckOutLiveData();
    }
}
