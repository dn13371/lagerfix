package com.example.myapplication.rvListManageWarehouse.rvList;

public class ListItemManageWarehouse {
    private String userId;
    private String username;

    public ListItemManageWarehouse(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
