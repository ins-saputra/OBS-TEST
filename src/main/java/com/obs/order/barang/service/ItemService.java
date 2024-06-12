package com.obs.order.barang.service;

import com.obs.order.barang.constant.ObsConstant;
import com.obs.order.barang.dto.ItemReq;
import com.obs.order.barang.exception.InvalidDataException;
import com.obs.order.barang.exception.ItemException;
import com.obs.order.barang.model.Item;
import com.obs.order.barang.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.obs.order.barang.util.Pagination.setPagination;


@Slf4j
@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    InventoryService inventoryService;

    public Object getItemById (Long id){
        try{
            return itemRepository.findById(id).orElseThrow(() -> new ItemException(ObsConstant.DATA_NOT_FOUND));
        } catch (ItemException e){
            log.error("Error occurred while get by id item: {}", e.getMessage());
            throw new ItemException(ObsConstant.FAILED_TO_GET_BY_ID);
        }
    }

    public List<Item> getItems(int page, int size, Boolean isShow){
        try{
            Pageable pageable = setPagination(page, size, ObsConstant.ID, ObsConstant.SORT_ASCENDING);
            List<Item> items = itemRepository.findAll(pageable).getContent();

            if (Boolean.TRUE.equals(isShow)) {
                items.forEach(item -> {
                    item.setStock(inventoryService.getRemainingStock(item.getId()));
                });
            }
            return  items;
        } catch (Exception e) {
            log.error("Error occurred while get all item: {}", e.getMessage());
            throw new InvalidDataException(ObsConstant.FAILED_TO_GET_ALL );
        }
    }

    public Object saveItem(ItemReq req){
        try {
            // Validate mandatory fields
            if (req.getName() == null || req.getPrice() == null || req.getName().isEmpty()) {
                throw new InvalidDataException(ObsConstant.NAME_AND_PRICE);
            }

            // Check if item with the same name already exists
            if (itemRepository.existsByName(req.getName())) {
                throw new InvalidDataException(ObsConstant.ALREADY_NAME);
            }

            Item itemSave = Item.builder()
                    .price(req.getPrice())
                    .name(req.getName())
                    .build();

            return itemRepository.save(itemSave);
        } catch (InvalidDataException e) {
            log.error("Error occurred while saving item: {}", e.getMessage());
            throw new InvalidDataException(ObsConstant.FAILED_TO_SAVE);
        }
    }

    public Object editItem(ItemReq req){
        try {
            Item item = itemRepository.findById(req.getId())
                    .orElseThrow(() -> new ItemException(ObsConstant.DATA_NOT_FOUND));

            if (item.getName().equals(req.getName())) {
                item.setName(req.getName());
                item.setPrice(req.getPrice());
            } else {
                if (itemRepository.existsByName(req.getName())) {
                    throw new InvalidDataException(ObsConstant.ALREADY_NAME);
                }
                item.setName(req.getName());
                item.setPrice(req.getPrice());
            }

            return itemRepository.saveAndFlush(item);
        } catch (Exception e) {
            log.error("Error occurred while editing item: {}", e.getMessage());
            throw new InvalidDataException(ObsConstant.FAILED_TO_EDIT);
        }
    }

    public void deleteItem(Long id) {
        try {
            itemRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error occurred while deleting item: {}", e.getMessage());
            throw new ItemException(ObsConstant.FAILED_TO_DELETE);
        }
    }

}
