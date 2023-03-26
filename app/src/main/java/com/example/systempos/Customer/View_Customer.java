package com.example.systempos.Customer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.systempos.CurrentDateHelper;
import com.example.systempos.R;
import com.example.systempos.Repository.CustomerRepository;
import com.example.systempos.ViewModel.CustomerViewModel;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityViewCustomerBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class View_Customer extends AppCompatActivity {

    ActivityViewCustomerBinding binding;
    UserDatabase userDatabase;
    RecyclerView recyclerView;
    UserDAO userDAO;
    int id;
   String date = "";

   AdapterCustomer adapterCustomer;
   CustomerRepository customerRepository;
   CustomerViewModel customerViewModel;
   CustomerData customerData;
   Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityViewCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.show_customer);
        ImageView imgBack = findViewById(R.id.img_toolbar_back);
        customerData = new CustomerData();

        customerViewModel  = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CustomerViewModel.class);
        customerViewModel.getAllCustomerLiveData().observe(View_Customer.this, new Observer<List<CustomerData>>() {
            @Override
            public void onChanged(List<CustomerData> customerData) {
                if(customerData != null){
                    adapterCustomer = new AdapterCustomer(customerData);
                    adapterCustomer.setCustomer(customerData);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapterCustomer);
                }
            }
        });


        binding.floatCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               binding.AddCustomActivity.setVisibility(View.VISIBLE);
               binding.ShowCustomActivity.setVisibility(View.GONE);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.AddCustomActivity.setVisibility(View.GONE);
                binding.ShowCustomActivity.setVisibility(View.VISIBLE);
            }
        });


       // Select Choose date
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
       binding.addCustDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(View_Customer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month+1;
                        date = dayOfMonth+"/"+month+"/"+year;
                       binding.addCustDob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //Add Customer
        binding.btnaddcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.addCustName.getText().toString().trim();
                String phone = binding.addCustPhone.getText().toString().trim();
                String email = binding.addCustEmail.getText().toString().trim();
                String address = binding.addCustAddress.getText().toString().trim();
                String dob = binding.addCustDob.getText().toString().trim();

                String gender = "";
                if(binding.addCustMale.isChecked()){
                    gender = "Male";
                }
                if(binding.addCustFemale.isChecked()){
                    gender  = "Female";
                }

                if(TextUtils.isEmpty(name)){
                    binding.addCustName.setError("Requird");
                } else if (TextUtils.isEmpty(phone)) {
                    binding.addCustPhone.setError("Requird");
                } else if (TextUtils.isEmpty(email)) {
                    binding.addCustDob.setError("Requird");
                }else {
                    customerData.setName(name);
                    customerData.setGender(gender);
                    customerData.setPhone(phone);
                    customerData.setEmail(email);
                    customerData.setAddress(address);
                    customerData.setDob(dob);
                    customerData.setDate(CurrentDateHelper.getCurrentDate());
                    customerViewModel.InsertCustomer(customerData);

                   BuildDialogsaveSuccess();
                   dialog.show();

                    binding.addCustName.setText("");
                    binding.addCustPhone.setText("");
                    binding.addCustEmail.setText("");
                    binding.addCustAddress.setText("");
                    binding.addCustDob.setText("");
                }
            }
        });

    }
    // dialog save success

    private void BuildDialogsaveSuccess(){
        dialog  = new Dialog(View_Customer.this);
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



    //Adapter Customer
    public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.MyviewHolder>{
        List<CustomerData> customerData;

        public AdapterCustomer(List<CustomerData> customerData) {
            this.customerData = customerData;
        }

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_customer,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

           // show
            holder.name.setText(String.valueOf(customerData.get(position).getName()));
            holder.phone.setText(String.valueOf(customerData.get(position).getPhone()));
            holder.dateNow.setText(CurrentDateHelper.getCurrentDate());






            //delete
            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries().build();

                    userDAO = userDatabase.userDAO();
                    userDAO.DeleteCustomerByid(customerData.get(position).getId());
                    customerData.remove(position);
                    notifyDataSetChanged();
                }
            });


            //update
            final CustomerData customerData1 = customerData.get(position);
            if(customerData1 == null){
                return;
            }

            holder.cartcustom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   Intent intent = new Intent(view.getContext(),Update_CustomerActivity.class);
                    intent.putExtra("customer",customerData1);

                    view.getContext().startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return customerData.size();
        }
        public void setCustomer(List<CustomerData> customerDataList){
            customerData = customerDataList;
            notifyDataSetChanged();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder{
            TextView name,phone,dateNow;

            ImageView img_delete;

            CardView cartcustom;
            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.cust_names);
                phone = itemView.findViewById(R.id.cust_phones);

                dateNow = itemView.findViewById(R.id.cust_datenow);
                img_delete = itemView.findViewById(R.id.img_Cust_deletes);
                cartcustom = itemView.findViewById(R.id.cardCustomer);





            }
        }

    }

}
