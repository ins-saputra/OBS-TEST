package com.obs.order.barang.controller;

import com.obs.order.barang.dto.InventReq;
import com.obs.order.barang.model.Inventory;
import com.obs.order.barang.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @PostMapping("/saveinven")
    public Object saveInven(@RequestBody InventReq req){
        return inventoryService.saveInventory(req);
    }

    @GetMapping("/getallinven")
    public List<Inventory> getAllInven(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size){
        return inventoryService.getInventorys(page, size);
    }

    @GetMapping("/getbyidinven")
    public Object getByIdInven(@RequestParam("id") long id){
        return inventoryService.getInventoryById(id);
    }

    @PutMapping("/editinven")
    public Object editInvent(@RequestBody InventReq req){
        return inventoryService.editInventory(req);
    }

    @DeleteMapping("/deleteinven")
    public void deleteInven(@RequestParam("id") long id){
        inventoryService.deleteInventory(id);
    }

}
