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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class Wishlist extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button t3;
    ImageView t4;
    MeowBottomNavigation bottomNavigation;
    private final int Id_HOME =1;
    private final int Id_Account =2;
    private final int Id_Wishlist =3;
    private final int Id_Cart =4;
    TextView t1,t2;
    Task<DataSnapshot> dict;

    WishlistAdapter wishlistAdapter;


    public static RecyclerView wish_recycler;
    private ArrayList<Model> list;
    String phoneno, name, names;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_wishlist);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.wishlist);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        wish_recycler = findViewById(R.id.wish_recycler);
        wish_recycler.setHasFixedSize(true);
        wish_recycler.setLayoutManager(new LinearLayoutManager(this, wish_recycler.VERTICAL, false));
        list = new ArrayList<>();
        wishlistAdapter = new WishlistAdapter(this, list);
        wish_recycler.setAdapter(wishlistAdapter);

        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        SharedPreferences getShare = getSharedPreferences("demos", MODE_PRIVATE);
        names = getShared.getString("str1", "jhfgjhj");
        phoneno = getShared.getString("str2","Save a note and it will show up here");
        name = getShare.getString("str3","Save a note ");



        dict = root.child("users").get();
        //Log.d("da1", String.valueOf(dict.getResult()));

        root.child(phoneno).child("wishlist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Model model = dataSnapshot.getValue(Model.class);
                        //String modelId = model.getName();
                        //Log.d("da1",modelId);
                        list.add(model);

                    }
                    wishlistAdapter.notifyDataSetChanged();
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
                startActivity(new Intent(Wishlist.this, Home.class));
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
                        intent = new Intent(Wishlist.this,Home.class);
                        startActivity(intent);
                        break;
                    case Id_Account:
                        intent = new Intent(Wishlist.this,profile.class);
                        startActivity(intent);
                        break;
                    case Id_Wishlist:
                        break;

                    case Id_Cart:
                        intent = new Intent(Wishlist.this,Shopping_Cart.class);
                        startActivity(intent);
                        break;
                }

            }
        });
        bottomNavigation.show(Id_Wishlist, true);

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
            case R.id.wishlist:
                break;
            case R.id.orders:
                Intent intent = new Intent(Wishlist.this,MyOrders.class);
                startActivity(intent);
                break;
            case R.id.cart:
                Intent intent4 = new Intent(Wishlist.this,Shopping_Cart.class);
                startActivity(intent4);
                break;
            case R.id.home:
                Intent intent5 = new Intent(Wishlist.this,Home.class);
                startActivity(intent5);
                break;
            case R.id.account:
                Intent intent6 = new Intent(Wishlist.this,profile.class);
                startActivity(intent6);
                break;
            case R.id.logout:
                Intent intent1 = new Intent(Wishlist.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.deals:
                Intent intent2 = new Intent(Wishlist.this, Jackets.class);
                startActivity(intent2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}