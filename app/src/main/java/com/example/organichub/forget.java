package com.example.organichub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class forget extends AppCompatActivity {
    Button reset;
    EditText phone, pass, reset_pass;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://organic-hub-c4f17-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        reset = findViewById(R.id.reset);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        reset_pass = findViewById(R.id.confirmPass);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassWord();
            }
        });

    }
    public void resetPassWord(){
        String phones = phone.getText().toString();
        String password = pass.getText().toString();
        String reset = reset_pass.getText().toString();
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern number = Pattern.compile("[0-9]");
        Pattern sp_char = Pattern.compile("[!@#$%^&*()/,.+]");

        if(phones.isEmpty()){
            phone.requestFocus();
            phone.setError("Please fill your Phone no.");

        }
        else if(!(phones.length() ==10)){
            phone.requestFocus();
            phone.setError("Please enter correct phone no.");
        }
        else if(lowerCase.matcher(phones).find() || upperCase.matcher(phones).find() || sp_char.matcher(phones).find()){
            phone.requestFocus();
            phone.setError("Please enter correct phone no.");
        }
        else if(password.isEmpty()) {
            pass.requestFocus();
            pass.setError("Minimum 6 characters are required");

        }
        else if(!lowerCase.matcher(password).find() || !upperCase.matcher(password).find() || !number.matcher(password).find() || !sp_char.matcher(password).find()){
            pass.requestFocus();
            pass.setError("Password should contain atleast 1 UpperCase Letter, 1 LowerCase Letter, 1 Special Character,1 Numerical and no white spaces");
        }
        else if(!password.equals(reset)){
            reset_pass.requestFocus();
            reset_pass.setError("Password not matched");
        }
        else{
            dref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(phones)){
                        dref.child("users").child(phones).child("profile").child("password").setValue(password);
                        Toast.makeText(forget.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}