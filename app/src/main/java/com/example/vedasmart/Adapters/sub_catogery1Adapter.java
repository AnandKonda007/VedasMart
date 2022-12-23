package com.example.vedasmart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vedasmart.InterFace.Sub_category2_Interface;
import com.example.vedasmart.R;
import com.example.vedasmart.DashBordServerResponseModels.SubCategorysModel;

import java.util.ArrayList;

public class sub_catogery1Adapter extends RecyclerView.Adapter<sub_catogery1Adapter.ViewHolder> {

    private final Sub_category2_Interface sub_category2_interface;
    Context context;
    ArrayList<SubCategorysModel> subCategorysModels;

    public sub_catogery1Adapter(Sub_category2_Interface sub_category2_interface, Context context, ArrayList<SubCategorysModel> subCategorysModels) {
        this.sub_category2_interface = sub_category2_interface;
        this.context = context;
        this.subCategorysModels = subCategorysModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_category1, parent, false);
        return new sub_catogery1Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.text.setText(subCategorysModels.get(position).getSubCategoryName());
        Glide.with(context).load("http://liveapi-vmart.softexer.com" + subCategorysModels
                        .get(position).getSubCategoryProfilePic())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sub_category2_interface != null) {
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        sub_category2_interface.OnItemClickSub(pos, subCategorysModels.get(holder.getAdapterPosition()).getSubCategoryID());
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if (subCategorysModels != null) {
            return subCategorysModels.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text2);
            image = itemView.findViewById(R.id.profile_image2);
        }
    }
}
