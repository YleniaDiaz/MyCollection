package com.example.mycollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mycollection.model.Category;
import com.example.mycollection.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dbReference;

    private ArrayList<Category> categories = new ArrayList<>();
    ArrayAdapter<Category> arrayAdapterCategories;

    ListView categoryListView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        categoryListView = findViewById(R.id.categoriesList);

        initializeFirebase();
        listCategories();


        Button addCategoryBtn = findViewById(R.id.addCategoryBtn);
        addCategoryBtn.setOnClickListener(v -> addCategory(v));

        Toast.makeText(this, "Añadida", Toast.LENGTH_SHORT).show();
    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference();
    }

    private void addCategory(View v){
        Category category = new Category("prueba2", null);
        dbReference.child("ylenia").child("categories").child(category.getName()).setValue(category);
    }

    private void listCategories(){
        dbReference.child("ylenia").child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                categories.clear();
                for(DataSnapshot obj: snapshot.getChildren()){
                    Category category = obj.getValue(Category.class);
                    categories.add(category);

                    arrayAdapterCategories = new ArrayAdapter<>(CategoriesActivity.this, android.R.layout.simple_list_item_1, categories);
                    categoryListView.setAdapter(arrayAdapterCategories);
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
    }
}