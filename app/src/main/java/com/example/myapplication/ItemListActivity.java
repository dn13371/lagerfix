package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.myapplication.db.DBContract;
import com.example.myapplication.db.LoginDbHelper;
import com.example.myapplication.rvList.ListItem;
import com.example.myapplication.rvList.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_activity);
        Intent intent = getIntent();
        String loggedInUID = intent.getStringExtra("UID");
        createMockItemEntries(loggedInUID);
        RecyclerView recyclerView = findViewById(R.id.rvItems);
        List<ListItem> dbDump = getAllDBEntries(loggedInUID);
        ListViewAdapter adapter = new ListViewAdapter(dbDump);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
    public List<ListItem> getAllDBEntries(String uid) {
        LoginDbHelper dbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ListItem> itemList = new ArrayList<>();
        String[] projection = {DBContract.ItemsDB.COLUMN_ID, DBContract.ItemsDB.COLUMN_ITEM_DESC, DBContract.ItemsDB.COLUMN_EAN,DBContract.ItemsDB.COLUMN_BELONGS_TO, DBContract.ItemsDB.COLUMN_QTY};
        String selection = DBContract.ItemsDB.COLUMN_BELONGS_TO + " = ?";
        String[] selectionArgs = {uid};

        Cursor cursor = db.query(
                DBContract.ItemsDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DBContract.ItemsDB.COLUMN_ID);
                String itemID = cursor.getString(idIndex);
                int descIndex = cursor.getColumnIndex(DBContract.ItemsDB.COLUMN_ITEM_DESC);
                String itemDesc = cursor.getString(descIndex);
                int eanIndex = cursor.getColumnIndex(DBContract.ItemsDB.COLUMN_EAN);
                String eanDesc = cursor.getString(eanIndex);
                int belongsToIndex = cursor.getColumnIndex(DBContract.ItemsDB.COLUMN_BELONGS_TO);
                String belongsTo = cursor.getString(belongsToIndex);
                int qtyIndex = cursor.getColumnIndex(DBContract.ItemsDB.COLUMN_QTY);
                String qty = cursor.getString(qtyIndex);
                ListItem lm = new ListItem(itemID, itemDesc, eanDesc, belongsTo, qty);
                itemList.add(lm);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return itemList;
    }
    private void createMockItemEntries(String uid) {
        LoginDbHelper dbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < 2; i++) {
            ContentValues values = new ContentValues();
            values.put(DBContract.ItemsDB.COLUMN_ITEM_DESC, "Item " + 1+i);
            values.put(DBContract.ItemsDB.COLUMN_EAN, 100000000000L + i); // Example EAN values, adjust as needed
            values.put(DBContract.ItemsDB.COLUMN_BELONGS_TO, uid);
            values.put(DBContract.ItemsDB.COLUMN_QTY, i+4   ); // Example quantity values, adjust as needed

            long newRowId = db.insert(DBContract.ItemsDB.TABLE_NAME, null, values);
        }

        System.out.println("Mock entries added to ITEM DB");
        dbHelper.close();
    }
}