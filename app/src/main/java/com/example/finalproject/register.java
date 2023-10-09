package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.bean.User;
import com.example.finalproject.database.SqlUser;

import java.util.ArrayList;

public class register extends AppCompatActivity implements View.OnClickListener {
    private Button clear,comfirm;
    private EditText userid,userpasswd,userpasswd1;
    private SqlUser mSQlite;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userid=(EditText)findViewById(R.id.inputaccount);
        userpasswd= (EditText) findViewById(R.id.inputpassword1);
        userpasswd1= (EditText) findViewById(R.id.inputpassword2);
        comfirm=(Button)findViewById(R.id.comfB);
        clear=(Button)findViewById(R.id.clearB);
        mSQlite = new SqlUser(register.this);
        comfirm.setOnClickListener(this);
        clear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.comfB) {
            String str = userid.getText().toString().trim();
            String str1 = userpasswd.getText().toString().trim();
            String str2 = userpasswd1.getText().toString().trim();
            if (TextUtils.isEmpty(str)) {
                Toast.makeText(register.this, "請輸入帳號", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(str1)) {
                Toast.makeText(register.this, "請輸入密碼", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(str2)) {
                Toast.makeText(register.this, "確認密碼不能為空", Toast.LENGTH_SHORT).show();
            } else if(str2.length()<8){
                Toast.makeText(register.this, "密碼太短了喔!", Toast.LENGTH_SHORT).show();
            } else if (str1.equals(str2)) {
                // 檢查用戶帳號是否已存在
                ArrayList<User> userList = mSQlite.getAllDATA();
                for (User user : userList) {
                    if (user.getAccount().equals(str)) {
                        Toast.makeText(register.this, "該帳號已存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                mSQlite.add(str,str1);
                Toast.makeText(register.this, "註冊成功!", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(register.this, login.class);
                startActivity(intent1);
            }
            else{
                Toast.makeText(register.this, "密碼不相同，請重新輸入", Toast.LENGTH_SHORT).show();
            }
        }else if (v.getId() == R.id.clearB) {
                userid.setText("");
                userpasswd.setText("");
                userpasswd1.setText("");
            } else {
                Toast.makeText(register.this, "錯誤!", Toast.LENGTH_SHORT).show();
            }
    }
}