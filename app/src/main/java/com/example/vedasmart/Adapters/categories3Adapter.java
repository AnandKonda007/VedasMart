package com.example.vedasmart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vedasmart.R;
import com.example.vedasmart.ServerResponseModels.bestSellings;

import java.util.ArrayList;

public class categories3Adapter extends RecyclerView.Adapter<categories3Adapter.categories3ViewHolder> {
    Context context;
    ArrayList<bestSellings> BestSellings;

    public categories3Adapter(Context context, ArrayList<bestSellings> bestSellings) {
        this.context = context;
        this.BestSellings = bestSellings;
    }

    @NonNull
    @Override
    public categories3ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.catogeries3, parent, false);
        categories3ViewHolder viewHolder = new categories3ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull categories3ViewHolder holder, int position) {
        Glide.with(context).load("http://liveapi-vmart.softexer.com" + BestSellings
                        .get(position).getProductImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground).into(holder.image);
        holder.price.setText(BestSellings.get(position).getVMART_Price());
        holder.mrp.setText(BestSellings.get(position).getMRP_Price());
        // holder.save.setText(BestSellings.get(position).get());
        holder.productname.setText(BestSellings.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return BestSellings.size();
    }

    public class categories3ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price, mrp, save, productname;
        ConstraintLayout layout;

        public categories3ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image2);
            price = itemView.findViewById(R.id.Price2);
            mrp = itemView.findViewById(R.id.MRP2);
            save = itemView.findViewById(R.id.Save2);
            productname = itemView.findViewById(R.id.Product_Name2);
            // layout=itemView.findViewById(R.id.constraint_layout);


        }
    }
}
