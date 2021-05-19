package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycollection.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    FirebaseDatabase database;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFirebase();

        usernameInput = findViewById(R.id.inputUsername);
        passwordInput = findViewById(R.id.inputPassword);

        Button signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(v -> addUser(v));

        TextView signUpTxt = findViewById(R.id.signUpTxt);
        signUpTxt.setOnClickListener(v -> {
            Intent intent = new Intent (v.getContext(), SignUpActivity.class);
            startActivityForResult(intent, 0);
        });
    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference();
    }

    private void addUser(View v){
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(username.equals("") || password.equals("")){
            validateFields(username, password);
            Toast.makeText(this, "Debe introducir usuario y contraseña", Toast.LENGTH_SHORT).show();
        }else{
            User user = new User(username, password);
            //dbReference.child(username).setValue(user);
            Intent intent = new Intent (v.getContext(), CategoriesActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    private void validateFields(String username, String password){
        if(username.equals("")) usernameInput.setError("Obligatorio");

        if(password.equals("")) passwordInput.setError("Obligatorio");
    }
}