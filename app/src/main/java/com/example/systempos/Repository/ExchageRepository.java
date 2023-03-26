package com.example.systempos.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.systempos.Exchange.ExchangeData;
import com.example.systempos.database.UserDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExchageRepository {
    private UserDatabase userDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void  InsertExchage(ExchangeData exchangeData){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDatabase.userDAO().InsertExchage(exchangeData);
            }
        });
    }

    public ExchageRepository(Context context){
        userDatabase = UserDatabase.getUserDatabase(context);
    }

    public LiveData<List<ExchangeData>> getAllExchageLiveData(){
        return userDatabase.userDAO().getAllExchageLiveData();
    }


}
