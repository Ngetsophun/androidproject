package com.example.systempos.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.systempos.Customer.CustomerData;
import com.example.systempos.Repository.CustomerRepository;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CustomerViewModel extends AndroidViewModel {
    private CustomerRepository customerRepository;

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        customerRepository = new CustomerRepository(application);
    }
    public void InsertCustomer(CustomerData customerData){
        customerRepository.InsertCustomer(customerData);
    }
    public List<CustomerData> getAllCustomerFuture() throws ExecutionException,InterruptedException{
        Callable<List<CustomerData>> callable = new Callable<List<CustomerData>>() {
            @Override
            public List<CustomerData> call() throws Exception {
                return customerRepository.getAllCustomerFuture();
            }
        };
        Future<List<CustomerData>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }
    public LiveData<List<CustomerData>> getAllCustomerLiveData(){
        return customerRepository.getAllCustomerLiveData();
    }
}
