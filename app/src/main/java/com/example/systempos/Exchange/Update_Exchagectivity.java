package com.example.systempos.Exchange;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.systempos.R;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityUpdateExchagectivityBinding;

public class Update_Exchagectivity extends AppCompatActivity {

    ActivityUpdateExchagectivityBinding binding;
    Boolean IS_UPDATE= false;
    UserDatabase userDatabase;
    UserDAO userDAO;
    ExchangeData exchangeData;
    int id;

    EditText exchageMoney,exchangeDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateExchagectivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentData();

        binding.btnUpBackexchage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ExchangeFragment fragment = new ExchangeFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.showAllFragement,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        binding.btnUpSaveexchage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(IS_UPDATE == false){
                    SaveExchage saveExchage = new SaveExchage();
                    saveExchage.execute();

                }else {
                    UpdateExchage updateExchage = new UpdateExchage();
                    updateExchage.execute();
                }


            }
        });
    }
    private void getIntentData(){
        if(getIntent().hasExtra("exchage")){
            IS_UPDATE = true;
            binding.btnUpSaveexchage.setText("UPDATE");

            exchangeData = (ExchangeData) getIntent().getSerializableExtra("exchage");
            binding.UpExchageMoney.setText(exchangeData.getExchageMoney());
            binding.UpExchageDescribtion.setText(exchangeData.getExchageDesc());

            id = exchangeData.getId();
        }else{
            IS_UPDATE = false;
            binding.btnUpSaveexchage.setText("SAVE");
        }
    }
    //Class Save
    class SaveExchage extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            ExchangeData exchangeData = new ExchangeData();
            exchangeData.setExchageMoney(String.valueOf(exchageMoney));
            exchangeData.setExchageDesc(String.valueOf(exchangeDesc));

            exchangeData.setId(id);

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
//
            userDAO.InsertExchage(exchangeData);

            userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
            exchangeData.setExchageDesc(binding.UpExchageDescribtion.getText().toString());
            exchangeData.setExchageMoney(binding.UpExchageMoney.getText().toString());

            userDAO.InsertExchage(exchangeData);

            return null;
        }

//        @Override
//        protected void onPostExecute(Void unused) {
//            super.onPostExecute(unused);
//            startActivity(new Intent(getApplicationContext(),ExchangeFragment.class));
//        }
    }
    class UpdateExchage extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            ExchangeData exchangeData = new ExchangeData();
            exchangeData.setExchageMoney(String.valueOf(exchageMoney));
            exchangeData.setExchageDesc(String.valueOf(exchangeDesc));

            exchangeData.setId(id);

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
//
            UserDAO userDAO = userDatabase.userDAO();
            userDAO.UpdateExchage(exchangeData);

            exchangeData.setId(id);
            exchangeData.setExchageDesc(binding.UpExchageDescribtion.getText().toString());
            exchangeData.setExchageMoney(binding.UpExchageMoney.getText().toString());

            userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
            userDAO.UpdateExchage(exchangeData);

            return null;
        }

//        @Override
//        protected void onPostExecute(Void unused) {
//            super.onPostExecute(unused);
//            startActivity(new Intent(getApplicationContext(),ExchangeFragment.class));
//
//        }
    }

}