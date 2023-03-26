package com.example.systempos.location;

import android.app.Dialog;
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


import com.example.systempos.Product.ViewAll_item;
import com.example.systempos.R;
import com.example.systempos.Repository.LocationRepository;
import com.example.systempos.ViewModel.LocationViewModel;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityLocationBinding;

import java.util.List;

public class LocationActivity extends AppCompatActivity {

    ActivityLocationBinding binding;

    UserDatabase userDatabase;
    UserDAO userDAO;

    RecyclerView recyclerView;

    LocationData locationData;
    LocationRepository locationRepository;
    LocationViewModel locationViewModel;
    Dialog dialog;
    AdapterLocation adapterLocation;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.showLocationitem);
        locationData = new LocationData();


        //Live data
        locationViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LocationViewModel.class);
        locationViewModel.getAllLocationLiveData().observe(LocationActivity.this, new Observer<List<LocationData>>() {
            @Override
            public void onChanged(List<LocationData> locationData) {
                if(locationData != null){
                    adapterLocation = new AdapterLocation(locationData);
                    adapterLocation.setLocation(locationData);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapterLocation);
                }

            }
        });



        //add location
        binding.btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.locAddName.getText().toString();
                String nameKH = binding.locAddName1.getText().toString();

                if(TextUtils.isEmpty(name)){
                    binding.locAddName.setError("Requird");
                } else if (TextUtils.isEmpty(nameKH)) {
                    binding.locAddName1.setError("Requird");
                }else {
                    locationData.setLocName(name);
                    locationData.setLocName1(nameKH);
                    locationViewModel.InsertLocation(locationData);
                    BuildDialogsaveSuccess();
                    binding.locAddName.setText("");
                    binding.locAddName1.setText("");
                    dialog.show();
                }
            }
        });

        binding.floatlocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               binding.btnAddLocation.setVisibility(View.VISIBLE);
               binding.showLocationActivity.setVisibility(View.GONE);

            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnAddLocation.setVisibility(View.GONE);
                binding.showLocationActivity.setVisibility(View.VISIBLE);
            }
        });


    }

    //Dialog save success
    private void BuildDialogsaveSuccess(){
        dialog  = new Dialog(LocationActivity.this);
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


    //Adapter Location
    public class AdapterLocation extends RecyclerView.Adapter<AdapterLocation.MyviewHolder>{

        List<LocationData> locationData;

        public AdapterLocation(List<LocationData> locationData) {
            this.locationData = locationData;
        }

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_show_location,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
            holder.name.setText(String.valueOf(locationData.get(position).getLocName()));
            holder.name1.setText(String.valueOf(locationData.get(position).getLocName1()));

            holder.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries().build();
                    userDAO = userDatabase.userDAO();
                    userDatabase.userDAO().DeleteLocationById(locationData.get(position).getLocID());
                    locationData.remove(position);
                    notifyDataSetChanged();
                }
            });

            //Update location
            final LocationData locationData1 = locationData.get(position);
            if(locationData1 == null){
                return;
            }

            holder.cardloc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),Update_Location_Activity.class);

                    intent.putExtra("location",locationData1);

                    view.getContext().startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    Toast.makeText(LocationActivity.this, "Update", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return locationData.size();
        }

        public void setLocation(List<LocationData> locationDataList){
            locationData = locationDataList;
            notifyDataSetChanged();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder{

            TextView name,name1;
            ImageView imgupdate,imgdelete;

            CardView cardloc;
            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.nameloc);
                name1 = itemView.findViewById(R.id.name1loc);
//                imgupdate = itemView.findViewById(R.id.img_loc_update);
                imgdelete = itemView.findViewById(R.id.img_loc_delete);
                cardloc = itemView.findViewById(R.id.cardLocation);
            }
        }
    }
}