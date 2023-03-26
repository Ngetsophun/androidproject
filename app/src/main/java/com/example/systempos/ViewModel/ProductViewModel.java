package com.example.systempos.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.systempos.Product.ProductData;
import com.example.systempos.Repository.ProductRepository;
import com.example.systempos.User.UserData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
    }
    public void InsertProduct(ProductData productData){
        productRepository.InsertProduct(productData);
    }
    public List<ProductData> getAllProductFuture() throws ExecutionException,InterruptedException{
        Callable<List<ProductData>> callable = new Callable<List<ProductData>>() {
            @Override
            public List<ProductData> call() throws Exception {
                return productRepository.getAllProductFuture();
            }
        };
        Future<List<ProductData>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }
    public LiveData<List<ProductData>> getAllProductLiveData(){
        return productRepository.getAllProductLiveData();
    }

}
