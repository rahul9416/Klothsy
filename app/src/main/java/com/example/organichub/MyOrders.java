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
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyOrders extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    TextView t1;
    Button t3;
    private ArrayList<Model> list;
    private orderadapter adapter;
    String phoneno, name, names;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("users");
    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        recyclerView = findViewById(R.id.myorder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, recyclerView.VERTICAL, false));

        list = new ArrayList<>();

        adapter = new orderadapter(this , list);
        recyclerView.setAdapter(adapter);
        t1 = findViewById(R.id.t1);
        t3 = findViewById(R.id.t3);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_wishlist);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.orders);

        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        SharedPreferences getShare = getSharedPreferences("demos", MODE_PRIVATE);
        names = getShared.getString("str1", "jhfgjhj");
        phoneno = getShared.getString("str2","Save a note and it will show up here");
        name = getShare.getString("str3","Save a note ");

        root.child(phoneno).child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Model model = dataSnapshot.getValue(Model.class);
                        list.add(model);

                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    t1.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyOrders.this, Home.class));
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.orders:
                break;
            case R.id.wishlist:
                Intent intent = new Intent(MyOrders.this,Wishlist.class);
                startActivity(intent);
                break;
            case R.id.cart:
                Intent intent4 = new Intent(MyOrders.this,Shopping_Cart.class);
                startActivity(intent4);
                break;
            case R.id.home:
                Intent intent5 = new Intent(MyOrders.this,Home.class);
                startActivity(intent5);
                break;
            case R.id.account:
                Intent intent6 = new Intent(MyOrders.this,profile.class);
                startActivity(intent6);
                break;
            case R.id.logout:
                Intent intent1 = new Intent(MyOrders.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.deals:
                Intent intent2 = new Intent(MyOrders.this, Jackets.class);
                startActivity(intent2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
}