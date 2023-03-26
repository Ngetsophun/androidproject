package com.example.systempos.Exchange;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.systempos.Check_out.Comfirm_Activity;
import com.example.systempos.MainActivity;
import com.example.systempos.Repository.ExchageRepository;
import com.example.systempos.ViewModel.ExchageViewModel;
import com.example.systempos.databinding.FragmentExchangeBinding;

import java.util.List;


public class ExchangeFragment extends Fragment {

    FragmentExchangeBinding binding;
    ExchangeData exchangeData;
    RecyclerView recyclerView;
    ExchageRepository exchageRepository;
    ExchageViewModel exchageViewModel;
    AdapterExchage adapterExchage;
    Dialog dialog;

    String exchageMoney;



    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExchangeBinding.inflate(inflater,container,false);
        exchangeData = new ExchangeData();
        mainActivity = (MainActivity) getActivity();





        binding.btnAddExcahge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.showFragement.setVisibility(View.GONE);
                binding.addFragement.setVisibility(view.VISIBLE);
            }
        });
         binding.btnbackchage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 binding.showFragement.setVisibility(View.VISIBLE);
                 binding.addFragement.setVisibility(view.GONE);
             }
         });

         binding.btnUpExchage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent intent = new Intent(getActivity(),Update_Exchagectivity.class);
                 startActivity(intent);
                 Toast.makeText(mainActivity, "Update", Toast.LENGTH_SHORT).show();

             }
         });




         //live data
        exchageViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ExchageViewModel.class);
        exchageViewModel.getAllExchageLiveData().observe(getViewLifecycleOwner(), new Observer<List<ExchangeData>>() {
            @Override
            public void onChanged(List<ExchangeData> exchangeData) {
                if(exchangeData != null){
                    adapterExchage = new AdapterExchage();
                    adapterExchage.setExchange(exchangeData);

                    binding.recyclerExchage.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));
                    binding.recyclerExchage.setAdapter(adapterExchage);

                }
            }
        });

        //add Exchage
        binding.btnchage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ExchageMoney = binding.exchageMoney.getText().toString().trim();
                String ExchageDesc = binding.exchageDescribtion.getText().toString().trim();

                if(TextUtils.isEmpty(ExchageMoney)){
                    binding.exchageMoney.setError("Requird");
                }else{
                    exchangeData.setExchageMoney(ExchageMoney);
                    exchangeData.setExchageDesc(ExchageDesc);
                    exchageViewModel.InsertExchage(exchangeData);

                    binding.exchageMoney.setText("");
                    binding.exchageDescribtion.setText("");

                    exchageMoney = binding.exchageMoney.getText().toString().trim();
                    Intent intent = getActivity().getIntent();
                    intent.putExtra(Comfirm_Activity.EXCHANGE,exchageMoney);


                }
            }
        });


        return binding.getRoot();

    }
    private void getIntenData(){
//
//        IS_UPDATE = true;
//        binding.btnUpSaveexchage.setText("UPDATE");
//        exchangeData = (ExchangeData) ge

    }


//    private void BuildDialogsaveSuccess(){
//        dialog  = new Dialog();
//        dialog.setContentView(R.layout.custom_savedialog_success);
//        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.back_ground_dialog_save));
////        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.setCancelable(false);
//        Button dialog_save = dialog.findViewById(R.id.btnDialog_user_ok);
//
//        dialog_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//    }
}