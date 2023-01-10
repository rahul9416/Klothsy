package com.example.organichub;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class orderadapter extends RecyclerView.Adapter<orderadapter.MyViewHolder> {

    private ArrayList<Model> mList;
    private Context context;


    public orderadapter(Context context , ArrayList<Model> mList){
        this.context = context;
        this.mList = mList;

    }

    @NonNull
    @Override
    public orderadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order , parent ,false);
        return new orderadapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull orderadapter.MyViewHolder holder, int position) {

        final Model temp = mList.get(position);

       holder.pname.setText(mList.get(position).getName());
        holder.pprice.setText(mList.get(position).getPrice());


        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView pname, pprice;
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
