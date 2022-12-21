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
import com.example.vedasmart.ServerResponseModels.DailyDeals;

import java.util.ArrayList;
public class categories2Adapter extends RecyclerView.Adapter<categories2Adapter.categories2ViewHolder> {
    Context context;
    ArrayList<DailyDeals> dailyDeals;

    public categories2Adapter(Context context, ArrayList<DailyDeals> dailyDeals) {
        this.context = context;
        this.dailyDeals = dailyDeals;
    }

    @NonNull
    @Override
    public categories2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.catogeries2, parent, false);
        categories2ViewHolder viewHolder = new categories2ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull categories2ViewHolder holder, int position) {
        Glide.with(context).load("http://liveapi-vmart.softexer.com" + dailyDeals
                        .get(position).getProductImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground).into(holder.image);
        holder.mrp.setText(dailyDeals.get(position).getMRP_Price());
        // holder.save.setText(dailyDeals.get(position).get());
        holder.price.setText(dailyDeals.get(position).getVMART_Price());
        holder.productname.setText(dailyDeals.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return dailyDeals.size();
    }

    public class categories2ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price, mrp, save, productname;
        ConstraintLayout layout;

        public categories2ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.Price);
            mrp = itemView.findViewById(R.id.MRP);
            save = itemView.findViewById(R.id.Save);
            productname = itemView.findViewById(R.id.Product_Name);
            // layout=itemView.findViewById(R.id.constraint_layout);


        }
    }
}
