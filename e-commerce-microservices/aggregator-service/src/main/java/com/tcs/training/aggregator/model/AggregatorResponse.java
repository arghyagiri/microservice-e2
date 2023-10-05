package com.tcs.training.aggregator.model;


import com.tcs.training.aggregator.feign.model.CustomerDTO;
import com.tcs.training.aggregator.feign.model.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AggregatorResponse {
    Long orderId;
    CustomerDTO customer;
    List<ProductDTO> products;
}
