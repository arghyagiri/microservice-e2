package com.tcs.training.aggregator.model;


import com.tcs.training.aggregator.feign.model.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AggregatorCustomerResponse {
    CustomerDTO customer;
}
