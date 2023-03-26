package com.example.systempos.Invoice;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.systempos.R;
import com.example.systempos.databinding.ActivityInvoiceitemsBinding;

public class Invoiceitems extends AppCompatActivity {

    ActivityInvoiceitemsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityInvoiceitemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}