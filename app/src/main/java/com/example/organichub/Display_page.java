package com.example.organichub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Display_page extends AppCompatActivity {

    ImageView img;
    TextView tv1, tv2, tv3;
    Model model = null;
    Button a2c;
    CheckBox fvt;
    RecyclerView cart_recy;
    String phoneno, names;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://organic-hub-c4f17-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_page);

        SharedPreferences sp = getSharedPreferences("a2f", MODE_PRIVATE);
        Boolean check = sp.getBoolean("a2f", false);

        final Object object = getIntent().getSerializableExtra("details");
        if(object instanceof Model){
            model = (Model) object;
        }

        img = findViewById(R.id.imageView);
        tv1 = findViewById(R.id.textView6);
        tv2 = findViewById(R.id.textView7);
        tv3 = findViewById(R.id.textView8);
        a2c = findViewById(R.id.addtocart);
        fvt = findViewById(R.id.cbHeart);
        cart_recy = findViewById(R.id.cart_recy);

        String img1 = model.getImageUrl();
        String name = model.getName();
        String price = model.getPrice();
        String desc = model.getDescription();
        String quant = "1";

        

        if(model != null){
            Glide.with(getApplicationContext()).load(model.getImageUrl()).into(img);
            tv3.setText(model.getName());
            tv1.setText(model.getDescription());
            tv2.setText(model.getPrice());
        }
        final String[] txt = {a2c.getText().toString()};
        String n = tv1.getText().toString();
        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        phoneno = getShared.getString("str2","Save a note and it will show up here");
        names = getShared.getString("str1","Save a note ");
        //name = getShared.getString("str3","Save a note ");

        /*ref.child("users").child(phoneno).child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.hasChild(name))
                        fvt.setChecked(Update("one"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        a2c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a2c.getText()== txt[0]) {
                    a2c.setText("VIEW CART");

                    ref.child("users").child(phoneno).child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(n) ) {
                                String namess = snapshot.child("name").getValue().toString();
                                Log.d("name", n);
                                Toast.makeText(Display_page.this, "Product already been added to cart", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                                SharedPreferences.Editor editor = shrd.edit();
                                editor.putString("str1", name);
                                editor.apply();
                                Model model = new Model(img1,name, price, desc,quant);
                                String modelId = ref.push().getKey();
                                ref.child("users").child(phoneno).child("cart").child(modelId).setValue(model);

                                Toast.makeText(Display_page.this, "Item Added in your cart", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    startActivity(new Intent(Display_page.this, Shopping_Cart.class));
                }
            }
        });

        fvt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ref.child("users").child(phoneno).child("wishlist").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(name)){
                            SharedPreferences shrds = getSharedPreferences("demos", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shrds.edit();
                            editor.putString("str3", name);
                            editor.apply();
                            SaveIntoSharedPrefs("abc",isChecked);
                            ref.child("users").child(phoneno).child("wishlist").child(name).setValue(null);
                            Toast.makeText(Display_page.this, "Removed from your Favourites", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Display_page.this,Home.class);
                            startActivity(intent);
                        }
                        else{
                            SharedPreferences shrds = getSharedPreferences("demos", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shrds.edit();
                            editor.putString("str3", name);
                            editor.apply();
                            SaveIntoSharedPrefs("abc",isChecked);
                            Model model = new Model(img1,name, price, desc,null);
                            String modelId = ref.push().getKey();
                            ref.child("users").child(phoneno).child("wishlist").child(modelId).setValue(model);
                            Toast.makeText(Display_page.this, "Item added in your Favourite List", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }


    private void SaveIntoSharedPrefs(String key, boolean value){

        SharedPreferences sp = getSharedPreferences("a2f", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private boolean Update(String key){
        SharedPreferences sp = getSharedPreferences("a2f", MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

}