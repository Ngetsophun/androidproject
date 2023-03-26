package com.example.systempos.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.systempos.Exchange.ExchangeData;
import com.example.systempos.Repository.ExchageRepository;

import java.util.List;

public class ExchageViewModel extends AndroidViewModel {
    private ExchageRepository exchageRepository;

    public ExchageViewModel(@NonNull Application application) {
        super(application);
        exchageRepository = new ExchageRepository(application);
    }

    public void InsertExchage(ExchangeData exchangeData){
        exchageRepository.InsertExchage(exchangeData);
    }
    public LiveData<List<ExchangeData>>getAllExchageLiveData(){
        return exchageRepository.getAllExchageLiveData();
    }
}
