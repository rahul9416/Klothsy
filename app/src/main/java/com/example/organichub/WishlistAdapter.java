package com.example.organichub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


public class WishlistAdapter<OnItemClick> extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {

    private ArrayList<Model> mList;
    private Context context;
    private DatabaseReference root ;
    SharedPreferences sharedpre;
    String phoneno;
    Dictionary dict;


    public WishlistAdapter(Context context , ArrayList<Model> mList){
        this.context = context;
        this.mList = mList;
        root = FirebaseDatabase.getInstance().getReference("users");
        sharedpre = context.getSharedPreferences("demo", MODE_PRIVATE);
        phoneno = sharedpre.getString("str2","Save a note and it will show up here");

    }

    @NonNull
    @Override
    public WishlistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.a2w , parent ,false);
        return new WishlistAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final Model temp = mList.get(position);
        HashMap<String, Object> hashMap = new HashMap<>();
        holder.pname.setText(mList.get(position).getName());
        holder.pprice.setText(mList.get(position).getPrice());

        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,wishlistDisplay.class);
                intent.putExtra("details", temp);
                context.startActivity(intent);

            }
        });

        /*holder.fvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.child("users").child(phoneno).child("wishlist").child(ids).removeValue().addOnSuccessListener(suc ->{
                    Toast.makeText(context, ""+ids, Toast.LENGTH_SHORT).show();
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }).addOnFailureListener(er -> {
                    Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                });
                //Toast.makeText(context, (int) id, Toast.LENGTH_SHORT).show();
                //notifyItemRemoved(position);
                //notifyDataSetChanged();
            }
        });*/


        /*holder.fvt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //root.child("users").child(phoneno).child("wishlist").setValue(null);
                root.child("users").child(phoneno).child("wishlist").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Model model = dataSnapshot.getValue(Model.class);
                            String modelId = model.getName();
                            Log.d("da2",modelId);
                            root.child("users").child(phoneno).child("wishlist").child(modelId).setValue(null);
                            Toast.makeText(context, "Removed from wishlist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });*/

    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView pname;
        TextView pprice;
        CheckBox fvt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pname = itemView.findViewById(R.id.name);
            pprice = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.img);
            fvt = itemView.findViewById(R.id.hearts);

        }
    }
}
