package com.example.systempos.Setting;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.systempos.Product.UpdateProduct;
import com.example.systempos.R;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityUpdateSettingBinding;
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.File;

public class Update_settingActivity extends AppCompatActivity {

    ActivityUpdateSettingBinding binding;
    UserDatabase userDatabase;
    UserDAO userDAO;
    Boolean IS_UPDATE =false;
     File file;
     int id;
     String imagePath;
     Uri uri;
     Boolean is_upload = false;
     SettingData settingData;

     Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();

        getIntenData();

       // BuildDialogsaveSuccess();

        binding.btnUpSettingUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IS_UPDATE == false){
                    UpSaveSetting upSaveSetting = new UpSaveSetting();
                    upSaveSetting.execute();
                }else {
                    UPUpdateSetting upUpdateSetting = new UPUpdateSetting();
                    upUpdateSetting.execute();
                }
            }
        });

        binding.UpSettingImagecopany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_upload = true;
                ChooseImage();
            }
        });
    }

    private void getIntenData(){
        if(getIntent().hasExtra("Setting")){
            IS_UPDATE = true;
            binding.btnUpSettingUpdate.setText("UPDATE");
            settingData = (SettingData) getIntent().getSerializableExtra("Setting");

            binding.UpSettingComName.setText(String.valueOf(settingData.getStoreName()));
            binding.UpSettingAddress.setText(String.valueOf(settingData.getStoreAddress()));
            binding.UpSettingNumber.setText(String.valueOf(settingData.getStoreNumber()));
            binding.UpSettingExchage.setText(String.valueOf(settingData.getStorrExchage()));

            Glide.with(this).load(settingData.getStoreImage()).into(binding.UpSettingImagecopany);

            imagePath = settingData.getStoreImage();

        }
        else {
            IS_UPDATE = false;
            binding.btnUpSettingUpdate.setText("SAVE");
        }


    }

    //save Dialog_success

    private void BuildDialogsaveSuccess(){
        dialog  = new Dialog(Update_settingActivity.this);
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

    //class Update

    class UpSaveSetting extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            SettingData settingData = new SettingData();
            settingData.setStoreNumber(binding.UpSettingNumber.getText().toString().trim());
            settingData.setStoreAddress(binding.UpSettingAddress.getText().toString().trim());
            settingData.setStoreName(binding.UpSettingComName.getText().toString().trim());
            settingData.setStorrExchage(Double.parseDouble(binding.UpSettingExchage.getText().toString()));
            settingData.setStoreImage(file.getPath());

            userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
            userDAO.InsertSetting(settingData);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }

    class UPUpdateSetting extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            SettingData settingData = new SettingData();
            userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();

            if(is_upload == true){
                settingData.setStoreID(id);
                settingData.setStoreNumber(binding.UpSettingNumber.getText().toString().trim());
                settingData.setStoreAddress(binding.UpSettingAddress.getText().toString().trim());
                settingData.setStoreName(binding.UpSettingComName.getText().toString().trim());
                settingData.setStorrExchage(Double.parseDouble(binding.UpSettingExchage.getText().toString()));
                settingData.setStoreImage(file.getPath());

                userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
                userDAO.UpdateSetting(settingData);

            }else {
                is_upload= false;
                settingData.setStoreID(id);
                settingData.setStoreNumber(binding.UpSettingNumber.getText().toString().trim());
                settingData.setStoreAddress(binding.UpSettingAddress.getText().toString().trim());
                settingData.setStoreName(binding.UpSettingComName.getText().toString().trim());
                settingData.setStorrExchage(Double.parseDouble(binding.UpSettingExchage.getText().toString()));
                settingData.setStoreImage(imagePath);

                userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
                userDAO.UpdateSetting(settingData);

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }




    //choose Image
    private void ChooseImage(){
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

                    uri=result.getData().getData();

                    file = new File(uri.getPath());

                    binding.UpSettingImagecopany.setImageURI(uri);
                }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                    Toast.makeText(this, "No image pick", Toast.LENGTH_SHORT).show();
                }
            });


}