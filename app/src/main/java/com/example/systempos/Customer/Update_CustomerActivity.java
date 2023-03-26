package com.example.systempos.Customer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.systempos.R;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityUpdateCustomerBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Update_CustomerActivity extends AppCompatActivity {



    UserDatabase userDatabase;

    int id;
    String date = "";
    Boolean IS_UPDATE = false;
    ActivityUpdateCustomerBinding binding;
    CustomerData customerData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ImageView imgBack = findViewById(R.id.img_toolbar_back);

        getIntenData();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Update_CustomerActivity.this,View_Customer.class);
                startActivity(intent);

            }
        });

        binding.btnUpCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IS_UPDATE == false){
                    SaveCustomer saveCustomer = new SaveCustomer();
                    saveCustomer.execute();
                }else {
                    UpdateCustomer updateCustomer = new UpdateCustomer();
                    updateCustomer.execute();
                }
            }
        });

        // Select date
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        binding.upCustDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Update_CustomerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date = year+"/"+month+"/"+day;
                        binding.upCustDob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

    }

    private void getIntenData(){
        if(getIntent().hasExtra("customer")){
            IS_UPDATE = true;
            binding.btnUpCustomer.setText("UPDATE");
            customerData = (CustomerData) getIntent().getSerializableExtra("customer");

            id = customerData.getId();
            binding.upCustName.setText(String.valueOf(customerData.getName()));
            binding.upCustPhone.setText(String.valueOf(customerData.getPhone()));
            binding.upCustEmail.setText(String.valueOf(customerData.getEmail()));
            binding.upCustAddress.setText(String.valueOf(customerData.getAddress()));
            binding.upCustDob.setText(String.valueOf(customerData.getDob()));
            binding.upCustGender.setText(String.valueOf(customerData.getGender()));



        }else {
            IS_UPDATE = false;
            binding.btnUpCustomer.setText("SAVE");
        }
    }



    //Class Save
    class SaveCustomer extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String date_save = simpleDateFormat.format(date);

            CustomerData customerData1 = new CustomerData();
            customerData1.setName(String.valueOf(binding.upCustName));
            customerData1.setPhone(String.valueOf(binding.upCustPhone));
           customerData1.setGender(String.valueOf(binding.upCustGender));
            customerData1.setEmail(String.valueOf(binding.upCustEmail));
            customerData1.setAddress(String.valueOf(binding.upCustAddress));
            customerData1.setDob(String.valueOf(binding.upCustDob));


            customerData1.setDate(date_save);


            customerData1.setId(id);

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            UserDAO userDAO = userDatabase.userDAO();
            userDAO.InsertCustomer(customerData1);


            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();

            customerData1.setName(binding.upCustName.getText().toString().trim());
            customerData1.setPhone(binding.upCustPhone.getText().toString().trim());
            customerData1.setEmail(binding.upCustEmail.getText().toString().trim());
            customerData1.setAddress(binding.upCustAddress.getText().toString().trim());
            customerData1.setDob(binding.upCustDob.getText().toString().trim());
            customerData1.setGender(binding.upCustGender.getText().toString().trim());

            customerData1.setDate(date_save);


            userDAO.InsertCustomer(customerData1);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            startActivity(new Intent(getApplicationContext(),View_Customer.class));
            Toast.makeText(Update_CustomerActivity.this, "Save Update", Toast.LENGTH_SHORT).show();
        }
    }



    //Class Update
    class UpdateCustomer extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String date_update = simpleDateFormat.format(date);

            CustomerData customerData1 = new CustomerData();
            customerData1.setName(String.valueOf(binding.upCustName));
            customerData1.setPhone(String.valueOf(binding.upCustPhone));
           customerData1.setGender(String.valueOf(binding.upCustGender));
            customerData1.setEmail(String.valueOf(binding.upCustEmail));
            customerData1.setAddress(String.valueOf(binding.upCustAddress));
            customerData1.setDob(String.valueOf(binding.upCustDob));

            customerData1.setDate(date_update);



            customerData1.setId(id);

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            UserDAO userDAO = userDatabase.userDAO();
            userDAO.UpdateCustomer(customerData1);

            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();

            customerData1.setName(binding.upCustName.getText().toString().trim());
            customerData1.setPhone(binding.upCustPhone.getText().toString().trim());
            customerData1.setEmail(binding.upCustEmail.getText().toString().trim());
            customerData1.setAddress(binding.upCustAddress.getText().toString().trim());
            customerData1.setDob(binding.upCustDob.getText().toString().trim());
            customerData1.setGender(binding.upCustGender.getText().toString().trim());

            customerData1.setDate(date_update);


            userDAO.UpdateCustomer(customerData1);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            startActivity(new Intent(getApplicationContext(),View_Customer.class));
            Toast.makeText(Update_CustomerActivity.this, "Report Update", Toast.LENGTH_SHORT).show();
        }
    }



}