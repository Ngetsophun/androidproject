package com.example.systempos.Product;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import com.example.systempos.CurrentDateHelper;
import com.example.systempos.R;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityUpdateProductBinding;
import com.example.systempos.location.LocationData;
import com.example.systempos.model.CatogoryData;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UpdateProduct extends AppCompatActivity {

   ActivityUpdateProductBinding binding;

    UserDatabase userDatabase;

    Boolean IS_UPDATE = false;

    ProductData productData;
    UserDAO userDAO;
    File file;
    int id;
    String loc_name;

    String cato_name;
    Boolean is_upload = false;
    Uri uri;
    String imagePath;

    List<LocationData> locationDataList;
    List<CatogoryData>catogoryDataList;
    AutoCompleteTextView autoCompleteTextView_UpCato,autoCompleteTextView_UpLocat;

    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUpdateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntenData();

        autoCompleteTextView_UpCato = findViewById(R.id.Spinner_Up_pro_Cato);
        autoCompleteTextView_UpLocat = findViewById(R.id.Spinner_Up_Pro_location);

        //show data for select catogory
        userDAO =userDatabase.getUserDatabase(getApplicationContext()).userDAO();

        catogoryDataList = userDAO.getAllCatoFuture();
        locationDataList = userDAO.getAllLocationFuture();

        ArrayList<String> catogoryArrayList =new ArrayList<String>();
        for(int i=0; i<catogoryDataList.size(); i++){
            catogoryArrayList.add(catogoryDataList.get(i).getCatoname());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.store_all_catogory,R.id.storeCatogory,catogoryArrayList);
        autoCompleteTextView_UpCato.setAdapter(arrayAdapter);


        ArrayList<String> locationArrayList = new ArrayList<>();
        for(int k=0; k< locationDataList.size(); k++){
            locationArrayList.add(locationDataList.get(k).getLocName());
        }
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this,R.layout.store_new_item,R.id.storetext,locationArrayList);
        autoCompleteTextView_UpLocat.setAdapter(arrayAdapter1);


        autoCompleteTextView_UpCato.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cato_name = catogoryDataList.get(i).getCatoname();
            }
        });

        autoCompleteTextView_UpLocat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loc_name = locationDataList.get(i).getLocName();
            }
        });

        binding.btnUpProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IS_UPDATE == false){
                    UpSaveProduct saveProduct = new UpSaveProduct();
                    saveProduct.execute();
                    BuildDialogsaveSuccess();
                }else {
                    UpDateItemProduct upDateItemProduct = new UpDateItemProduct();
                    upDateItemProduct.execute();

                }
            }
        });


        binding.chooseUpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                is_upload = true;
                ChooseImage();

            }
        });

        binding.btnUpScanBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scancode();
            }
        });

    }
    private void getIntenData(){
        if(getIntent().hasExtra("product")){
            IS_UPDATE = true;
            binding.btnUpProduct.setText("UPDATE");
            productData = (ProductData) getIntent().getSerializableExtra("product");

            id = productData.getProID();
            binding.UpProBarcode.setText(String.valueOf(productData.getProBarcode()));
            binding.UpProName.setText(String.valueOf(productData.getProname()));
            binding.UpProNameKH.setText(String.valueOf(productData.getPronameKh()));

            binding.UpProQtys.setText(String.valueOf(productData.getProqty()));
            binding.UpProCost.setText(String.valueOf(productData.getProcost()));
            binding.UpProPrice.setText(String.valueOf(productData.getProprice()));
            binding.UpProTax.setText(String.valueOf(productData.getProtax()));

            Glide.with(this).load(productData.getImage()).into(binding.chooseUpImage);

            imagePath = productData.getImage();


        }else {
            IS_UPDATE = false;
            binding.btnUpProduct.setText("SAVE");
        }


    }

    //save Dialog_success
    private void BuildDialogsaveSuccess(){
        dialog  = new Dialog(UpdateProduct.this);
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



    class UpSaveProduct extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            ProductData productData = new ProductData();

            productData.setCatoID(cato_name);
            productData.setLocID(loc_name);

            productData.setProBarcode(binding.UpProBarcode.getText().toString());
            productData.setProname(binding.UpProName.getText().toString());
            productData.setPronameKh(binding.UpProNameKH.getText().toString());

            productData.setProqty(Integer.parseInt(binding.UpProQtys.getText().toString()));
            productData.setProcost(Double.parseDouble(binding.UpProCost.getText().toString()));
            productData.setProprice(Double.parseDouble(binding.UpProPrice.getText().toString()));
            productData.setProtax(Double.parseDouble(binding.UpProTax.getText().toString()));
//
            productData.setImage(file.getPath());
            productData.setDate(CurrentDateHelper.getCurrentDate());

            userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
            userDAO.InsertProduct(productData);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

//            startActivity(new Intent(getApplicationContext(),ViewAll_item.class));
            Toast.makeText(UpdateProduct.this, "Save Update", Toast.LENGTH_SHORT).show();


        }
    }
    class UpDateItemProduct extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            double tax = Double.parseDouble(binding.UpProTax.getText().toString());

            ProductData productData = new ProductData();
            userDAO = userDatabase.getUserDatabase(getApplicationContext()).userDAO();


            if(is_upload == true){

                productData.setCatoID(cato_name);
                productData.setLocID(loc_name);
                productData.setProID(id);

                productData.setProname(binding.UpProName.getText().toString());
                productData.setPronameKh(binding.UpProNameKH.getText().toString());
                productData.setProqty(Integer.parseInt(binding.UpProQtys.getText().toString()));
                productData.setProBarcode(binding.UpProBarcode.getText().toString());
                productData.setProprice(Double.parseDouble(binding.UpProPrice.getText().toString()));
                productData.setProcost(Double.parseDouble(binding.UpProCost.getText().toString()));
                productData.setProtax(tax);
                productData.setDate(CurrentDateHelper.getCurrentDate());

                productData.setImage(file.getPath());

                userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
                userDAO.UpdateProduct(productData);
            }else{
                is_upload = false;
                productData.setCatoID(cato_name);
                productData.setLocID(loc_name);
                productData.setProID(id);

                productData.setProname(binding.UpProName.getText().toString());
                productData.setPronameKh(binding.UpProNameKH.getText().toString());
                productData.setProqty(Integer.parseInt(binding.UpProQtys.getText().toString()));
                productData.setProBarcode(binding.UpProBarcode.getText().toString());
                productData.setProprice(Double.parseDouble(binding.UpProPrice.getText().toString()));
                productData.setProcost(Double.parseDouble(binding.UpProCost.getText().toString()));
                productData.setProtax(tax);
                productData.setDate(CurrentDateHelper.getCurrentDate());

                productData.setImage(imagePath);

                userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
                userDAO.UpdateProduct(productData);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            startActivity(new Intent(getApplicationContext(), ViewAll_item.class));
            Toast.makeText(UpdateProduct.this, "Record Update", Toast.LENGTH_SHORT).show();

        }
    }


    private  void Scancode(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setPrompt("Scan code");
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            if(intentResult.getContents() == null){
                Toast.makeText(this, "No code", Toast.LENGTH_SHORT).show();
            }else {
               // binding.upBarcodePro.setText(intentResult.getContents());
                Toast.makeText(this, "Code = " + intentResult.getContents(), Toast.LENGTH_SHORT).show();
            }
        }
    }

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

                    binding.chooseUpImage.setImageURI(uri);
                }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                    Toast.makeText(this, "No image pick", Toast.LENGTH_SHORT).show();
                }
            });
}