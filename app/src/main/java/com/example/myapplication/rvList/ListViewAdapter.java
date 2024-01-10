package com.example.myapplication.rvList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.DbHelper;
import com.example.myapplication.rvListManageWarehouse.warehousesListRvList.ListItemManageWarehouse;

import org.w3c.dom.Text;

import java.util.List;

public class ListViewAdapter extends
        RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private List<ListItem> items;
    public ListViewAdapter(List<ListItem> items){
        this.items =items;
    }


    @NonNull
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = items.get(position);
        TextView itemName = holder.itemName;
        TextView itemPrice = holder.itemPrice;
        TextView qty = holder.qty;
        Button plusButton = holder.plusButton;
        Button minusButton = holder.minusButton;
        Context context = holder.itemView.getContext();
        DbHelper dbHelper = new DbHelper(context);

        itemName.setText(item.getItem_description());

        // Uncomment the following line if you want to display the price
        itemPrice.setText(String.valueOf(item.getPrice()));

        // Assuming getQuantity() returns an integer
        qty.setText(String.valueOf(item.getQuantity()));

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer newqty = dbHelper.incrementQuantityById(dbHelper.getQuantityById(item.getId()),item.getId());
                qty.setText(String.valueOf(newqty));
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer newqty = dbHelper.decrementQuantityById(dbHelper.getQuantityById(item.getId()),item.getId());
                qty.setText(String.valueOf(newqty));
                if (newqty == 0){
                    dbHelper.deleteItemById(item.getId());
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemPrice;
        public TextView qty;

        public Button minusButton;

        public Button plusButton;



        public ViewHolder(View itemView) {

            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.price_text_view);
            qty = itemView.findViewById(R.id.amount_text_view);
            minusButton = itemView.findViewById(R.id.minus_button);
            plusButton = itemView.findViewById(R.id.plus_button);



        }
    }
    public void updateList(List<ListItem> newList) {
        items.clear();
        items.addAll(newList);
        notifyDataSetChanged();
    }
}