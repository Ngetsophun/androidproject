package com.example.systempos.location;

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
import com.example.systempos.databinding.ActivityUpdateLocationBinding;

public class Update_Location_Activity extends AppCompatActivity {

    EditText name,name1;
    ActivityUpdateLocationBinding binding;
    LocationData locationData;
    Boolean IS_UPDATE = false;

    UserDatabase userDatabase;
    Dialog dialog;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       getIntenData();

        binding.btnUpLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IS_UPDATE = false){
                    SaveLocation saveLocation = new SaveLocation();
                    saveLocation.execute();

                }else {
                    UpdateLocation updateLocation = new UpdateLocation();
                    updateLocation.execute();

                }

            }
        });

    }
    private void getIntenData(){
        if(getIntent().hasExtra("location")){
            IS_UPDATE  = true;
            binding.btnUpLoc.setText("UPDATE");
            locationData = (LocationData) getIntent().getSerializableExtra("location");
            binding.upLocName.setText(String.valueOf(locationData.getLocName()));
            binding.upLocNamekh.setText(String.valueOf(locationData.getLocName1()));
            id = locationData.getLocID();

        }else {
            IS_UPDATE = false;
            binding.btnUpLoc.setText("SAVE");
        }
    }
    class SaveLocation extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            LocationData locationData1 = new LocationData();
            locationData1.setLocName(String.valueOf(name));
            locationData1.setLocName1(String.valueOf(name1));

            locationData1.setLocID(id);
            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

            UserDAO userDAO = userDatabase.userDAO();
            userDAO.InsertLocation(locationData1);


            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();
            locationData1.setLocName(binding.upLocName.getText().toString().trim());
            locationData1.setLocName1(binding.upLocNamekh.getText().toString().trim());

            userDAO.InsertLocation(locationData1);



            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            startActivity(new Intent(getApplicationContext(),LocationActivity.class));
            Toast.makeText(Update_Location_Activity.this, "Save", Toast.LENGTH_SHORT).show();

        }
    }

    class UpdateLocation extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            LocationData locationData1 = new LocationData();
            locationData1.setLocName(String.valueOf(name));
            locationData1.setLocName1(String.valueOf(name1));

            locationData1.setLocID(id);

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

            UserDAO userDAO = userDatabase.userDAO();
            userDAO.UpdateAllLocatioon(locationData1);

            locationData1.setLocID(id);
            locationData1.setLocName(binding.upLocName.getText().toString().trim());
            locationData1.setLocName1(binding.upLocNamekh.getText().toString().trim());

            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();

            userDAO.UpdateAllLocatioon(locationData1);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            startActivity(new Intent(getApplicationContext(),LocationActivity.class));
            Toast.makeText(Update_Location_Activity.this, "Update", Toast.LENGTH_SHORT).show();
        }
    }

    //dialog save success
    private void BuildDialogsaveSuccess(){
        dialog  = new Dialog(Update_Location_Activity.this);
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

}

