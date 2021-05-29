package com.example.mycollection;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    FirebaseDatabase database;
    DatabaseReference dbReference;
    boolean userExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.inputUsername);
        passwordInput = findViewById(R.id.inputPassword);

        initializeFirebase();


        Button signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(v -> login(v));

        TextView signUpTxt = findViewById(R.id.signUpTxt);
        signUpTxt.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SignUpActivity.class);
            startActivityForResult(intent, 0);
        });
    }

    private void initializeFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference();
    }

    private void login(View v) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.equals("") || password.equals("")) {
            validateFields(username, password);
            Toast.makeText(this, "Debe introducir usuario y contraseña", Toast.LENGTH_SHORT).show();
        } else {
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
                            if (user.getPassword().equals(password)) {
                                Intent intent = new Intent(v.getContext(), CategoriesActivity.class);

                                Bundle bundle = new Bundle();
                                bundle.putString("user", username);

                                intent.putExtras(bundle);
                                startActivityForResult(intent, 0);
                            } else {
                                Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
    }

    private void validateFields(String username, String password) {
        if (username.equals("")) usernameInput.setError("Obligatorio");

        if (password.equals("")) passwordInput.setError("Obligatorio");
    }
}