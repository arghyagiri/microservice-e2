package com.tcs.training.aggregator.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
 * https://datatracker.ietf.org/doc/html/rfc7807
 */
public class Problem {

    private String type;

    private String title;

    private Integer status;

    private String detail;

    private String instance;

}
