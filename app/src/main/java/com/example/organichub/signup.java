package com.example.organichub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.regex.Pattern;

public class signup extends AppCompatActivity {
    private EditText fname,lname,email,password;
    TextView login;
    public static EditText phone;
    private Button signin;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://organic-hub-c4f17-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phoneno);
        signin = findViewById(R.id.signin);
        login = findViewById(R.id.next);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(signup.this, MainActivity.class);
                startActivity(intent);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String emailid = email.getText().toString();
                String pass = password.getText().toString();
                String firstname = fname.getText().toString();
                String lastname = lname.getText().toString();
                String phoneno = phone.getText().toString();
                Pattern upperCase = Pattern.compile("[A-Z]");
                Pattern lowerCase = Pattern.compile("[a-z]");
                Pattern number = Pattern.compile("[0-9]");
                Pattern sp_char = Pattern.compile("[!@#$%^&*()/,.+]");
                if(firstname.isEmpty()) {
                    fname.requestFocus();
                    fname.setError("Please fill your first name");

                }
                else if(!firstname.matches("[a-zA-Z]+")){
                    fname.requestFocus();
                    fname.setError("Enter only Alphabetical order");

                }
                else if(lastname.isEmpty()) {
                    lname.requestFocus();
                    lname.setError("Please fill your last name");

                }
                else if(!lastname.matches("[a-zA-Z]+")) {
                    lname.requestFocus();
                    lname.setError("Enter only Alphabetical order");

                }
                else if(phoneno.isEmpty()){
                    phone.requestFocus();
                    phone.setError("Please fill your Phone no.");

                }
                else if(!(phoneno.length() ==10)){
                    phone.requestFocus();
                    phone.setError("Please enter correct phone no.");
                }
                else if(lowerCase.matcher(phoneno).find() || upperCase.matcher(phoneno).find() || sp_char.matcher(phoneno).find()){
                    phone.requestFocus();
                    phone.setError("Please enter correct phone no.");
                }
                else if(emailid.isEmpty()) {
                    email.requestFocus();
                    email.setError("Please fill your email id");

                }
                else if(!emailid.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    email.requestFocus();
                    email.setError("Please enter correct email");

                }
                else if(pass.isEmpty()) {
                    password.requestFocus();
                    password.setError("Minimum 6 characters are required");

                }
                else if(!lowerCase.matcher(pass).find() || !upperCase.matcher(pass).find() || !number.matcher(pass).find() || !sp_char.matcher(pass).find()){
                    password.requestFocus();
                    password.setError("Password should contain atleast 1 UpperCase Letter, 1 LowerCase Letter, 1 Special Character,1 Numerical and no white spaces");
                }
                else{
                    ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneno)){
                                Toast.makeText(signup.this, "User already registered", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                ref.child("users").child(phoneno).child("profile").child("first name").setValue(firstname);
                                ref.child("users").child(phoneno).child("profile").child("last name").setValue(lastname);
                                ref.child("users").child(phoneno).child("profile").child("email").setValue(emailid);
                                ref.child("users").child(phoneno).child("profile").child("password").setValue(pass);
                                Toast.makeText(signup.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(signup.this, MainActivity.class);
                                //intent.putExtra("phone", phoneno);
                                startActivity(intent);

                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

        });
    }
}