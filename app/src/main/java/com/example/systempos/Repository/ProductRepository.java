package com.example.systempos.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.systempos.Product.ProductData;
import com.example.systempos.database.UserDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductRepository {
    private UserDatabase userDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void InsertProduct(ProductData productData){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDatabase.userDAO().InsertProduct(productData);
            }
        });
    }
    public ProductRepository(Context context){
        userDatabase = UserDatabase.getUserDatabase(context);
    }
    public List<ProductData> getAllProductFuture() throws ExecutionException,InterruptedException{
        return userDatabase.userDAO().getAllProductFuture();
    }
    public LiveData<List<ProductData>> getAllProductLiveData(){
        return userDatabase.userDAO().getAllProductLiveData();
    }
}
