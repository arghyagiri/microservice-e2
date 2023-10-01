package com.tcs.training.borrowing.model;

import com.tcs.training.borrowing.entity.Borrowing;
import com.tcs.training.borrowing.feign.model.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingResponse extends Borrowing implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private List<BookDTO> books;

    private BookDTO book;

}
