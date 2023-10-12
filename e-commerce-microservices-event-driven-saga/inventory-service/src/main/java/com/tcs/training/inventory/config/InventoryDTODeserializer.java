package com.tcs.training.inventory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.inventory.model.exception.InventoryUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;
@Slf4j
public class InventoryDTODeserializer  implements Deserializer<InventoryUpdateDTO> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //Do nothing
    }

    @Override
    public InventoryUpdateDTO deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                log.info("Null received at deserializing");
                return null;
            }
            String str = new String(data, StandardCharsets.UTF_8);
            log.info("Deserializing... " + str);
            return objectMapper.readValue(str, InventoryUpdateDTO.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public void close() {
        //Do nothing
    }
}

