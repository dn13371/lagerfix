package com.example.myapplication.WarehousesRvList;

public class ListItemWarehouse {
    private String warehouseId;
    private String warehouseName;

    public ListItemWarehouse(String warehouseId, String warehouseName) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }
}
