package com.example.vedasmart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vedasmart.R;
import com.example.vedasmart.ServerResponseModels.Info;

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

        View view = LayoutInflater.from(context).inflate(R.layout.items2activity2, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.text1.setText(infoArrayList.get(position).getVMART_Price());
        holder.text2.setText(infoArrayList.get(position).getMRP_Price());
        holder.text3.setText(infoArrayList.get(position).getQuantity());
        holder.text4.setText(infoArrayList.get(position).getProductName());
        Glide.with(context).load("http://liveapi-vmart.softexer.com" + infoArrayList
                        .get(position).getProductImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground).into(holder.image3);

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

        ImageView image3;
        TextView text1, text2, text3, text4;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image3 = itemView.findViewById(R.id.imageActView2);
            text1 = itemView.findViewById(R.id.textAct1);
            text2 = itemView.findViewById(R.id.textAct2);
            text3 = itemView.findViewById(R.id.textActQut);
            text4 = itemView.findViewById(R.id.textAct4);
            layout = itemView.findViewById(R.id.layoutAct1);

        }
    }
}
