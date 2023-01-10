package com.example.organichub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView firstname,lastname, email, phone, address;
    String phoneno;
    Button logout, addaddress;
    EditText add_address;
    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    String addres;
    MeowBottomNavigation bottomNavigation;
    NavigationView navigationView;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private final int Id_HOME =1;
    private final int Id_Account =2;
    private final int Id_Wishlist =3;
    private final int Id_Cart =4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firstname = findViewById(R.id.profile_firstname);
        email = findViewById(R.id.profile_email);
        phone = findViewById(R.id.profile_phone);
        logout = findViewById(R.id.log) ;
        address = findViewById(R.id.profile_address);
        addaddress = findViewById(R.id.profile_addaddress);
        add_address = findViewById(R.id.profile_add_address);
        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        phoneno = getShared.getString("str2","Save a note and it will show up here");
        phone.setText(phoneno);
        lastname = findViewById(R.id.profile_name);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_wishlist);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.account);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        bottomNavigation.add(new MeowBottomNavigation.Model(Id_HOME, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(Id_Account, R.drawable.ic_baseline_account_circle_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(Id_Wishlist, R.drawable.ic_baseline_favorite_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(Id_Cart, R.drawable.ic_baseline_shopping_cart_24));

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this, MainActivity.class));
            }
        });


        ref.child("users").child(phoneno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("profile").hasChild("address")){
                    firstname.setText(snapshot.child("profile").child("first name").getValue(String.class));
                    lastname.setText(snapshot.child("profile").child("last name").getValue(String.class));
                    email.setText(snapshot.child("profile").child("email").getValue(String.class));
                    address.setText(snapshot.child("profile").child("address").getValue(String.class));
                    String add = address.getText().toString();
                    String nam = firstname.getText().toString();
                    String lnam = lastname.getText().toString();
                    firstname.setText(make(nam));
                    lastname.setText(lmake(lnam));
                    address.setText(amake(add));
                }
                else{
                    addaddress.setVisibility(View.VISIBLE);
                    firstname.setText(snapshot.child("profile").child("first name").getValue(String.class));
                    lastname.setText(snapshot.child("profile").child("last name").getValue(String.class));
                    email.setText(snapshot.child("profile").child("email").getValue(String.class));
                    String nam = firstname.getText().toString();
                    String lnam = lastname.getText().toString();
                    firstname.setText(make(nam));
                    lastname.setText(lmake(lnam));
                    addaddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            add_address.setVisibility(View.VISIBLE);
                            addaddress.setText("Save Address");
                            addaddress.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    addres = add_address.getText().toString();
                                    if(addres.isEmpty()){
                                        Toast.makeText(profile.this, "Address cannot be empty.", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        addaddress.setVisibility(View.INVISIBLE);
                                        add_address.setVisibility(View.INVISIBLE);
                                        ref.child("users").child(phoneno).child("profile").child("address").setValue(addres);
                                        ref.child("users").child(phoneno).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                address.setText(snapshot.child("address").getValue(String.class));
                                                String add = address.getText().toString();

                                                address.setText(amake(add));
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

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Intent intent = null;
                switch (item.getId()){
                    case Id_HOME:
                        intent = new Intent(profile.this,Home.class);
                        startActivity(intent);
                        break;
                    case Id_Account:
                        break;
                    case Id_Wishlist:
                        intent = new Intent(profile.this,Wishlist.class);
                        startActivity(intent);
                        break;

                    case Id_Cart:
                        intent = new Intent(profile.this,Shopping_Cart.class);
                        startActivity(intent);
                        break;
                }

            }
        });
        bottomNavigation.show(Id_Account, true);

    }
    public String make(String nam){
        return nam.substring(0,1).toUpperCase()+nam.substring(1).toLowerCase();
    }
    public String lmake(String lnam){
        return lnam.substring(0,1).toUpperCase()+lnam.substring(1).toLowerCase();
    }
    public String amake(String add){
        return add.substring(0,1).toUpperCase()+add.substring(1).toLowerCase();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Intent intent4 = new Intent(profile.this,Home.class);
                startActivity(intent4);
                break;
            case R.id.orders:
                Intent intent = new Intent(profile.this,MyOrders.class);
                startActivity(intent);
                break;
            case R.id.account:
                break;
            case R.id.logout:
                Intent intent1 = new Intent(profile.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.cart:
                Intent intent6 = new Intent(profile.this, Shopping_Cart.class);
                startActivity(intent6);
                break;
            case R.id.deals:
                Intent intent2 = new Intent(profile.this, Jackets.class);
                startActivity(intent2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}