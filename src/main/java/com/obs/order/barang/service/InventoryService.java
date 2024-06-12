package com.obs.order.barang.service;

import com.obs.order.barang.constant.ObsConstant;
import com.obs.order.barang.dto.InventReq;
import com.obs.order.barang.exception.InvalidDataException;
import com.obs.order.barang.exception.InventoryNotFoundException;
import com.obs.order.barang.model.Inventory;
import com.obs.order.barang.repository.InventoryRepository;
import com.obs.order.barang.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.obs.order.barang.util.Pagination.setPagination;

@Slf4j
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory getInventoryById(Long id) {
        try{
            return inventoryRepository.findById(id)
                    .orElseThrow(() -> new InventoryNotFoundException(ObsConstant.DATA_NOT_FOUND));
        }catch (Exception e){
            log.error("Error occurred while get all inventory: {}", e.getMessage());
            throw new InventoryNotFoundException(ObsConstant.FAILED_TO_GET_BY_ID);
        }
    }

    public List<Inventory> getInventorys(int page, int size) {
        try{
            Pageable pageable = setPagination(page, size, ObsConstant.ITEM, ObsConstant.SORT_ASCENDING);
            return inventoryRepository.findAll(pageable).getContent();
        }catch (Exception e){
            log.error("Error occurred while get all inventory: {}", e.getMessage());
            throw new InvalidDataException(ObsConstant.FAILED_TO_GET_ALL );
        }

    }

    public Object saveInventory(InventReq req) {
        try{
            Inventory saveInven = Inventory.builder()
                    .qty(req.getQty())
                    .type(req.getType())
                    .item(req.getItem())
                    .build();

            return inventoryRepository.save(saveInven);
        }catch (Exception e){
            log.error("Error occurred while save inventory: {}", e.getMessage());
            throw new InvalidDataException(ObsConstant.FAILED_TO_SAVE);
        }
    }

    public Object editInventory (InventReq req){
        try {
            Inventory inventory = inventoryRepository.findById(req.getId())
                    .orElseThrow(() -> new InventoryNotFoundException(ObsConstant.INVENTORY_NOT_FOUND));
            inventory.setQty(req.getQty());
            inventory.setType(req.getType());
            inventory.setItem(req.getItem());

            return inventoryRepository.save(inventory);
        } catch (Exception e) {
            log.error("Error occurred while saving inventory: {}", e.getMessage());
            throw new InvalidDataException(ObsConstant.FAILED_TO_EDIT);
        }
    }

    public void deleteInventory(Long id) {
        try{
            inventoryRepository.deleteById(id);
        } catch (Exception e){
            log.error("Error occurred while saving inventory: {}", e.getMessage());
            throw new InvalidDataException(ObsConstant.FAILED_TO_DELETE);
        }

    }

    public int getRemainingStock(Long itemId) {
        int topUpQty = inventoryRepository.sumQtyByItemIdAndType(itemId, "T");
        int withdrawalQty = inventoryRepository.sumQtyByItemIdAndType(itemId, "W");
        return topUpQty - withdrawalQty;
    }



}
