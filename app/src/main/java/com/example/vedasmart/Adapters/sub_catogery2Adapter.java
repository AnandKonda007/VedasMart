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
import com.example.vedasmart.DashBordServerResponseModels.Info;

import java.util.ArrayList;

public class sub_catogery2Adapter extends RecyclerView.Adapter<sub_catogery2Adapter.ViewHolder> {

    Context context;
    ArrayList<Info> infoArrayList;

    public sub_catogery2Adapter(Context context, ArrayList<Info> infoArrayList) {
        this.context = context;
        this.infoArrayList = infoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sub_category2, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.product_name.setText(infoArrayList.get(position).getProductName());
        holder.mrp.setText(String.valueOf(infoArrayList.get(position).getMRP_Price()));
        holder.price.setText(String.valueOf(infoArrayList.get(position).getVMART_Price()));
        holder.netweight.setText(infoArrayList.get(position).getNetweight());
        holder.save.setText(save(infoArrayList.get(position).getMRP_Price(), infoArrayList.get(position).getVMART_Price()));
        Glide.with(context).load("http://liveapi-vmart.softexer.com" + infoArrayList
                        .get(position).getProductImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground).into(holder.image);
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(infoArrayList.get(position).getCustomerQuantity());
                count = count + 1;
                Info info = (infoArrayList.get(holder.getAdapterPosition()));
                info.setCustomerQuantity(String.valueOf(count));
                infoArrayList.set(holder.getAdapterPosition(), info);
                holder.quantity.setText("" + info.getCustomerQuantity());

            }
        });
        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(infoArrayList.get(position).getCustomerQuantity());
                if (count <= 0) {
                    count = 0;
                } else {
                    count = count - 1;
                }
                Info info = (infoArrayList.get(holder.getAdapterPosition()));
                info.setCustomerQuantity(String.valueOf(count));
                infoArrayList.set(holder.getAdapterPosition(), info);
                holder.quantity.setText("" + info.getCustomerQuantity());


            }
        });

    }

    public String save(int mrp_price, int vmart_price) {
        int mrp = mrp_price;
        int vmartPrice = vmart_price;
        int save = mrp - vmartPrice;
        return String.valueOf(save);
    }


    @Override
    public int getItemCount() {
        if (infoArrayList != null) {
            return infoArrayList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView product_name, netweight, mrp, price, save, quantity;
        ImageButton decrement, increment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image3);
            product_name = itemView.findViewById(R.id.Product_Name3);
            netweight = itemView.findViewById(R.id.netweight);
            mrp = itemView.findViewById(R.id.MRP3);
            price = itemView.findViewById(R.id.Price3);
            save = itemView.findViewById(R.id.Save3);
            quantity = itemView.findViewById(R.id.quantity3);
            decrement = itemView.findViewById(R.id.decrement3);
            increment = itemView.findViewById(R.id.increment3);


        }
    }
}
