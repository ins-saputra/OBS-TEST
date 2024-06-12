package com.obs.order.barang.service;

import com.obs.order.barang.constant.ObsConstant;
import com.obs.order.barang.dto.InventReq;
import com.obs.order.barang.dto.OrderReq;
import com.obs.order.barang.exception.InsufficientStockException;
import com.obs.order.barang.exception.InvalidDataException;
import com.obs.order.barang.exception.ItemException;
import com.obs.order.barang.exception.OrderNotFoundException;
import com.obs.order.barang.model.Order;
import com.obs.order.barang.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.obs.order.barang.util.Pagination.setPagination;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryService inventoryService;

    public Object getOrderById(Long id) {
        try{
            return orderRepository.findById(id)
                    .orElseThrow(() -> new OrderNotFoundException(ObsConstant.ORDER_NOT_FOUND));
        }catch (Exception e){
            log.error("Error occurred while get order by id: {}", e.getMessage());
            throw new OrderNotFoundException(ObsConstant.FAILED_TO_GET_BY_ID);
        }

    }

    public List<Order> getOrders(int page, int size) {
        try{
            Pageable pageable = setPagination(page, size, ObsConstant.ID, ObsConstant.SORT_ASCENDING);
            return orderRepository.findAll(pageable).getContent();
        }catch (Exception e){
            log.error("Error occurred while get all orders: {}", e.getMessage());
            throw new InvalidDataException(ObsConstant.FAILED_TO_GET_ALL );
        }

    }

    public Object saveOrder(OrderReq req) {
        try{
            // Memeriksa stok tersisa dari item
            int remainingStock = inventoryService.getRemainingStock(req.getItem().getId());
            if (req.getQty() > remainingStock) {
                throw new InsufficientStockException("Insufficient stock for item: " + req.getItem().getName());
            }

            // Membuat entri pengurangan stok di tabel Inventory
            InventReq inventReq = InventReq.builder()
                    .qty(req.getQty())
                    .type("W")
                    .item(req.getItem())
                    .build();
            inventoryService.saveInventory(inventReq);

            return orderRepository.save(Order.builder()
                    .item(req.getItem())
                    .qty(req.getQty())
                    .build());
        }catch (Exception e){
            log.error("Error occurred while saving order: {}", e.getMessage());
            throw new InvalidDataException(ObsConstant.FAILED_TO_SAVE);
        }

    }

    public Object editOrder(OrderReq req) {
        try{
            var order = orderRepository.findById(req.getId())
                    .orElseThrow(() -> new OrderNotFoundException(ObsConstant.ORDER_NOT_FOUND));

            // Memeriksa stok tersisa dari item
            int remainingStock = inventoryService.getRemainingStock(req.getItem().getId());
            if (req.getQty() > remainingStock + order.getQty()) {
                throw new InsufficientStockException("Insufficient stock for item: " + req.getItem().getName());
            }

            // Mengubah jumlah stok sesuai perubahan pesanan
            log.info("log req {}, order {}", req.getQty(), order.getQty());
            int stockChange = req.getQty() - order.getQty();
            log.info("log stockChange {}",stockChange);
            InventReq inventoryUpdate = InventReq.builder()
                    .qty(Math.abs(stockChange))
                    .type("W")
                    .item(req.getItem())
                    .build();
            inventoryService.saveInventory(inventoryUpdate);

            order.setItem(req.getItem());
            order.setQty(req.getQty());

            return orderRepository.save(order);
        } catch (Exception e) {
            log.error("Error occurred while editing order: {}", e.getMessage());
            throw new InvalidDataException(ObsConstant.FAILED_TO_EDIT);
        }

    }

    public void deleteOrder(Long id) {
        try{
            orderRepository.deleteById(id);
        }catch (Exception e) {
            log.error("Error occurred while deleting order: {}", e.getMessage());
            throw new ItemException(ObsConstant.FAILED_TO_DELETE);
        }

    }

}
