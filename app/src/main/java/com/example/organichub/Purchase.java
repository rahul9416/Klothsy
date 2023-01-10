package com.example.organichub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Purchase extends AppCompatActivity {

    TextView img_name, img_price, bagtotal, saving, delivery,amountpayable;
    Button remove, confirmOrder;
    ImageView img;
    Model model;
    String phoneno, names;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://organic-hub-c4f17-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        img_name = findViewById(R.id.name);
        img_price = findViewById(R.id.price);
        bagtotal = findViewById(R.id.bagTotal);
        saving = findViewById(R.id.bagSavings);
        delivery = findViewById(R.id.delivery);
        amountpayable = findViewById(R.id.amountPayable);
        confirmOrder = findViewById(R.id.confirm_order);
        img = findViewById(R.id.img);

        final Object object = getIntent().getSerializableExtra("details");
        if(object instanceof Model){
            model = (Model) object;
        }

        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        phoneno = getShared.getString("str2","Save a note and it will show up here");
        names = getShared.getString("str1","Save a note ");
        String quant = "1";

        String img1 = model.getImageUrl();
        String name = model.getName();
        String price = model.getPrice();
        String desc = model.getDescription();

        if(model != null){
            Glide.with(getApplicationContext()).load(model.getImageUrl()).into(img);
            img_name.setText(model.getName());
            img_price.setText(model.getPrice());
        }
        Log.d("a", String.valueOf(bagtotal));
        bagtotal.setText(img_price.getText());

        int save = Integer.parseInt(String.valueOf(bagtotal.getText()));
        int discount = (save*10)/100;
        saving.setText(Integer.toString(discount));
        int del = Integer.parseInt(String.valueOf(delivery.getText()));

        int total = save + del - discount;
        amountpayable.setText(Integer.toString(total));

        String t = amountpayable.getText().toString();

        SharedPreferences.Editor editor = getShared.edit();

        editor.putString("str4",t );
        editor.putString("str5", name);
        editor.putString("str6", img1);
        editor.putString("str7", desc);
        editor.putString("str8",quant);
        editor.apply();

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Purchase.this, Payment.class));
            }
        });



    }
}