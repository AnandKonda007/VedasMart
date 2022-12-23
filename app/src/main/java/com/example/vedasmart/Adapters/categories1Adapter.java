package com.example.vedasmart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vedasmart.InterFace.Sub_category1_Interface;
import com.example.vedasmart.R;
import com.example.vedasmart.DashBordServerResponseModels.CategoryInfo;

import java.util.ArrayList;

public class categories1Adapter extends RecyclerView.Adapter<categories1Adapter.categories1ViewHolder> {
    private final Sub_category1_Interface sub_category1_interface;
    Context context;
    ArrayList<CategoryInfo> Products;


    public categories1Adapter(Sub_category1_Interface sub_category1_interface, Context context, ArrayList<CategoryInfo> products) {
        this.sub_category1_interface = sub_category1_interface;
        this.context = context;
        Products = products;
    }

    @NonNull
    @Override
    public categories1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.catogeries1, parent, false);
        categories1ViewHolder viewHolder = new categories1ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull categories1ViewHolder holder, int position) {
        holder.text.setText(Products.get(position).getCategoryName());
        Glide.with(context).load("http://liveapi-vmart.softexer.com" + Products
                        .get(position).getCategoryImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sub_category1_interface != null) {
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        sub_category1_interface.OnItemClick(pos, Products.get(holder.getAdapterPosition()).getCategoryID());
                    }
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return Products.size();
    }

    public class categories1ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;

        public categories1ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.profile_image);
        }
    }
}
