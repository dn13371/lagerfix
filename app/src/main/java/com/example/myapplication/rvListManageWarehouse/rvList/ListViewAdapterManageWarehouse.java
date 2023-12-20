package com.example.myapplication.rvListManageWarehouse.rvList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ListViewAdapterManageWarehouse extends RecyclerView.Adapter<ListViewAdapterManageWarehouse.ViewHolder> {

    private List<ListItemManageWarehouse> items;

    public ListViewAdapterManageWarehouse(List<ListItemManageWarehouse> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.allowed_on_warehouse, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemManageWarehouse item = items.get(position);
        holder.userName.setText(item.getUsername());
        holder.removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the remove user button click here
                // You can use the position to identify the clicked item
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public Button removeUserButton;

        public ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
            removeUserButton = itemView.findViewById(R.id.RemoveUser);
        }
    }
    public void updateList(List<ListItemManageWarehouse> newList) {
        items.clear();
        items.addAll(newList);
        notifyDataSetChanged();
    }
}
