package com.tcs.training.library.service;

import com.tcs.training.library.model.exception.InventoryUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "library-service", groupId = "inventory-update")
    public InventoryUpdateDTO update(InventoryUpdateDTO inventoryUpdateDTO) {
        log.info("Received message: {}", inventoryUpdateDTO);
        return inventoryService.update(inventoryUpdateDTO);
    }
}
