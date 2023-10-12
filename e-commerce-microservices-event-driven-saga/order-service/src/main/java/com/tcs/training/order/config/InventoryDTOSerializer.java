package com.tcs.training.order.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.order.model.InventoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class InventoryDTOSerializer implements Serializer<InventoryDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //Do nothing
    }

    @Override
    public byte[] serialize(String topic, InventoryDTO data) {
        try {
            if (data == null){
                log.info("Null received at serializing");
                return null;
            }
            log.info("Serializing... {}", data);
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing MessageDto to byte[]");
        }
    }

    @Override
    public void close() {
        //Do nothing
    }
}
