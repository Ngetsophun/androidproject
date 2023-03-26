package com.example.systempos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.systempos.Drawer.LanguageFragment;
import com.example.systempos.Setting.SettingFragment;
import com.example.systempos.Exchange.ExchangeFragment;
import com.example.systempos.LoginForm.LoginActivity;
import com.example.systempos.User.ViewUser;
import com.example.systempos.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    RecyclerView gridView;

    List<HomePage> datahomepage;
    FrameLayout frameLayout;

    AlertDialog.Builder builder;
    CardView cardView;

    ActivityMainBinding binding;




    TextView username;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public static final String SHARE_NAME = "isLogin";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USERPOSITION = "userposition";
    public static final String KEY_USERPROFILE = "profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        frameLayout= findViewById(R.id.fragmMenu);



        getSupportFragmentManager().beginTransaction().replace(R.id.fragmMenu,new MenuFragment()).commit();



//        login share preferences
        sharedPreferences = this.getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString(SHARE_NAME,"false").equals("fale")){
            editor.putString(SHARE_NAME,"yes");


            editor.commit();
            openLogin();



        }



        //======================app_bar
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.Close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //header
       View headerView = binding.navigation.getHeaderView(0);
       HeaderDrawer(headerView);





        //Intent image to activity user
        ImageView img_headerUsername =  navigationView.getHeaderView(0).findViewById(R.id.img_hearder_drawer);
        img_headerUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ViewUser.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "Add new user", Toast.LENGTH_SHORT).show();
            }
        });





    }
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } //else {
//            AlertDialog.Builder adb = new AlertDialog.Builder(this);
//            adb.setTitle("Confire Exit?");
//            adb.setMessage("Are you sure you want to Exit?");
//            adb.setCancelable(false);
//            adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    finish();
//                }
//            });
//            adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
//
//            AlertDialog alertDialog = adb.create();
//            alertDialog.show();

//
//            super.onBackPressed();
       // }
    }


    public void openLogin(){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        item.setCheckable(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.setting:
                replaceFramgement(new SettingFragment());break;
            case R.id.About_US:
//                replaceFramgement(new About_US_Fragment());break;
                Intent intent1 = new Intent(MainActivity.this,About_US_Activity.class);
                startActivity(intent1);break;
            case R.id.Logout:

                findViewById(R.id.Logout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Logout");
                        builder.setMessage("Do you want to exit");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                editor.putString(SHARE_NAME,"false");
                                editor.commit();
                                openLogin();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                });
                break;
            case R.id.languages:
                replaceFramgement(new LanguageFragment());break;
            case R.id.dashboard:
                replaceFramgement(new MenuFragment());break;
            case R.id.exchage:
                replaceFramgement(new ExchangeFragment());break;





        }
        return true;
    }
   public  void replaceFramgement(Fragment fragment){
       FragmentManager fragmentManager = getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentTransaction.replace(R.id.fragmMenu,fragment);
       fragmentTransaction.commit();
   }

   public void HeaderDrawer(View headerView){
        sharedPreferences = getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        ImageView profileUser = headerView.findViewById(R.id.img_hearder_drawer);

        TextView username = headerView.findViewById(R.id.header_username);
        TextView userRole = headerView.findViewById(R.id.header_userRole);

        userRole.setText(sharedPreferences.getString(KEY_USERPOSITION,null));
        username.setText(sharedPreferences.getString(KEY_USERNAME,null));
        String Imagepath = sharedPreferences.getString(KEY_USERPROFILE,null);
       Glide.with(this).load(Imagepath).into(profileUser);
   }

}