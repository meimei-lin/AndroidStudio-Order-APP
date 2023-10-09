package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.bean.User;
import com.example.finalproject.database.SqlUser;

import java.util.ArrayList;

public class login extends AppCompatActivity {
    EditText useraccount, userpassword;
    Button userregister, signin;
    private SqlUser mSQlite;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        useraccount = (EditText) findViewById(R.id.inputaccount1);
        userpassword = (EditText) findViewById(R.id.inputpassword);
        userregister = (Button) findViewById(R.id.regB);
        signin = (Button) findViewById(R.id.loginB);
        mSQlite = new SqlUser(login.this);
    }

    public void onreg(View view) {
        Intent intent = new Intent(login.this, register.class);
        startActivity(intent);
    }

    public void onlogin(View view) {
        String account = useraccount.getText().toString().trim();
        String password = userpassword.getText().toString().trim();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
            ArrayList<User> data = mSQlite.getAllDATA();
            boolean user = false;
            for (int i = 0; i < data.size(); i++) {
                User userdata = data.get(i);   //用來存帳號數量
                if (account.equals(userdata.getAccount()) && password.equals(userdata.getPassword())) {
                    user = true;
                    break;
                } else {
                    user = false;
                }
            }
            if (user) {
                int user_id = getCurrentUserId(account);
                // 保存User_id到SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserId", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("user_id", user_id);
                editor.apply();

                Toast.makeText(login.this, "成功登入", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this, firstpage.class);
                intent.putExtra("username",account);
                intent.putExtra("password",password);

                startActivity(intent);
                finish();
            } else {
                Toast.makeText(login.this,"帳號密碼有誤",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(login.this, "帳號密碼不能為空", Toast.LENGTH_SHORT).show();
        }

        }
    private int getCurrentUserId(String account) {
        ArrayList<User> data = mSQlite.getAllDATA();
        for (int i = 0; i < data.size(); i++) {
            User userdata = data.get(i);
            if (account.equals(userdata.getAccount())) {
                return userdata.getId();
            }
        }
        return -1; // 返回-1代表沒找到目前user_id
    }


}