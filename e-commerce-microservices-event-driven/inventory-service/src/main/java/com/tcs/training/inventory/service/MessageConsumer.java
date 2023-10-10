package com.tcs.training.inventory.service;

import com.tcs.training.inventory.model.exception.InventoryDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    @KafkaListener(topics = "test", groupId = "my-group-id")
    public void listen(InventoryDTO InventoryUpdateDTO) {
        System.out.println("Received message: " + InventoryUpdateDTO);
    }
}
