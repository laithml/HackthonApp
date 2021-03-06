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


public class Register extends AppCompatActivity {


    DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://hackthaon-default-rtdb.firebaseio.com/").getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText password=findViewById(R.id.password);
        final EditText conPassword=findViewById(R.id.conPassword);
        final EditText name=findViewById(R.id.Name);
        final EditText phone=findViewById(R.id.phone);
        final TextView Login=findViewById(R.id.LoginButton);
        final Button Register=findViewById(R.id.RegisterButton);
        final EditText location=findViewById(R.id.location);




        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameTxt=name.getText().toString();
                final String phoneTxt=phone.getText().toString();
                final String passwordTxt=password.getText().toString();
                final String conPasswordTxt=conPassword.getText().toString();
                final String address=location.getText().toString();

                if(nameTxt.isEmpty()||phoneTxt.isEmpty()||passwordTxt.isEmpty()||conPasswordTxt.isEmpty())
                    Toast.makeText(Register.this,"One field or more is missing!",Toast.LENGTH_SHORT).show();


                else if(!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(Register.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(phoneTxt)){
                                Toast.makeText(Register.this,"Phone is already registered!",Toast.LENGTH_SHORT).show();
                            }
                            else{

                                databaseReference.child("users").child(phoneTxt).child("FullName").setValue(nameTxt);
                                databaseReference.child("users").child(phoneTxt).child("Password").setValue(passwordTxt);
                                databaseReference.child("users").child(phoneTxt).child("Address").setValue(address);

                                Toast.makeText(Register.this,"Register Successful!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Register.this, Login.class));
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }


}