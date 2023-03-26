package com.example.systempos.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.systempos.Payment.PaymentData;
import com.example.systempos.database.UserDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PaymentRepository {
    private UserDatabase userDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void InsertPayment(PaymentData paymentData){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDatabase.userDAO().InsertPayment(paymentData);
            }
        });
    }
    public PaymentRepository(Context context){
        userDatabase = UserDatabase.getUserDatabase(context);
    }
    public List<PaymentData> getAllPaymentFuture() throws ExecutionException,InterruptedException{
        return userDatabase.userDAO().getAllPaymentFuture();
    }
    public LiveData<List<PaymentData>> getAllPaymentLiveData(){
        return userDatabase.userDAO().getAllPaymentLiveData();
    }

}
