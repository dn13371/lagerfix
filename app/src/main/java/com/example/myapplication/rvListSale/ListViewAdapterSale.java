package com.example.myapplication.rvListSale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.DbHelper;
import com.example.myapplication.rvList.ListItem;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapterSale extends
        RecyclerView.Adapter<ListViewAdapterSale.ViewHolder> {

    private List<ListItemSale> items;
    public ListViewAdapterSale(List<ListItemSale> items) {
        this.items = new ArrayList<>(items);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.row_item_sale, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemSale item = items.get(position);
        TextView itemName = holder.itemName;
        TextView itemPrice = holder.itemPrice;
        TextView qty = holder.qty;
        TextView total = holder.total;

        itemName.setText(item.getItem_description());

        // Uncomment the following line if you want to display the price
        itemPrice.setText(String.valueOf(item.getPrice()));
        total.setText(String.valueOf(item.getTotalPrice()));
        // Assuming getQuantity() returns an integer
        qty.setText(String.valueOf(item.getQuantity()));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemPrice;
        public TextView qty;

        public TextView total;



        public ViewHolder(View itemView) {

            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.price_text_view);
            qty = itemView.findViewById(R.id.amount_text);
            total = itemView.findViewById(R.id.total_price);



        }
    }
    public void updateList(List<ListItemSale> newList) {
        Log.d("ListViewAdapterSale", "Updated list. New size: " + newList.size());
        items.clear();
        Log.d("ListViewAdapterSale", "after items.clear: " + newList.size());

        items.addAll(newList);
        notifyDataSetChanged();
    }
}