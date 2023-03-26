package com.example.systempos.LoginForm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.ims.ImsMmTelManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.systempos.MainActivity;
import com.example.systempos.R;
import com.example.systempos.User.DefaultUser;
import com.example.systempos.User.UserData;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityLoginBinding;

import java.io.File;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    EditText userEmail,userPassword;



    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    UserDAO userDAO;
    File file;

    public static final String SHARE_NAME = "isLogin";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USERPOSITION = "userposition";
    public static final String KEY_USERPROFILE = "profile";



    List<UserData> userDataList;

    String USER_POSITION;
    String USER_IMAGE;
    String USER_NAME;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();


        binding.btnloginForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.loginUsername.getText().toString();
                String password = binding.loginPassword.getText().toString();

                userDataList = userDAO.loginAccount(username,password);

                //Default User
                if(username.isEmpty()){
                    userEmail.setError("Requird");
                }else if (password.isEmpty()){
                    userPassword.setError("Requird");
                } else if (username.equals("Admin") && password.equals("123")) {
                    sharedPreferences = getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME,"username");
                    editor.putString(KEY_USERPROFILE,"Admin");
                    editor.putBoolean("haslogin_default",true);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(LoginActivity.this, "Please Check Email and Password", Toast.LENGTH_SHORT).show();
                }

                for(int i = 0; i< userDataList.size(); i++){
                    if(username.equals(userDataList.get(i).getName()) && password.equals(userDataList.get(i).getPassword())){
                        if(userDataList.get(i).isAdmin()){
                            USER_POSITION ="Admin";
                        }
                        if(userDataList.get(i).isChashier()){
                            USER_POSITION = "Cashire";
                        }
                        if(userDataList.get(i).isManager()){
                            USER_POSITION ="Manager";
                        }

                        sharedPreferences = getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
                        USER_NAME = userDataList.get(i).getName();
                        USER_IMAGE = userDataList.get(i).getImage();
                        USER_POSITION = userDataList.get(i).getPermission();

                        editor = sharedPreferences.edit();

                        editor.putString(KEY_USERNAME,USER_NAME);
                        editor.putString(KEY_USERPOSITION,USER_POSITION);
                        editor.putString(KEY_USERPROFILE,USER_IMAGE);
                        editor.putBoolean("haslogin",true);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }


            }
        });

//
//
//
//        sharedPreferences = this.getSharedPreferences("login",MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//
//        if (sharedPreferences.getString("isLogin","false").equals("yes")){
//            openDash();
//        }
//
//        userEmail = findViewById(R.id.login_username);
//        userPassword = findViewById(R.id.login_password);





    }
//    public void validateData(){
//        username = userEmail.getText().toString();
//        password = userPassword.getText().toString();
//
//        if(username.isEmpty()){
//            userEmail.setError("Requird");
//            userEmail.requestFocus();
//        }else if(password.isEmpty()){
//            userPassword.setError("Requird");
//            userPassword.requestFocus();
//        } else if (username.equals("Admin") && password.equals("123")) {
//            editor.putString("isLogin","yes");
//            editor.putString("username",username).apply();
//            editor.commit();
//            openDash();
//        }else {
//            Toast.makeText(this, "Please check email and password", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void openDash(){
//
//        startActivity(new Intent(LoginActivity.this,MainActivity.class));
//        finish();
//    }
}