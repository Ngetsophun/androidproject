package com.example.systempos.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.systempos.Repository.CatogoryRepository;
import com.example.systempos.model.CatogoryData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CatogoryViewModel extends AndroidViewModel {

    private CatogoryRepository catogoryRepository;

    public CatogoryViewModel(@NonNull Application application) {
        super(application);
        catogoryRepository = new CatogoryRepository(application);
    }
    public void InsertCatogory(CatogoryData catogoryData){
        catogoryRepository.InsertCatogory(catogoryData);
    }
    public List<CatogoryData> getAllCatoFuture() throws ExecutionException,InterruptedException{
        Callable<List<CatogoryData>> callable = new Callable<List<CatogoryData>>() {
            @Override
            public List<CatogoryData> call() throws Exception {
                return catogoryRepository.getAllCatoFuture();
            }
        };
        Future<List<CatogoryData>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<CatogoryData>> getAllCatoLiveData(){
        return catogoryRepository.getAllCatoLiveData();
    }

}
