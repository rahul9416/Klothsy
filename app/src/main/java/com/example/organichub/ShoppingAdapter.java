package com.example.organichub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.MyViewHolder>{

    private ArrayList<Model> mList;
    private Context context;

    public ShoppingAdapter(Context context , ArrayList<Model> mList){

        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ShoppingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.a2c , parent ,false);
        return new ShoppingAdapter.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ShoppingAdapter.MyViewHolder holder, int position) {
        int a;
        final Model temp = mList.get(position);

        holder.pname.setText(mList.get(position).getName());
        holder.pprice.setText(mList.get(position).getPrice());



        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);

        holder.p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Purchase.class);
                intent.putExtra("details", temp);
                context.startActivity(intent);

            }
        });
    }

        @Override
        public int getItemCount() {
            return mList.size();
        }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView pname, pprice, bagTotal,invis, savings;
        Button p;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pname = itemView.findViewById(R.id.name);
            pprice = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.img);
            bagTotal = itemView.findViewById(R.id.bagTotal);
            p = itemView.findViewById(R.id.BuyNow);
            invis = itemView.findViewById(R.id.invisible);
            savings = itemView.findViewById(R.id.bagSavings);
        }
    }

}

