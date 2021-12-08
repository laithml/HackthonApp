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


    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText email=findViewById(R.id.Email);
        final EditText password=findViewById(R.id.password);
        final EditText conPassword=findViewById(R.id.conPassword);
        final EditText name=findViewById(R.id.Name);
        final EditText phone=findViewById(R.id.phone);
        final TextView Login=findViewById(R.id.LoginButton);
        final Button Register=findViewById(R.id.RegisterButton);
            databaseReference.push().child("users").child("phone");




        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailTxt=email.getText().toString();
                final String nameTxt=name.getText().toString();
                final String phoneTxt=phone.getText().toString();
                final String passwordTxt=password.getText().toString();
                final String conPasswordTxt=conPassword.getText().toString();

                if(nameTxt.isEmpty()||emailTxt.isEmpty()||phoneTxt.isEmpty()||passwordTxt.isEmpty()||conPasswordTxt.isEmpty())
                    Toast.makeText(Register.this,"One or more fields are empty!",Toast.LENGTH_SHORT).show();


                else if(!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(Register.this,"Passwords dare not matching",Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(emailTxt)){
                                Toast.makeText(Register.this,"Email is already registered!",Toast.LENGTH_SHORT).show();
                            }
                            else{

                                databaseReference.child("users").child(emailTxt).child("FullName").setValue(nameTxt);
                                databaseReference.child("users").child(emailTxt).child("Phone").setValue(phoneTxt);
                                databaseReference.child("users").child(emailTxt).child("Password").setValue(passwordTxt);

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