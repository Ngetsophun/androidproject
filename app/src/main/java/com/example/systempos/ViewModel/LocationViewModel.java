package com.example.systempos.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.systempos.Repository.LocationRepository;
import com.example.systempos.location.LocationData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LocationViewModel extends AndroidViewModel {
    private LocationRepository locationRepository;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        locationRepository = new LocationRepository(application);
    }
    public void InsertLocation(LocationData locationData){
        locationRepository.InsertLocation(locationData);
    }
    public List<LocationData> getAllLocationFuture() throws ExecutionException,InterruptedException{
        Callable<List<LocationData>> callable = new Callable<List<LocationData>>() {
            @Override
            public List<LocationData> call() throws Exception {
                return locationRepository.getAllLocationFuture();
            }
        };
        Future<List<LocationData>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }
    public LiveData<List<LocationData>> getAllLocationLiveData(){
        return locationRepository.getAllLocationLiveData();
    }

}
