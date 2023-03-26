package com.example.systempos.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.systempos.Repository.SettingRepository;
import com.example.systempos.Setting.SettingData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SettingViewModel extends AndroidViewModel {
    private SettingRepository settingRepository;

    public SettingViewModel(@NonNull Application application) {
        super(application);
        settingRepository = new SettingRepository(application);
    }

    public void InsertSetting(SettingData settingData){
        settingRepository.InsertSetting(settingData);
    }
    public List<SettingData> getALLSettingFuture()throws ExecutionException,InterruptedException{
        Callable<List<SettingData>> callable = new Callable<List<SettingData>>() {
            @Override
            public List<SettingData> call() throws Exception {

                return settingRepository.getALLSettingFuture();
            }
        };
        Future<List<SettingData>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }
    public LiveData<List<SettingData>> getALLSettingLiveData(){
        return settingRepository.getALLSettingLiveData();
    }

}
