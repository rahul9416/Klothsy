package com.example.organichub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class wishlistDisplay extends AppCompatActivity {
    Model model = null;
    ImageView img;
    TextView tv1, tv2, tv3;
    Button a2c;
    CheckBox fvt;
    String phoneno, names;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://organic-hub-c4f17-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_display);


        final Object object = getIntent().getSerializableExtra("details");
        if(object instanceof Model){
            model = (Model) object;
        }

        img = findViewById(R.id.imageView2);
        tv1 = findViewById(R.id.textView9);
        tv2 = findViewById(R.id.textView10);
        tv3 = findViewById(R.id.textView11);
        a2c = findViewById(R.id.addTocart);
        fvt = findViewById(R.id.heart);


        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        phoneno = getShared.getString("str2","Save a note and it will show up here");
        names = getShared.getString("str1","Save a note ");
        String quant = "1";


        String img1 = model.getImageUrl();
        String name = model.getName();
        String price = model.getPrice();
        String desc = model.getDescription();

        String n = tv1.getText().toString();

        if(model != null){
            Glide.with(getApplicationContext()).load(model.getImageUrl()).into(img);
            tv2.setText(model.getName());
            tv1.setText(model.getDescription());
            tv3.setText(model.getPrice());
        }
        final String[] txt = {a2c.getText().toString()};

        a2c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a2c.getText()== txt[0]) {
                    a2c.setText("VIEW CART");

                    ref.child("users").child(phoneno).child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(n) ) {
                                Toast.makeText(wishlistDisplay.this, "Product already been added to cart", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                                SharedPreferences.Editor editor = shrd.edit();

                                editor.putString("str1", name);
                                editor.apply();
                                Model model = new Model(img1,name, price, desc,quant);
                                String modelId = ref.push().getKey();
                                ref.child("users").child(phoneno).child("cart").child(modelId).setValue(model);

                                Toast.makeText(wishlistDisplay.this, "Item Added in your cart", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    startActivity(new Intent(wishlistDisplay.this, Shopping_Cart.class));
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
                            ref.child("users").child(phoneno).child("wishlist").child(name).setValue(null);
                            Toast.makeText(wishlistDisplay.this, "Removed from your Favourites", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(wishlistDisplay.this,Home.class);
                            startActivity(intent);
                        }
                        else{
                            Model model = new Model(img1,name, price, desc,null);
                            String modelId = ref.push().getKey();
                            ref.child("users").child(phoneno).child("wishlist").child(name).child(modelId).setValue(model);
                            Toast.makeText(wishlistDisplay.this, "Product already in your Favourite List", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}