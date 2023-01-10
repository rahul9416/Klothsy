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

public class MainActivity extends AppCompatActivity {

    private EditText phone,password;
    private Button login;
    private TextView signup,pass;


    DatabaseReference dref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://organic-hub-c4f17-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.pass);
        login =  findViewById(R.id.login);
        signup = findViewById(R.id.link);
        pass = findViewById(R.id.forget);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signup.class));
                finish();
            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, forget.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {

        String phoneno = phone.getText().toString();
        String pass = password.getText().toString();
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern number = Pattern.compile("[0-9]");
        Pattern sp_char = Pattern.compile("[!@#$%^&*()/,.+]");
        String owner_phone = "0123456789";
        String owner_pass = "Abc@123";

        if(phoneno.isEmpty()){
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
        else if(pass.isEmpty()) {
            password.requestFocus();
            password.setError("Minimum 6 characters are required");

        }
        else if(!lowerCase.matcher(pass).find() || !upperCase.matcher(pass).find() || !number.matcher(pass).find() || !sp_char.matcher(pass).find()){
            password.requestFocus();
            password.setError("Password should contain atleast 1 UpperCase Letter, 1 LowerCase Letter, 1 Special Character,1 Numerical and no white spaces");
        }

        else if(phoneno.matches("0123456789") || pass.matches("Abc@123")){
            startActivity(new Intent(MainActivity.this, image.class));
            finish();
        }

        else{
            dref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(phoneno)) {
                        String get_pass = snapshot.child(phoneno).child("profile").child("password").getValue(String.class);
                        if (get_pass.equals(pass)) {

                            SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shrd.edit();

                            editor.putString("str2", phoneno);
                            editor.apply();

                                Toast.makeText(MainActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                                String get_firstname = snapshot.child(phoneno).child("profile").child("first name").getValue(String.class);
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.putExtra("name", get_firstname);
                                startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Phone no. is not registered", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}