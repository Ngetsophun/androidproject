package com.example.systempos.User;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;

import com.example.systempos.CurrentDateHelper;
import com.example.systempos.R;
import com.example.systempos.ViewModel.UserViewModel;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityViewUserBinding;
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewUser extends AppCompatActivity {
    ActivityViewUserBinding binding;
    UserDAO userDAO;
    UserDatabase userDatabase;
    UserData userData;
    RecyclerView recyclerView;
    String date = "";

    Boolean Admin = false;
    Boolean Cashier = false;

    Boolean Allow = false;
    Boolean Views = false;

    File file;

    Dialog dialog;

    AdapterUser adapterUser;
    UserViewModel userViewModel;

    EditText username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewUserBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            userData = new UserData();

            ImageView imageView = findViewById(R.id.chooseimg_add_user);


        //live data
        userViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserViewModel.class);
      userViewModel.getAllUserLiveData().observe(ViewUser.this, new Observer<List<UserData>>() {
          @Override
          public void onChanged(List<UserData> userData) {
              if(userData != null){
                  adapterUser = new AdapterUser(userData,getBaseContext());
                  adapterUser.setUser(userData);
                  binding.showUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                  binding.showUser.setAdapter(adapterUser);
              }
          }
      });


        binding.addUserAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.addUserAdmin.isChecked()){
                    binding.addUserManager.setEnabled(false);
                    binding.addUserCashier.setEnabled(false);
                }
                else {
                    binding.addUserManager.setEnabled(true);
                    binding.addUserCashier.setEnabled(true);
                }

            }
        });
        binding.addUserManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.addUserManager.isChecked()){
                    binding.addUserAdmin.setEnabled(false);
                    binding.addUserCashier.setEnabled(false);
                }else{
                    binding.addUserAdmin.setEnabled(true);
                    binding.addUserCashier.setEnabled(true);
                }
            }
        });
        binding.addUserCashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.addUserCashier.isChecked()){
                    binding.addUserManager.setEnabled(false);
                    binding.addUserAdmin.setEnabled(false);

                    binding.addCheckDelete.setEnabled(false);
                    binding.addCheckUpdate.setEnabled(false);
                    binding.addCheckInsert.setEnabled(false);
                    binding.addCheckView.setEnabled(false);
                    binding.addCheckAllow.setEnabled(false);

                }else {
                    binding.addUserManager.setEnabled(true);
                    binding.addUserAdmin.setEnabled(true);

                    binding.addCheckDelete.setEnabled(true);
                    binding.addCheckUpdate.setEnabled(true);
                    binding.addCheckInsert.setEnabled(true);
                    binding.addCheckView.setEnabled(true);
                    binding.addCheckAllow.setEnabled(true);
                }
            }
        });

        binding.btnfloatuser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   binding.AddUserActivity.setVisibility(View.VISIBLE);
                   binding.ShowUserActivity.setVisibility(View.GONE);
                }
            });
            binding.imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.AddUserActivity.setVisibility(View.GONE);
                    binding.ShowUserActivity.setVisibility(View.VISIBLE);
                }
            });

            //select choose date
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        binding.addUserDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        binding.addUserDob.setText(date);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        //Add user
        binding.btnaddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.addUsername.getText().toString().trim();
                String pass = binding.addUserPassword.getText().toString().trim();
                String address = binding.addUserAddress.getText().toString();
                String dob = binding.addUserDob.getText().toString().trim();


                String role = "";
                if(binding.addUserAdmin.isChecked()){
                    role = "Admin";

                }
                if(binding.addUserManager.isChecked()){
                    role = "Manager";
                }if(binding.addUserCashier.isChecked()){
                    role = "Cashier";
                }
