package com.example.myapplication.rvListManageWarehouse.warehousesListRvList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.EditWarehouseActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.DbHelper;

import java.util.List;

public class ListViewAdapterManageWarehouse extends RecyclerView.Adapter<ListViewAdapterManageWarehouse.ViewHolder> {

    private List<ListItemManageWarehouse> items;
    private String warehouse;
    private String currentUser;

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
        Context context = holder.itemView.getContext();
        DbHelper dbHelper = new DbHelper(context);

        ListItemManageWarehouse item = items.get(position);
        holder.userName.setText(item.getUsername());
        holder.removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = item.getUserId();
                dbHelper.removeWarehouseAccess(warehouse,uid);

                updateList(dbHelper.getCurrentAccessObjectsList(warehouse,currentUser));

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
    public void setWarehouse(String warehouse){
        this.warehouse = warehouse;
    }
    public void setCurrentUser(String currentUser){
        this.currentUser = currentUser;
    }
}
