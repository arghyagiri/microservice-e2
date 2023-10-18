package com.tcs.training.model.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Problem {

	private String type;

	private String title;

	private Integer status;

	private String detail;

	private String instance;

}
