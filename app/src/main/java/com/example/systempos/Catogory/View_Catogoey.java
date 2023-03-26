package com.example.systempos.Catogory;

import android.app.Dialog;
import android.content.Context;
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


import com.example.systempos.R;
import com.example.systempos.Sale.SaleData;
import com.example.systempos.User.ViewUser;
import com.example.systempos.ViewModel.CatogoryViewModel;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityViewCatogoeyBinding;
import com.example.systempos.model.CatogoryData;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.TemplatesHandler;

public class View_Catogoey extends AppCompatActivity {


    ActivityViewCatogoeyBinding binding;
    UserDatabase database;
    UserDAO userDAO;

    RecyclerView recyclerView;
    CatogoryData catogoryData;
    CatogoryViewModel catogoryViewModel;
    AdapterCatogory adapterCatogory;

    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewCatogoeyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.showCatogoryitem);
        catogoryData = new CatogoryData();


        //live data
        catogoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CatogoryViewModel.class);
        catogoryViewModel.getAllCatoLiveData().observe(View_Catogoey.this, new Observer<List<CatogoryData>>() {
            @Override
            public void onChanged(List<CatogoryData> catogoryData) {
                if(catogoryData != null){
                    adapterCatogory = new AdapterCatogory(catogoryData);
                    adapterCatogory.setCatogory(catogoryData);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapterCatogory);

                }
            }
        });





        //Add Catogoty
        binding.btnAddCatogory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.catoAddName.getText().toString();
                String name1 = binding.CatoAddName1.getText().toString();

                if(TextUtils.isEmpty(name)){
                    binding.catoAddName.setError("Requird");

                } else if (TextUtils.isEmpty(name1)) {
                    binding.CatoAddName1.setError("Requird");
                } else{
                    catogoryData.setCatoname(name);
                    catogoryData.setCatoname1(name1);
                    catogoryViewModel.InsertCatogory(catogoryData);

                    BuildDialogsaveSuccess();
                    binding.catoAddName.setText("");
                    binding.CatoAddName1.setText("");
                    dialog.show();
                }


            }
        });


        binding.floatCatogory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               binding.AddCatoActivity.setVisibility(View.VISIBLE);
               binding.showCatoActivity.setVisibility(View.GONE);
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.AddCatoActivity.setVisibility(View.GONE);
                binding.showCatoActivity.setVisibility(View.VISIBLE);
            }
        });

    }
    private void BuildDialogsaveSuccess(){
        dialog  = new Dialog(View_Catogoey.this);
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



    //Adapter Catogory
    public class AdapterCatogory extends RecyclerView.Adapter<AdapterCatogory.MyviewHolder>{

        List<CatogoryData> catogoryData;
        List<SaleData> saleDataList;

        public AdapterCatogory(List<CatogoryData> catogoryData) {
            this.catogoryData = catogoryData;
            this.saleDataList = new ArrayList<>();
        }

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_catogory,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
            holder.txtname.setText(String.valueOf(catogoryData.get(position).getCatoname()));
            holder.txtname1.setText(String.valueOf(catogoryData.get(position).getCatoname1()));

            //delete
            holder.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    database = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                    userDAO = database.userDAO();
                    userDAO.DeleteCatoByid(catogoryData.get(position).getId());
                    catogoryData.remove(position);
                    notifyDataSetChanged();
                }
            });

            final CatogoryData catogoryData1 = catogoryData.get(position);
                if(catogoryData1 == null){
                    return;
                }

             holder.cardcato.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     Intent intent = new Intent(view.getContext(), Update_Catogory.class);
                     intent.putExtra("catogory",catogoryData1);
                     view.getContext().startActivity(intent);

                     Toast.makeText(View_Catogoey.this, "Update Success", Toast.LENGTH_SHORT).show();
                 }
             });

        }

        @Override
        public int getItemCount() {
            return catogoryData.size();
        }

        public void setCatogory(List<CatogoryData> catogoryDataList){
            catogoryData = catogoryDataList;
            notifyDataSetChanged();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder{

            TextView txtname,txtname1;
            ImageView imgdelete;
            CardView cardcato;

            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                txtname = itemView.findViewById(R.id.namcato);
                txtname1 = itemView.findViewById(R.id.name1cato);
                imgdelete = itemView.findViewById(R.id.imgdelete);

                cardcato = itemView.findViewById(R.id.cardCatogoryitem);
            }
        }
    }


}