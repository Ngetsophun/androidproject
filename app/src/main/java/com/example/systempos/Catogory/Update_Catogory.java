package com.example.systempos.Catogory;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import com.example.systempos.R;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityUpdateCatogoryBinding;
import com.example.systempos.model.CatogoryData;

public class Update_Catogory extends AppCompatActivity {
    EditText name,name1;

    int id;
    UserDatabase userDatabase;
    Boolean IS_UPDATE = false;

    ActivityUpdateCatogoryBinding binding;
     CatogoryData catogoryData;
     Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateCatogoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntenData();

        binding.btnUpCatogory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IS_UPDATE == false){
                    SaveCatogory saveCatogory = new SaveCatogory();
                    saveCatogory.execute();
                    BuildDialogsaveSuccess();
                }
                else {
                    UpdateCatogory updateCatogory = new UpdateCatogory();
                    updateCatogory.execute();
                }

            }

        });


    }
    private void getIntenData(){
        if(getIntent().hasExtra("catogory")){
            IS_UPDATE = true;
            binding.btnUpCatogory.setText("UPDATE");

            catogoryData = (CatogoryData) getIntent().getSerializableExtra("catogory");
            binding.CatoName.setText(catogoryData.getCatoname());
            binding.CatoName1.setText(catogoryData.getCatoname1());

            id = catogoryData.getId();

        }else {
            IS_UPDATE = false;
            binding.btnUpCatogory.setText("SAVE");
        }
    }


    //Dialog save Success
    private void BuildDialogsaveSuccess(){
        dialog  = new Dialog(Update_Catogory.this);
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


    //Save Catogoty
    class SaveCatogory extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            CatogoryData catogoryData = new CatogoryData();
            catogoryData.setCatoname(String.valueOf(name));
            catogoryData.setCatoname1(String.valueOf(name1));
            catogoryData.setId(id);

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

            UserDAO userDAO = userDatabase.userDAO();
            userDAO.InsertCotogory(catogoryData);

            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();

            catogoryData.setCatoname(binding.CatoName.getText().toString().trim());
            catogoryData.setCatoname1(binding.CatoName1.getText().toString().trim());
            userDAO.InsertCotogory(catogoryData);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            startActivity(new Intent(getApplicationContext(),View_Catogoey.class));
            Toast.makeText(Update_Catogory.this, "Save Update", Toast.LENGTH_SHORT).show();
        }
    }

    //Update Catogory
    class UpdateCatogory extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            CatogoryData catogoryData = new CatogoryData();

            catogoryData.setCatoname(String.valueOf(name));
            catogoryData.setCatoname1(String.valueOf(name1));
            catogoryData.setId(id);

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

            UserDAO userDAO = userDatabase.userDAO();
            userDAO.UpdateCatogory(catogoryData);

            catogoryData.setId(id);
            catogoryData.setCatoname(binding.CatoName.getText().toString().trim());
            catogoryData.setCatoname1(binding.CatoName1.getText().toString().trim());

            //
            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();

            userDAO.UpdateCatogory(catogoryData);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            startActivity(new Intent(getApplicationContext(),View_Catogoey.class));
            Toast.makeText(Update_Catogory.this, "Update", Toast.LENGTH_SHORT).show();
        }
    }
}