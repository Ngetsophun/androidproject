package com.example.systempos.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.systempos.database.UserDatabase;
import com.example.systempos.model.CatogoryData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CatogoryRepository {
    private UserDatabase userDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void InsertCatogory(CatogoryData catogoryData){
        executor.execute(new Runnable() {
            @Override
            public void run() {

                userDatabase.userDAO().InsertCotogory(catogoryData);
            }
        });
    }

    public CatogoryRepository(Context context){
        userDatabase = UserDatabase.getUserDatabase(context);
    }

    public List<CatogoryData> getAllCatoFuture() throws ExecutionException,InterruptedException{
        return userDatabase.userDAO().getAllCatoFuture();
    }
    public LiveData<List<CatogoryData>> getAllCatoLiveData(){
        return userDatabase.userDAO().getAllCatoLiveData();
    }
}
