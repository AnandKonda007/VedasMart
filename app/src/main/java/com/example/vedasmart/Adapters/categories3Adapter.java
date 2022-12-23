package com.example.vedasmart.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vedasmart.R;
import com.example.vedasmart.DashBordServerResponseModels.BestSellings;

import java.util.ArrayList;

public class categories3Adapter extends RecyclerView.Adapter<categories3Adapter.categories3ViewHolder> {
    Context context;
    ArrayList<BestSellings> bestSellingsArrayList;


    public categories3Adapter(Context context, ArrayList<BestSellings> bestSellingsArrayList) {
        this.context = context;
        this.bestSellingsArrayList = bestSellingsArrayList;
    }

    @NonNull
    @Override
    public categories3ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.catogeries3, parent, false);
        categories3ViewHolder viewHolder = new categories3ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull categories3ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load("http://liveapi-vmart.softexer.com" + bestSellingsArrayList
                        .get(position).getProductImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground).into(holder.image);
        holder.price.setText(bestSellingsArrayList.get(position).getVMART_Price());
        holder.mrp.setText(bestSellingsArrayList.get(position).getMRP_Price());
        holder.save.setText(save(bestSellingsArrayList.get(position).getMRP_Price(), bestSellingsArrayList.get(position).getVMART_Price()));
        holder.productname.setText(bestSellingsArrayList.get(position).getProductName());

        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(bestSellingsArrayList.get(position).getCustomerQuantity());
                count= count+1;
                BestSellings bestSellings=(bestSellingsArrayList.get(holder.getAdapterPosition()));
                bestSellings.setCustomerQuantity(String.valueOf(count));
                bestSellingsArrayList.set(holder.getAdapterPosition(),bestSellings);
                holder.quantity.setText("" + bestSellings.getCustomerQuantity());
            }
        });
        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(bestSellingsArrayList.get(position).getCustomerQuantity());
                if (count <= 0) {
                    count = 0;
                } else {
                    count = count - 1;
                }
                BestSellings bestSellings=(bestSellingsArrayList.get(holder.getAdapterPosition()));
                bestSellings.setCustomerQuantity(String.valueOf(count));
                bestSellingsArrayList.set(holder.getAdapterPosition(),bestSellings);
                holder.quantity.setText(" " + bestSellings.getCustomerQuantity());
            }
        });


    }


    public String save(String mrp_price, String vmart_price) {
        int mrp = Integer.parseInt(mrp_price);
        int vmartPrice = Integer.parseInt(vmart_price);
        int save = mrp - vmartPrice;
        return String.valueOf(save);


    }

    @Override
    public int getItemCount() {

        return bestSellingsArrayList.size();
    }

    public class categories3ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price, mrp, save, productname, quantity;
        ImageButton decrement, increment;

        public categories3ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image2);
            price = itemView.findViewById(R.id.Price2);
            mrp = itemView.findViewById(R.id.MRP2);
            save = itemView.findViewById(R.id.Save2);
            productname = itemView.findViewById(R.id.Product_Name2);
            quantity = itemView.findViewById(R.id.quantity2);
            decrement = itemView.findViewById(R.id.decrement2);
            increment = itemView.findViewById(R.id.increment2);

        }
    }
}
