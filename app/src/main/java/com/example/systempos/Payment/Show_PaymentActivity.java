package com.example.systempos.Payment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.example.systempos.Catogory.View_Catogoey;
import com.example.systempos.R;
import com.example.systempos.ViewModel.PaymentViewModel;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityShowPaymentBinding;

import java.util.List;

public class Show_PaymentActivity extends AppCompatActivity {

    ActivityShowPaymentBinding binding;
    UserDatabase userDatabase;
    UserDAO userDAO;
    RecyclerView recyclerView;
    int id;
    PaymentData paymentData;
    AdapterPayment adapterPayment;
    PaymentViewModel paymentViewModel;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recyclerView = findViewById(R.id.show_allPayment);
        paymentData = new PaymentData();
//
//        userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
//                .build();
//        userDAO = userDatabase.userDAO();
//
//        List<PaymentData> paymentData = userDAO.getAllPayment();
//        AdapterPayment paymentAdapter = new AdapterPayment(paymentData);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(paymentAdapter);
        paymentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PaymentViewModel.class);
        paymentViewModel.getAllPaymentLiveData().observe(Show_PaymentActivity.this, new Observer<List<PaymentData>>() {
            @Override
            public void onChanged(List<PaymentData> paymentData) {
                if(paymentData != null){
                    adapterPayment = new AdapterPayment(paymentData);
                    adapterPayment.setPayment(paymentData);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapterPayment);
                }
            }
        });



        //add new Payment
       binding.btnAddPayment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String payment = binding.addPaymentType.getText().toString();
               String description  = binding.addPaymentDsc.getText().toString();


               if(TextUtils.isEmpty(payment)){
                   binding.addPaymentType.setError("Requird");
               } else if (TextUtils.isEmpty(description)) {
                   binding.addPaymentDsc.setError("Requird");
               }else {
                   paymentData.setPaymentType(payment);
                   paymentData.setDecription(description);
                   paymentViewModel.InsertPayment(paymentData);
                   BuildDialogsaveSuccess();
                   binding.addPaymentType.setText("");
                   binding.addPaymentDsc.setText("");
                   dialog.show();
               }


           }

       });



        binding.floatpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addpaymentActivity.setVisibility(View.VISIBLE);
                binding.showpaymentActivity.setVisibility(View.GONE);

            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addpaymentActivity.setVisibility(View.GONE);
                binding.showpaymentActivity.setVisibility(View.VISIBLE);
            }
        });


    }

    private void BuildDialogsaveSuccess(){
        dialog  = new Dialog(Show_PaymentActivity.this);
        dialog.setContentView(R.layout.custom_savedialog_success);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.back_ground_dialog_save));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        Button dialog_save = dialog.findViewById(R.id.btnDialog_user_ok);

        dialog_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //Adapter
    public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.MyviewHolder>{

        List<PaymentData> paymentData;

        public AdapterPayment(List<PaymentData> paymentData) {
            this.paymentData = paymentData;
        }

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_showpayment,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
            holder.textpayment.setText(String.valueOf(paymentData.get(position).getPaymentType()));
            holder.textdesc.setText(String.valueOf(paymentData.get(position).getDecription()));

            //delete
            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

                    userDAO = userDatabase.userDAO();
                    userDAO.DeletePaymentByid(paymentData.get(position).getPayId());
                    paymentData.remove(position);
                    notifyDataSetChanged();

                }
            });

            final PaymentData paymentData1 = paymentData.get(position);
            if(paymentData1 == null){
                return;
            }

//          //Update
//            holder.img_update.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), Update_PaymentActivity.class);
//                    intent.putExtra("payment",paymentData1);
//                    view.getContext().startActivity(intent);
//
//                    Toast.makeText(Show_PaymentActivity.this, "Update", Toast.LENGTH_SHORT).show();
//                }
//            });

            holder.cardpayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Update_PaymentActivity.class);
                    intent.putExtra("payment",paymentData1);
                    view.getContext().startActivity(intent);

                    Toast.makeText(Show_PaymentActivity.this, "Update", Toast.LENGTH_SHORT).show();

                }
            });
        }
        @Override
        public int getItemCount() {
            return paymentData.size();
        }

        public void setPayment(List<PaymentData> paymentDataList){
            paymentData = paymentDataList;
            notifyDataSetChanged();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder{

            TextView textpayment,textdesc;
            ImageView img_delete;
            CardView cardpayment;
            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                textpayment = itemView.findViewById(R.id.pay_payment);
                textdesc = itemView.findViewById(R.id.pay_Description);

                img_delete = itemView.findViewById(R.id.img_pay_delete);
                cardpayment = itemView.findViewById(R.id.cardshowPayment);


            }
        }
    }


}

