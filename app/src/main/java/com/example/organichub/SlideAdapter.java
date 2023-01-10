package com.example.organichub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.MyViewHolder>{

    private ArrayList<Model> mList;
    private Context context;


    public SlideAdapter(Context context , ArrayList<Model> mList){

        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public SlideAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_slide , parent ,false);
        return new SlideAdapter.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull SlideAdapter.MyViewHolder holder, int position) {

        final Model temp = mList.get(position);

        holder.pname.setText(mList.get(position).getName());
        holder.pprice.setText(mList.get(position).getPrice());


        Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,Display_page.class);
                intent.putExtra("details", temp);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        int limit =10;
        if(mList.size() > limit){
            return limit;
        }
        else{
            return mList.size();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView pname, pprice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pname = itemView.findViewById(R.id.name);
            pprice = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.img);
        }
    }

}
