package com.example.systempos.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.systempos.Setting.SettingData;
import com.example.systempos.database.UserDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SettingRepository {
    private UserDatabase userDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void InsertSetting(SettingData settingData){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDatabase.userDAO().InsertSetting(settingData);
            }
        });
    }
    public SettingRepository(Context context){
        userDatabase = UserDatabase.getUserDatabase(context);
    }
    public List<SettingData>getALLSettingFuture() throws ExecutionException,InterruptedException {
        return userDatabase.userDAO().getALLSettingFuture();
    }
    public LiveData<List<SettingData>> getALLSettingLiveData(){
        return userDatabase.userDAO().getALLSettingLiveData();
    }

}
