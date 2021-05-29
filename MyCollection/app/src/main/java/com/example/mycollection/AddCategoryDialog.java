package com.example.mycollection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mycollection.model.Category;
import com.example.mycollection.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCategoryDialog extends AppCompatDialogFragment {
    private String username;
    FirebaseDatabase database;
    DatabaseReference dbReference;

    public AddCategoryDialog(String username) {
        this.username = username;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        EditText categoryNameEditTxt;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_category_dialog, null);

        EditText categoryName = view.findViewById(R.id.newCategory);

        builder.setView(view).setTitle("Añadir categoría")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addCategory(view, categoryName.getText().toString());
                    }
                });

        return builder.create();
    }

    private void addCategory(View v, String newCategory) {
        FirebaseApp.initializeApp(v.getContext());
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference();
        checkUserExist(v, newCategory);
        //Category auxCategory = new Category(newCategory, null);
        //dbReference.child("users").child(username).child("categories").child(newCategory).setValue(auxCategory);
    }

    private void checkUserExist(View v, String newCategory) {
        System.out.println("COMIENZA METODO");
        dbReference.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                for (DataSnapshot obj : snapshot.getChildren()) {
                    //Category category = obj.getValue(Category.class);
                    System.out.println("CATEGORIA? -> "+obj);
                    /*if (category != null) {
                        System.out.println("CATEGORIA NO NULL");
                        if (category.getName().equals(newCategory)) {
                            Toast.makeText(v.getContext(), "Ya existe esa categoría", Toast.LENGTH_LONG).show();
                        } else {
                            System.out.println("CATEGORIA NO EXISTE / SE CREA");
                            Category auxCategory = new Category(newCategory, null);
                            dbReference.child("users").child(username).child("categories").child(auxCategory.getName()).setValue(auxCategory);
                        }
                    } else {
                        Category auxCategory = new Category(newCategory, null);
                        dbReference.child("users").child(username).child("categories").child(auxCategory.getName()).setValue(auxCategory);
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                System.out.println("ON CANCELLED");
            }
        });
    }
}
