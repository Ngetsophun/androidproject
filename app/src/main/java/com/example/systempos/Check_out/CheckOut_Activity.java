package com.example.systempos.Check_out;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.systempos.R;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityCheckOut2Binding;

import java.util.List;

public class CheckOut_Activity extends AppCompatActivity {

    ActivityCheckOut2Binding binding;

    UserDAO userDAO;
    List<CheckOutData> checkOutData;
    AdapterCheckOut adapterCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityCheckOut2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userDAO = UserDatabase.getUserDatabase(this).userDAO();
        checkOutData = userDAO.getAllCheckOutFuture();
        adapterCheckOut = new AdapterCheckOut(checkOutData,getApplicationContext());





    }
}