package com.example.systempos.Payment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityUpdatePaymentBinding;

public class Update_PaymentActivity extends AppCompatActivity {

    EditText paymentType,Descrip;


    UserDatabase userDatabase;

    int id;
    Boolean IS_UPDATE = false;

    ActivityUpdatePaymentBinding binding;

    PaymentData paymentData;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getIntenData();

        binding.btnUpPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(IS_UPDATE == false){
                    savePayment savePayment = new savePayment();
                    savePayment.execute();
                }else {
                    UpdatePayment updatePayment = new UpdatePayment();
                    updatePayment.execute();
                }

            }
        });
    }

    private void getIntenData(){
        if(getIntent().hasExtra("payment")){
            IS_UPDATE = true;
            binding.btnUpPayment.setText("UPDATE");

            paymentData = (PaymentData) getIntent().getSerializableExtra("payment");
            binding.paymentUp.setText(paymentData.getPaymentType());
            binding.descrptionUp.setText(paymentData.getDecription());


            id = paymentData.getPayId();

        }else {
            IS_UPDATE = false;
            binding.btnUpPayment.setText("SAVE");
        }

    }


    //Save Class
    class savePayment extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            PaymentData paymentData = new PaymentData();
            paymentData.setPaymentType(String.valueOf(paymentType));
            paymentData.setDecription(String.valueOf(Descrip));

            paymentData.setPayId(id);

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

            UserDAO userDAO = userDatabase.userDAO();
            userDAO.InsertPayment(paymentData);

            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();

            paymentData.setPaymentType(binding.paymentUp.getText().toString().trim());
            paymentData.setDecription(binding.descrptionUp.getText().toString().trim());

//            customer.setDate_time_create(date_update);

            userDAO.InsertPayment(paymentData);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            startActivity(new Intent(getApplicationContext(),Show_PaymentActivity.class));
            Toast.makeText(Update_PaymentActivity.this, "Saved Update", Toast.LENGTH_SHORT).show();
        }
    }

    //Update Class
    class UpdatePayment extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            PaymentData paymentData = new PaymentData();
            paymentData.setPaymentType(String.valueOf(paymentType));
            paymentData.setDecription(String.valueOf(Descrip));

            paymentData.setPayId(id);

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

            UserDAO userDAO = userDatabase.userDAO();
            userDAO.UpdatePayment(paymentData);

            paymentData.setPayId(id);
            paymentData.setPaymentType(binding.paymentUp.getText().toString().trim());
            paymentData.setDecription(binding.descrptionUp.getText().toString().trim());

            //connect db
            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();
            //query update
            userDAO.UpdatePayment(paymentData);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            startActivity(new Intent(getApplicationContext(),Show_PaymentActivity.class));
            Toast.makeText(Update_PaymentActivity.this, "Repord Update", Toast.LENGTH_SHORT).show();
        }
   }
}
