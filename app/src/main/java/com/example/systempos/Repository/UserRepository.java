package com.example.systempos.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.systempos.User.UserData;
import com.example.systempos.database.UserDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class UserRepository {
    private UserDatabase userDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void InsertUser (UserData userData){
        executor.execute(new Runnable() {
            @Override
            public void run() {

                userDatabase.userDAO().InsertUser(userData);
            }
        });
    }

    public UserRepository (Context context){
        userDatabase = UserDatabase.getUserDatabase(context);
    }
    public List<UserData> getAllUserFuture() throws ExecutionException,InterruptedException{
        return userDatabase.userDAO().getAllUserFuture();
    }
    public LiveData<List<UserData>> getAllUserLiveData(){
        return userDatabase.userDAO().getAllUserLiveData();
    }

}
