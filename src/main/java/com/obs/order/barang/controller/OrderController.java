package com.obs.order.barang.controller;

import com.obs.order.barang.dto.OrderReq;
import com.obs.order.barang.model.Order;
import com.obs.order.barang.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/getorderbyid")
    public Object getOrderById(@RequestParam("id") long id){
        return orderService.getOrderById(id);
    }

    @GetMapping("getallorder")
    public List<Order> getOrders(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size){
        return orderService.getOrders(page, size);
    }

    @PostMapping("/saveorder")
    public Object saveOrder(@RequestBody OrderReq req){
        return orderService.saveOrder(req);
    }

    @PutMapping("/editorder")
    public Object editOrder(@RequestBody OrderReq req){
        return orderService.editOrder(req);
    }

    @DeleteMapping("/deleteorder")
    public void deleteOrder(@RequestParam("id") long id){
        orderService.deleteOrder(id);
    }
}
