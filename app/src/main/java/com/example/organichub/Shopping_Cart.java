package com.example.organichub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class Shopping_Cart extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    Button cs,b;
    Button t3;
    ImageView t4;
    TextView t1,t2;
    TextView bagTotal, amountPayable, bagSavings, delivery,a;
    MeowBottomNavigation bottomNavigation;
    private final int Id_HOME =1;
    private final int Id_Account =2;
    private final int Id_Wishlist =3;
    private final int Id_Cart =4;

    private ShoppingAdapter shoppingadapter;

    public static RecyclerView cart_recycler;
    private ArrayList<Model> list;
    ArrayList<String> arr;

    ImageView im;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    String name, phoneno;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReferenceFromUrl("https://organic-hub-c4f17-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping__cart);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_shopping);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.cart);

        bagTotal = findViewById(R.id.bagTotal);
        amountPayable = findViewById(R.id.amountPayable);
        bagSavings = findViewById(R.id.amountPayable);
        delivery = findViewById(R.id.amountPayable);
        a = findViewById(R.id.textView10);
        b = findViewById(R.id.button2);
        im = findViewById(R.id.imageView4);

        cs = findViewById(R.id.confirm_order);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        cart_recycler = findViewById(R.id.cart_recy);
        cart_recycler.setHasFixedSize(true);
        cart_recycler.setLayoutManager(new LinearLayoutManager(this, cart_recycler.VERTICAL, false));
        list = new ArrayList<>();
        arr = new ArrayList<>();
        shoppingadapter = new ShoppingAdapter(this , list);
        cart_recycler.setAdapter(shoppingadapter);
        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        phoneno = getShared.getString("str2","Save a note and it will show up here");
        name = getShared.getString("str1","Save a note ");

        root.child("users").child(phoneno).child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Model model = dataSnapshot.getValue(Model.class);
                        list.add(model);

                    }
                    shoppingadapter.notifyDataSetChanged();
                }
                else{
                    t1.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                    t4.setVisibility(View.VISIBLE);
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Shopping_Cart.this, Home.class));
            }
        });


        bottomNavigation.add(new MeowBottomNavigation.Model(Id_HOME, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(Id_Account, R.drawable.ic_baseline_account_circle_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(Id_Wishlist, R.drawable.ic_baseline_favorite_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(Id_Cart, R.drawable.ic_baseline_shopping_cart_24));

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Intent intent = null;
                switch (item.getId()){
                    case Id_HOME:
                        intent = new Intent(Shopping_Cart.this,Home.class);
                        startActivity(intent);
                        break;
                    case Id_Account:
                        intent = new Intent(Shopping_Cart.this, profile.class);
                        startActivity(intent);
                        break;
                    case Id_Wishlist:
                        intent = new Intent(Shopping_Cart.this,Wishlist.class);
                        startActivity(intent);
                        break;

                    case Id_Cart:
                        break;
                }

            }
        });
        bottomNavigation.show(Id_Cart, true);
    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cart:
                break;
            case R.id.orders:
                Intent intent = new Intent(Shopping_Cart.this,MyOrders.class);
                startActivity(intent);
                break;
            case R.id.account:
                Intent intent4 = new Intent(Shopping_Cart.this,profile.class);
                startActivity(intent4);
                break;
            case R.id.logout:
                Intent intent1 = new Intent(Shopping_Cart.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.deals:
                Intent intent2 = new Intent(Shopping_Cart.this, Jackets.class);
                startActivity(intent2);
                break;
            case R.id.home:
                Intent intent5= new Intent(Shopping_Cart.this, Home.class);
                startActivity(intent5);
                break;
            case R.id.wishlist:
                Intent intent6= new Intent(Shopping_Cart.this, Wishlist.class);
                startActivity(intent6);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}