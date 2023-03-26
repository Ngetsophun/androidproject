package com.example.systempos.Product;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.systempos.CurrentDateHelper;
import com.example.systempos.R;
import com.example.systempos.User.ViewUser;
import com.example.systempos.ViewModel.ProductViewModel;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityViewAllItemBinding;
import com.example.systempos.location.LocationData;
import com.example.systempos.model.CatogoryData;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewAll_item extends AppCompatActivity {

    ActivityViewAllItemBinding binding;
    RecyclerView recyclerView;
    UserDatabase userDatabase;
    UserDAO userDAO;

    int id;
    String catoID;
    String locID;

    File file;

    List<LocationData> locationData;
    List<CatogoryData> catogoryData;

    ProductViewModel productViewModel;
    AdapterProduct adapterProduct;
    ProductData productData;
    AutoCompleteTextView autoCompleteTextViewCato,autoCompleteTextViewLocat;
    Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAllItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        productData = new ProductData();
        recyclerView = findViewById(R.id.recycler_Pro_View);
        autoCompleteTextViewCato = findViewById(R.id.Spinner_select_Cato);
        autoCompleteTextViewLocat = findViewById(R.id.Spinner_Add_location);

        ImageView imgBack = findViewById(R.id.img_back_product);



        userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        userDAO = userDatabase.userDAO();

        catogoryData = userDAO.getAllCatoFuture();
        locationData = userDAO.getAllLocationFuture();

        ArrayList<String> catogoryArrayList =new ArrayList<String>();

        for(int i= 0; i< catogoryData.size();i++){
            catogoryArrayList.add(catogoryData.get(i).getCatoname());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.store_all_catogory,R.id.storeCatogory,catogoryArrayList);
        autoCompleteTextViewCato.setAdapter(arrayAdapter);


        ArrayList<String> locationArraylist = new ArrayList<String>();
        for(int k=0; k<locationData.size(); k++){
            locationArraylist.add(locationData.get(k).getLocName());
        }
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this,R.layout.store_new_item,R.id.storetext,locationArraylist);
        autoCompleteTextViewLocat.setAdapter(arrayAdapter1);



        //tool bar
////
//        Toolbar toolbar = findViewById(R.id.app_bar_product);
//        setSupportActionBar(toolbar);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//-------------------------------

        //live data product

        productViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProductViewModel.class);
        productViewModel.getAllProductLiveData().observe(ViewAll_item.this, new Observer<List<ProductData>>() {
            @Override
            public void onChanged(List<ProductData> productData) {
                if(productData != null){
                    adapterProduct = new AdapterProduct(productData,getBaseContext());
                    adapterProduct.setProduct(productData);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapterProduct);
                }
            }
        });




        //Add New Product
        binding.btnaddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Productcode = binding.ProductBarcode.getText().toString().trim();
                String Productname = binding.ProductName.getText().toString().trim();
                String ProductnameKH = binding.ProductNameKH.getText().toString().trim();
                String ProductQty =binding.ProductQty.getText().toString();
                String ProductCost = binding.ProductCost.getText().toString().trim();
                String ProductPrice = binding.ProductPrice.getText().toString().trim();
                String ProductTax = binding.ProductTax.getText().toString().trim();

                try {
                    if(file.getPath() == null){
                        Toast.makeText(ViewAll_item.this, "Choose Image", Toast.LENGTH_SHORT).show();
                    }else{
                        if(TextUtils.isEmpty(Productcode)){
                            binding.ProductBarcode.setError("Requird");
                        }
                        else if(TextUtils.isEmpty(Productname)){
                            binding.ProductName.setError("Requird");
                        } else if (TextUtils.isEmpty(ProductnameKH)) {
                            binding.ProductNameKH.setError("Requird");
                        } else if (TextUtils.isEmpty(ProductQty)) {
                            binding.ProductQty.setError("Requird");
                        } else if (TextUtils.isEmpty(ProductCost)) {
                            binding.ProductCost.setError("Requird");
                        }  else if (TextUtils.isEmpty(ProductPrice)) {
                            binding.ProductPrice.setError("Requird");
                        }
                        else if (TextUtils.isEmpty(ProductTax)) {
                            binding.ProductTax.setError("Requird");
                        }else{
                            productData.setProname(Productname);
                            productData.setProBarcode(Productcode);
                            productData.setPronameKh(ProductnameKH);
                            productData.setProqty(Integer.parseInt(ProductQty));
                            productData.setProcost(Double.parseDouble(ProductCost));
                            productData.setProprice(Double.parseDouble(ProductPrice));
                            productData.setProtax(Double.parseDouble(ProductTax));
                            productData.setImage(file.getPath());
                            productData.setDate(CurrentDateHelper.getCurrentDate());

                            productData.setCatoID(catoID);
                            productData.setLocID(locID);
                            productViewModel.InsertProduct(productData);

                            BuildDialogsaveSuccess();

                            binding.ProductBarcode.setText("");
                            binding.ProductName.setText("");
                            binding.ProductNameKH.setText("");
                            binding.ProductQty.setText("");
                            binding.ProductCost.setText("");
                            binding.ProductPrice.setText("");
                            binding.ProductTax.setText("");
                            binding.SpinnerSelectCato.setText("");
                            binding.SpinnerAddLocation.setText("");
                            dialog.show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(ViewAll_item.this, "Choose Image", Toast.LENGTH_SHORT).show();

                }

            }

        });
        autoCompleteTextViewCato.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                catoID = catogoryData.get(i).getCatoname();

            }
        });

        autoCompleteTextViewLocat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                locID = locationData.get(i).getLocName();
            }
        });


        binding.btnScanBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scanCode();
            }
        });


        //Add Image
        binding.chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseImage();
            }
        });



        binding.floatingViewPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.ActivityShowProduct.setVisibility(View.GONE);
                binding.ActivityAddProduct.setVisibility(View.VISIBLE);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.ActivityShowProduct.setVisibility(View.VISIBLE);
                binding.ActivityAddProduct.setVisibility(View.GONE);
            }
        });


        binding.imgSearchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.BarSearch.setVisibility(View.GONE);
                binding.BarCloseSearch.setVisibility(View.VISIBLE);
            }
        });
        binding.imgCloseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.BarSearch.setVisibility(View.VISIBLE);
                binding.BarCloseSearch.setVisibility(View.VISIBLE);
            }
        });


        ///search
        binding.edsearchlistProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapterProduct.getFilter().filter(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
    //dialog save success
    private void BuildDialogsaveSuccess(){
        dialog  = new Dialog(ViewAll_item.this);
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

                    Uri uri=result.getData().getData();

                    file = new File(uri.getPath());

                    binding.chooseImage.setImageURI(uri);
                }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                    Toast.makeText(this, "No image pick", Toast.LENGTH_SHORT).show();
                }
            });




    private void scanCode(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setPrompt("Scan code");
        intentIntegrator.initiateScan();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode, data);
        if(intentResult != null){
            if(intentResult.getContents() == null){
                Toast.makeText(this, "No code", Toast.LENGTH_SHORT).show();
            }else{
                binding.ProductBarcode.setText(intentResult.getContents());
                Toast.makeText(this, "Code = " + intentResult.getContents(), Toast.LENGTH_SHORT).show();

            }
        }



    }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.product_search,menu);
//        MenuItem item = menu.findItem(R.id.search);
//
//        SearchView searchView1 = (SearchView) item.getActionView();
//        searchView1.setQueryHint("Search here");
//
//        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return false;
//            }
//        });
//
//
//
//
//
//        return super.onCreateOptionsMenu(menu);
//    }
}