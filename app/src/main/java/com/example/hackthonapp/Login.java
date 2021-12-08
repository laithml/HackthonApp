package com.example.hackthonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://hackthaon-default-rtdb.firebaseio.com/").getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText phone=findViewById(R.id.Phone);
        final EditText password=findViewById(R.id.password);
        final Button loginBtn=findViewById(R.id.LoginButton);
        final TextView RegisterBtn=findViewById(R.id.RegisterButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneTxt=phone.getText().toString();
                final String passTxt=password.getText().toString();

                if(phoneTxt.isEmpty()||passTxt.isEmpty())
                    Toast.makeText(Login.this,"Please Enter phone and password",Toast.LENGTH_SHORT).show();

                else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneTxt)){
                                if(snapshot.child(phoneTxt).child("Password").getValue(String.class).equals(passTxt)) {
                                    Toast.makeText(Login.this, "logged in Successfully!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,MainActivity.class));

                                }
                                else
                                    Toast.makeText(Login.this,"Wrong Password!",Toast.LENGTH_SHORT).show();

                            }else
                                Toast.makeText(Login.this,"Wrong Phone Number!",Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

    }
}