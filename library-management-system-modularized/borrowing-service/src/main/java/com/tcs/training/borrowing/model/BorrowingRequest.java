package com.tcs.training.borrowing.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRequest {

    @Valid
    @NotNull
    @NotEmpty
    private List<Long> bookIds;

    @Valid
    @NotNull
    private String userId;

}
