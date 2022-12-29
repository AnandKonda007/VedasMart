package com.example.vedasmart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vedasmart.InterFace.Side_navigation_interface;
import com.example.vedasmart.R;

import java.util.ArrayList;

public class side_NavigationAdapter extends RecyclerView.Adapter<side_NavigationAdapter.ViewHolder> {
    private final Side_navigation_interface sideNavigationInterface;
    Context context;
    ArrayList<String> sidemenu_items;

    public side_NavigationAdapter(Side_navigation_interface sideNavigationInterface, Context context, ArrayList<String> sidemenu_items) {
        this.sideNavigationInterface = sideNavigationInterface;
        this.context = context;
        this.sidemenu_items = sidemenu_items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.side_navigation_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item_name.setText(sidemenu_items.get(position));


        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mContext = v.getContext();
                final Intent intent;
    switch (getAdapterPostion()){
        case 0:
           intent =  new Intent(context, FirstActivity.class);
           break;

        case 1:
            intent =  new Intent(context, SecondActivity.class);
            break;
           ...
        default:
           intent =  new Intent(context, DefaultActivity.class);
           break;
     }
                mContext.startActivity(intent);
            }*/
    }

    @Override
    public int getItemCount() {
        return sidemenu_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_name;
        LinearLayout row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            item_name = itemView.findViewById(R.id.side_menu_items);
            row = itemView.findViewById(R.id.row);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sideNavigationInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            sideNavigationInterface.side_Navigation_On_ItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
