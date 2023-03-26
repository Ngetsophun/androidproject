package com.example.systempos.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.systempos.Payment.PaymentData;
import com.example.systempos.Repository.PaymentRepository;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PaymentViewModel extends AndroidViewModel {

    private PaymentRepository paymentRepository;

    public PaymentViewModel(@NonNull Application application) {
        super(application);
        paymentRepository = new PaymentRepository(application);
    }
    public void InsertPayment(PaymentData paymentData){
        paymentRepository.InsertPayment(paymentData);
    }
    public List<PaymentData> getAllPaymentFuture()throws ExecutionException,InterruptedException {
        Callable<List<PaymentData>> callable = new Callable<List<PaymentData>>() {
            @Override
            public List<PaymentData> call() throws Exception {
                return paymentRepository.getAllPaymentFuture();
            }
        };
        Future<List<PaymentData>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }
    public LiveData<List<PaymentData>> getAllPaymentLiveData(){
        return paymentRepository.getAllPaymentLiveData();
    }

}
