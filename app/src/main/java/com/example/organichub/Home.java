package com.example.organichub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    private RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4, recyclerView5;
    private ArrayList<Model> list, nList, list3, list4, list5;
    private TextView ViewMore, vm1,vm2,vm3,vm4;
    Toolbar toolbar;
    Dictionary da1;


    private SlideAdapter slideadapter, slideadapter2, slideadapter3, slideadapter4, slideadapter5;
    private final int Id_HOME =1;
    private final int Id_Account =2;
    private final int Id_Wishlist =3;
    private final int Id_Cart =4;

    MeowBottomNavigation bottomNavigation;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("products");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ViewMore = findViewById(R.id.ViewMore);
        vm1 = findViewById(R.id.viewMor);
        vm2 = findViewById(R.id.view);
        vm3 = findViewById(R.id.viewm);
        vm4 = findViewById(R.id.viewmo);

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

        vm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Tshirts.class));
            }
        });

        vm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Jackets.class));
            }
        });
        vm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Bottomwear.class));
            }
        });

        vm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Footwear.class));
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Intent intent = null;
                switch (item.getId()){
                    case Id_HOME:
                        break;
                    case Id_Account:
                        intent = new Intent(Home.this,profile.class);
                        startActivity(intent);
                        break;
                    case Id_Wishlist:
                        intent = new Intent(Home.this,Wishlist.class);
                        startActivity(intent);
                        break;

                    case Id_Cart:
                        intent = new Intent(Home.this,Shopping_Cart.class);
                        startActivity(intent);
                        break;
                }

            }
        });
        bottomNavigation.show(Id_HOME, true);

        ViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Shirts.class));
            }
        });

        recyclerView = findViewById(R.id.recyc_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, recyclerView.HORIZONTAL, false));

        recyclerView2 = findViewById(R.id.recyc_view2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, recyclerView2.HORIZONTAL, false));

        recyclerView3 = findViewById(R.id.recyc_view3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, recyclerView.HORIZONTAL, false));

        recyclerView4 = findViewById(R.id.recyc_view4);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this, recyclerView.HORIZONTAL, false));

        recyclerView5 = findViewById(R.id.recyc_view5);
        recyclerView5.setHasFixedSize(true);
        recyclerView5.setLayoutManager(new LinearLayoutManager(this, recyclerView.HORIZONTAL, false));

        list = new ArrayList<>();
        nList = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();

        slideadapter = new SlideAdapter(this , list);
        slideadapter2 = new SlideAdapter(this , nList);
        slideadapter3 = new SlideAdapter(this, list3);
        slideadapter4 = new SlideAdapter(this, list4);
        slideadapter5 = new SlideAdapter(this, list5);

        recyclerView.setAdapter(slideadapter);
        recyclerView2.setAdapter(slideadapter2);
        recyclerView3.setAdapter(slideadapter3);
        recyclerView4.setAdapter(slideadapter4);
        recyclerView5.setAdapter(slideadapter5);

        //Values For First Recycler
        root.child("Male").child("Topwear").child("Shirts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                    Collections.shuffle(list);

                }
                slideadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Values for First Recycler
        root.child("Female").child("Topwear").child("Shirts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model1 = dataSnapshot.getValue(Model.class);
                    list.add(model1);
                    Collections.shuffle(list);
                }
                slideadapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Values for Second Recycler
        root.child("Male").child("Topwear").child("T-Shirts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    nList.add(model);
                    Collections.shuffle(nList);

                }
                slideadapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Values for Second Recycler
        root.child("Female").child("Topwear").child("T-Shirts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    nList.add(model);
                    Collections.shuffle(nList);

                }
                slideadapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Male").child("Topwear").child("Jackets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list3.add(model);
                    Collections.shuffle(list3);

                }
                slideadapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Female").child("Topwear").child("Jackets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list3.add(model);
                    Collections.shuffle(list3);

                }
                slideadapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Male").child("Bottomwear").child("Jeans").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list4.add(model);
                    Collections.shuffle(list4);

                }
                slideadapter4.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Female").child("Bottomwear").child("Jeans").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list4.add(model);
                    Collections.shuffle(list4);

                }
                slideadapter4.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Male").child("Bottomwear").child("Trouser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list4.add(model);
                    Collections.shuffle(list4);

                }
                slideadapter4.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Male").child("Footwear").child("Shoes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list5.add(model);
                    Collections.shuffle(list5);

                }
                slideadapter5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Female").child("Footwear").child("Shoes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list5.add(model);
                    Collections.shuffle(list5);

                }
                slideadapter5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Male").child("Footwear").child("Loafers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list5.add(model);
                    Collections.shuffle(list5);

                }
                slideadapter5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Male").child("Footwear").child("Boots").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list5.add(model);
                    Collections.shuffle(list5);

                }
                slideadapter5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Male").child("Footwear").child("Sandals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list5.add(model);
                    Collections.shuffle(list5);

                }
                slideadapter5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Male").child("Footwear").child("Flip-Flops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list5.add(model);
                    Collections.shuffle(list5);

                }
                slideadapter5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Female").child("Footwear").child("Flip-Flops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list5.add(model);
                    Collections.shuffle(list5);

                }
                slideadapter5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Female").child("Footwear").child("Sandals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list5.add(model);
                    Collections.shuffle(list5);

                }
                slideadapter5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        root.child("Female").child("Footwear").child("Boots").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list5.add(model);
                    Collections.shuffle(list5);

                }
                slideadapter5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

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
            case R.id.home:
                break;
            case R.id.orders:
                Intent intent = new Intent(Home.this,MyOrders.class);
                startActivity(intent);
                break;
            case R.id.account:
                Intent intent4 = new Intent(Home.this,profile.class);
                startActivity(intent4);
                break;
            case R.id.logout:
                Intent intent1 = new Intent(Home.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.deals:
                Intent intent2 = new Intent(Home.this, Jackets.class);
                startActivity(intent2);
                break;
            case R.id.cart:
                Intent intent5= new Intent(Home.this, Shopping_Cart.class);
                startActivity(intent5);
                break;
            case R.id.wishlist:
                Intent intent6= new Intent(Home.this, Wishlist.class);
                startActivity(intent6);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}