package com.obs.order.barang.controller;
import com.obs.order.barang.dto.ItemReq;
import com.obs.order.barang.model.Item;
import com.obs.order.barang.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping("/saveitem")
    public Object saveItem(@RequestBody ItemReq req){
        return itemService.saveItem(req);
    }

    @PutMapping("/edititem")
    public Object editItem(@RequestBody ItemReq req){
        return itemService.editItem(req);
    }

    @GetMapping("/getbyiditem")
    public Object getByIdItem(@RequestParam("id") long id){
        return itemService.getItemById(id);
    }

    @GetMapping("/getallitem")
    public List<Item> getAllItem(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false) Boolean isShow){
        return itemService.getItems(page, size, isShow);
    }

    @DeleteMapping("/deleteitem")
    public void deleteItem(@RequestParam("id") long id){
        itemService.deleteItem(id);
    }
}
