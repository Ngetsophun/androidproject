package com.example.systempos.User;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityUpdateUserBinding;
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.File;
import java.util.Calendar;

public class UpdateUser extends AppCompatActivity {

    ActivityUpdateUserBinding binding;

    Boolean IS_UPDATE = false;
    UserDatabase userDatabase;
    Boolean is_upload = false;
    String date = "";
    File file;
    Boolean IS_Admin = false;
    Boolean IS_Cashier = false;
    Boolean IS_Insert = false;
    Boolean IS_Update = false;
    Boolean IS_Delete = false;

    Boolean IS_Allow = false;
    Boolean IS_Views = false;

    Boolean Male = false;
    Boolean Female = false;


    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntenData();


        binding.upUserAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.upUserAdmin.isChecked()){
                    binding.upUserManager.setEnabled(false);
                    binding.upUserCashier.setEnabled(false);
                }
                else {
                    binding.upUserManager.setEnabled(true);
                    binding.upUserCashier.setEnabled(true);
                }

            }
        });
        binding.upUserManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.upUserManager.isChecked()){
                    binding.upUserCashier.setEnabled(false);
                    binding.upUserAdmin.setEnabled(false);
                }
                else {
                    binding.upUserCashier.setEnabled(true);
                    binding.upUserAdmin.setEnabled(true);
                }
            }
        });
        binding.upUserCashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.upUserCashier.isChecked()){
                    binding.upUserManager.setEnabled(false);
                    binding.upUserAdmin.setEnabled(false);

                    binding.upCheckDelete.setEnabled(false);
                    binding.upCheckUpdate.setEnabled(false);
                    binding.upCheckInsert.setEnabled(false);
                    binding.upCheckView.setEnabled(false);
                    binding.upCheckAllow.setEnabled(false);
                }
                else {
                    binding.upUserManager.setEnabled(true);
                    binding.upUserAdmin.setEnabled(true);

                    binding.upCheckDelete.setEnabled(true);
                    binding.upCheckUpdate.setEnabled(true);
                    binding.upCheckInsert.setEnabled(true);
                    binding.upCheckView.setEnabled(true);
                    binding.upCheckAllow.setEnabled(true);
                }
            }
        });


       binding.btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IS_UPDATE == false){
                    SaveUser saveUser = new SaveUser();
                    saveUser.execute();
                }else {
                    UpdateUserClass updateUserClass = new UpdateUserClass();
                    updateUserClass.execute();
                }
            }
        });

       binding.upCheckSale.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

       binding.upUserImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ChooseImageUpdate();
               is_upload = true;
           }
       });


       //Select date
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.upUserDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        binding.upUserDob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



    }
    private void ChooseImageUpdate(){
        launcher.launch(
                ImagePicker.Companion.with(this)
                        .maxResultSize(1080,1080, true)
                        .crop()
                        .galleryOnly()
                        .createIntent()
        );
    }
    ActivityResultLauncher<Intent> launcher=
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                if(result.getResultCode()==RESULT_OK){

                    assert result.getData() != null;

                    Uri uri=result.getData().getData();

                    file = new File(uri.getPath());

                    binding.upUserImage.setImageURI(uri);
                }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                    Toast.makeText(this, "No image pick", Toast.LENGTH_SHORT).show();
                }
            });

    private void getIntenData(){
        if(getIntent().hasExtra("user")){
            IS_UPDATE = true;
            binding.btnUpdateUser.setText("UPDATE");
            userData = (UserData) getIntent().getSerializableExtra("user");

            binding.upUsername.setText(String.valueOf(userData.getName()));
            binding.upUserPassword.setText(String.valueOf(userData.getPassword()));
            binding.upUserAddress.setText(String.valueOf(userData.getAddress()));
            binding.upUserDob.setText(String.valueOf(userData.getDob()));

            Glide.with(this).load(userData.getImage()).into(binding.upUserImage);


//            if(userData.IS_Admin() == false){
//                IS_Admin = false;
//            }


            if(IS_Admin == false){
                IS_Admin = false;
            }else{
                IS_Admin =true;
            }



            binding.upUserAdmin.setChecked(IS_Admin);
            if(binding.upUserAdmin.isChecked()){
                IS_Admin = true;
            }



        }else {
            IS_UPDATE = false;
            binding.btnUpdateUser.setText("SAVE");
        }
    }




    //class save
    class SaveUser extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            UserData userData1 = new UserData();
            userData1.setName(String.valueOf(binding.upUsername));
            userData1.setPassword(String.valueOf(binding.upUserPassword));
            userData1.setAddress(String.valueOf(binding.upUserAddress));
            userData1.setDob(String.valueOf(binding.upUserDob));


            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

            UserDAO userDAO = userDatabase.userDAO();
            userDAO.InsertUser(userData1);

            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();
            userData1.setName(binding.upUsername.getText().toString().trim());
            userData1.setPassword(binding.upUserPassword.getText().toString().trim());
            userData1.setAddress(binding.upUserAddress.getText().toString().trim());
            userData1.setDob(binding.upUserDob.getText().toString().trim());


            userDAO.InsertUser(userData1);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            startActivity(new Intent(getApplicationContext(),ViewUser.class));
            Toast.makeText(UpdateUser.this, "Save", Toast.LENGTH_SHORT).show();
            super.onPostExecute(unused);
        }
    }

    //class Update
    class UpdateUserClass extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            UserData userData1 = new UserData();
            userData1.setName(String.valueOf(binding.upUsername));
            userData1.setPassword(String.valueOf(binding.upUserPassword));
            userData1.setAddress(String.valueOf(binding.upUserAddress));
            userData1.setDob(String.valueOf(binding.upUserDob));

            userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

            UserDAO  userDAO = userDatabase.userDAO();
            userDAO.UpdaAllteUser(userData1);

            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();
            userData1.setName(binding.upUsername.getText().toString().trim());
            userData1.setPassword(binding.upUserPassword.getText().toString().trim());
            userData1.setAddress(binding.upUserAddress.getText().toString().trim());
            userData1.setDob(binding.upUserDob.getText().toString().trim());


            userDAO.UpdaAllteUser(userData1);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            startActivity(new Intent(getApplicationContext(),ViewUser.class));
            Toast.makeText(UpdateUser.this, "Save", Toast.LENGTH_SHORT).show();
            super.onPostExecute(unused);
        }
    }

}