package com.example.systempos.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.systempos.Check_out.CheckOutData;
import com.example.systempos.Repository.CheckOutRepository;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CheckOutViewModel extends AndroidViewModel {
    private CheckOutRepository checkOutRepository;


    public CheckOutViewModel(@NonNull Application application) {
        super(application);
        checkOutRepository = new CheckOutRepository(application);
    }

    public void InsertCheckOut(CheckOutData checkOutData){
        checkOutRepository.InsertCheckOut(checkOutData);
    }

    public List<CheckOutData> getAllCheckOutFuture() throws ExecutionException,InterruptedException{
        Callable<List<CheckOutData>> callable = new Callable<List<CheckOutData>>() {
            @Override
            public List<CheckOutData> call() throws Exception {
                return checkOutRepository.getAllCheckOutFuture();
            }
        };
        Future<List<CheckOutData>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<CheckOutData>> getAllCheckOutLiveData(){
        return checkOutRepository.getAllCheckOutLiveData();
    }
}
