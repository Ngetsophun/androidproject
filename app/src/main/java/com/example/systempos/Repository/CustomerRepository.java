package com.example.systempos.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.systempos.Customer.CustomerData;
import com.example.systempos.database.UserDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CustomerRepository {
    private UserDatabase userDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void InsertCustomer(CustomerData customerData){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDatabase.userDAO().InsertCustomer(customerData);
            }
        });
    }
    public CustomerRepository(Context context){
        userDatabase = UserDatabase.getUserDatabase(context);
    }
    public List<CustomerData> getAllCustomerFuture() throws ExecutionException,InterruptedException{
        return userDatabase.userDAO().getAllCustomerFuture();
    }

    public LiveData<List<CustomerData>> getAllCustomerLiveData(){
        return userDatabase.userDAO().getAllCustomerLiveData();
    }
}
