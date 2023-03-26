package com.example.systempos.Customer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.example.systempos.databinding.ActivityViewCustomerBinding;

import java.util.List;

public class Detail_CustomerActivity extends AppCompatActivity {

    ActivityViewCustomerBinding binding;


    public Detail_CustomerActivity(List<CustomerData> customerData) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityViewCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());






    }
}