//

                String gender = "";
                if(binding.addUserMale.isChecked()){
                    gender = "Male";
                }
                if(binding.addUserFemale.isChecked()){
                    gender  = "Female";
                }


                String permission = "";
                if(binding.addCheckView.isChecked()){
                    permission = "View";

                }



                if(TextUtils.isEmpty(name) ||(TextUtils.isEmpty(pass))|| (TextUtils.isEmpty(address))||(TextUtils.isEmpty(dob))) {
                    binding.addUsername.setError("Requird");
                    binding.addUserPassword.setError("Requird");
                    binding.addUserAddress.setError("Requird");
                    binding.addUserDob.setError("Requird");

                    if(TextUtils.isEmpty(name)){
                        binding.addUsername.setError("Requird");

                    }else if(TextUtils.isEmpty(pass)){
                        binding.addUserPassword.setError("Requird");

                    }else if(TextUtils.isEmpty(address)){
                        binding.addUserAddress.setError("Requird");

                    }else if(TextUtils.isEmpty(dob)){
                        binding.addUserDob.setError("Requird");

                    } else if(TextUtils.isEmpty(file.getPath())) {
                        imageView.setImageResource(R.drawable.add_image2);

                   }


                } else{
                    userData.setName(name);
                    userData.setPassword(pass);
                    userData.setGender(gender);
                    userData.setAddress(address);
                    userData.setRole(role);
                    userData.setDob(dob);
                    userData.setPermission(permission);
                    userData.setImage(file.getPath());
                    userData.setDate(CurrentDateHelper.getCurrentDate());

                    userViewModel.InsertUser(userData);


                    Toast.makeText(ViewUser.this, "Save Success", Toast.LENGTH_SHORT).show();
                    BuildDialogsaveSuccess();

                    binding.addUsername.setText("");
                    binding.addUserPassword.setText("");
                    binding.addUserAddress.setText("");
                    binding.addUserDob.setText("");
                    dialog.show();
                }
            }

        });

        binding.chooseimgAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImageAdd();
            }
        });




    }



    //add dialog animation
    private void BuildDialogsaveSuccess(){
        dialog  = new Dialog(ViewUser.this);
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

    //--------

    //choose Image
    private  void ChooseImageAdd(){
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

                    binding.chooseimgAddUser.setImageURI(uri);
                }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                    Toast.makeText(this, "No image pick", Toast.LENGTH_SHORT).show();
                }
            });





    //Adapter User
    public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyViewHolder>{

        List<UserData> userData;
        Context context;

        public AdapterUser(List<UserData> userData, Context context) {
            this.userData = userData;
            this.context = context;
        }



        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_user,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Glide.with(context).load(userData.get(position).getImage()).into(holder.showImageAddUser);

            holder.name.setText(String.valueOf(userData.get(position).getName()));
            holder.role.setText(String.valueOf(userData.get(position).getRole()));
            holder.address.setText(String.valueOf(userData.get(position).getAddress()));
            holder.date.setText(CurrentDateHelper.getCurrentDate());





//            delete
            holder.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userDatabase = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"bluedb")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration().build();

                    userDAO = userDatabase.userDAO();
                    userDAO.DeleteUserByid(userData.get(position).getId());
                    userData.remove(position);
                    notifyDataSetChanged();
                }
            });

            //Update
            final UserData userData1 = userData.get(position);
            if(userData1 == null){
                return;
            }

            holder.cardupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),UpdateUser.class);
                    intent.putExtra("user",userData1);
                    view.getContext().startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return userData.size();
        }

        public void setUser(List<UserData> userDatas){
            userData = userDatas;
            notifyDataSetChanged();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView name,role,address,date;
            ImageView imgdelete,showImageAddUser;

            CardView cardupdate;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.show_user_name);
                role = itemView.findViewById(R.id.Show_user_role);
                address = itemView.findViewById(R.id.Show_user_address);
                date = itemView.findViewById(R.id.Show_user_date);
                showImageAddUser = itemView.findViewById(R.id.show_img_user);


                imgdelete = itemView.findViewById(R.id.img_deleteUser);
                cardupdate = itemView.findViewById(R.id.cardShowUser);
            }
        }
    }

}