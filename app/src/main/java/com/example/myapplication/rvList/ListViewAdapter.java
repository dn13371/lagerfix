package com.example.myapplication.rvList;

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
        TextView textView = holder.descTextView;
        TextView qtyView = holder.qtyView;
        qtyView.setText(item.getQuantity());
        textView.setText(item.getItem_description());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView descTextView;
        public TextView qtyView;
        public Button messageButton;


        public ViewHolder(View itemView) {
            super(itemView);
            descTextView = (TextView) itemView.findViewById(R.id.username);



        }
    }
}