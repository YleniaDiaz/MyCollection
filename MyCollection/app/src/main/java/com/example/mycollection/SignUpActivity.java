package com.example.mycollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycollection.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    FirebaseDatabase database;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeFirebase();

        usernameInput = findViewById(R.id.inputUsername);
        passwordInput = findViewById(R.id.inputPassword);

        Button signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(v -> addUser(v));
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
            checkUserExist(v);
        }
    }

    private void checkUserExist(View v) {
        dbReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                for (DataSnapshot obj : snapshot.getChildren()) {
                    User user = obj.getValue(User.class);
                    String username = usernameInput.getText().toString();
                    String password = passwordInput.getText().toString();
                    if (user != null) {
                        if (user.getUsername().equals(username)) {
                            Toast.makeText(SignUpActivity.this, "Ya existe ese usuario", Toast.LENGTH_SHORT).show();
                        } else {
                            User newUSer = new User(username, password);
                            dbReference.child("users").child(username).setValue(newUSer);
                            Intent intent = new Intent (v.getContext(), MainActivity.class);
                            startActivityForResult(intent, 0);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
    }

    private void validateFields(String username, String password){
        if(username.equals("")) usernameInput.setError("Obligatorio");

        if(password.equals("")) passwordInput.setError("Obligatorio");
    }
}