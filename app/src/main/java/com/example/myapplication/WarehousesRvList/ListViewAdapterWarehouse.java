package com.example.myapplication.WarehousesRvList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.EditWarehouseActivity;
import com.example.myapplication.ItemListActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.DbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListViewAdapterWarehouse extends RecyclerView.Adapter<ListViewAdapterWarehouse.ViewHolder> {

    private List<ListItemWarehouse> items;
    private String warehouse;
    private String currentUser;

    public ListViewAdapterWarehouse(List<ListItemWarehouse> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.row_item_warehouse, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        DbHelper dbHelper = new DbHelper(context);

        ListItemWarehouse item = items.get(position);
        holder.warehouseName.setText(item.getWarehouseName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String warehouseID = item.getWarehouseId();
                String warehouseName = item.getWarehouseName();
                Intent intent = new Intent(context, ItemListActivity.class);
                intent.putExtra("UID",currentUser);
                intent.putExtra("warehouseID", warehouseID);
                intent.putExtra("warehouseName", warehouseName);
                context.startActivity(intent);

            }
        });

        holder.warehouseInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String warehouseID = item.getWarehouseId();
                String warehouseName = item.getWarehouseName();
                Intent intent = new Intent(context, EditWarehouseActivity.class);
                intent.putExtra("UID",currentUser);
                intent.putExtra("warehouseID", warehouseID);
                intent.putExtra("warehouseName", warehouseName);
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView warehouseName;
        public FloatingActionButton warehouseInfoButton;

        public ViewHolder(View itemView) {
            super(itemView);
            warehouseName = itemView.findViewById(R.id.item_name);
            warehouseInfoButton = itemView.findViewById(R.id.warehouseInfo);
        }
    }

    public void updateList(List<ListItemWarehouse> newList) {
        items.clear();
        items.addAll(newList);
        notifyDataSetChanged();
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
