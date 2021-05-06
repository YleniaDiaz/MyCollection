package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mycollection.model.User;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //MIN 26:36

        Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameEditTxt = (EditText) findViewById(R.id.inputUsername);
                EditText passwordEditTxt = (EditText) findViewById(R.id.inputPassword);

                String username = usernameEditTxt.getText().toString();
                String password = passwordEditTxt.getText().toString();

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                System.out.println(username + ' ' + password);

                //Intent intent = new Intent (v.getContext(), CategoriesActivity.class);
                //startActivityForResult(intent, 0);
            }
        });
    }
}