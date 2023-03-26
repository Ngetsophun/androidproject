package com.example.systempos.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.systempos.Repository.UserRepository;
import com.example.systempos.User.UserData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);

    }

    public void InsertUser(UserData userData){

        userRepository.InsertUser(userData);
    }
    public List<UserData> getAllUserFuture() throws ExecutionException,InterruptedException{
        Callable<List<UserData>> callable = new Callable<List<UserData>>() {
            @Override
            public List<UserData> call() throws Exception {
                return userRepository.getAllUserFuture();
            }
        };
        Future<List<UserData>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }
    public LiveData<List<UserData>> getAllUserLiveData(){
        return userRepository.getAllUserLiveData();
    }



}
