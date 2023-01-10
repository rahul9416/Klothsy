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

public class Payment extends AppCompatActivity {

    Button add, co;
    EditText add_address;
    TextView address,tt,tt1;
    String phoneno, price, name, quant, desc, img1;
    String addres;


    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        add = findViewById(R.id.add);
        address = findViewById(R.id.address);
        add_address = findViewById(R.id.add_address);
        tt = findViewById(R.id.total_price);
        tt1 = findViewById(R.id.tt);
        co = findViewById(R.id.orderplace);
        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        phoneno = getShared.getString("str2","Save a note and it will show up here");
        name = getShared.getString("str1","Save a note ");
        desc = getShared.getString("str7", "next");
        quant = getShared.getString("str8", "next");
        img1 = getShared.getString("str6", "next");
        price = getShared.getString("str4","next");
        tt.setText(price);

        co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addres = address.getText().toString();
                if(addres.isEmpty()){
                    Toast.makeText(Payment.this, "Address cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else{
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shrd.edit();

                            editor.putString("str1", name);
                            editor.apply();
                            Model model = new Model(img1,name, price, desc,quant);
                            String modelId = ref.push().getKey();
                            ref.child("users").child(phoneno).child("Orders").child(modelId).setValue(model);
                            ref.child("All-orders").child(modelId).setValue(model);
                            ref.child("All-orders").child(modelId).child("address").setValue(addres);
                            ref.child("All-orders").child(modelId).child("phone no").setValue(phoneno);

                            Toast.makeText(Payment.this, "Order Placed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Payment.this, Home.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        ref.child("users").child(phoneno).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("address")){
                    tt1.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                    address.setText(snapshot.child("address").getValue(String.class));
                }
                else{
                    add.setVisibility(View.VISIBLE);
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            add_address.setVisibility(View.VISIBLE);
                            add.setText("Save Address");
                            add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    addres = add_address.getText().toString();
                                    if(addres.isEmpty()){
                                        Toast.makeText(Payment.this, "Address cannot be empty.", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        add.setVisibility(View.INVISIBLE);
                                        add_address.setVisibility(View.INVISIBLE);
                                        ref.child("users").child(phoneno).child("profile").child("address").setValue(addres);
                                        ref.child("users").child(phoneno).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                tt1.setVisibility(View.VISIBLE);
                                                address.setVisibility(View.VISIBLE);
                                                address.setText(snapshot.child("address").getValue(String.class));
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}