package com.tcs.training.ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "BORROWER",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UC_BORROWER",
                        columnNames = {"emailAddress"})
        })
public class Borrower implements Serializable {
    @Id
    private Long borrowerId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phone;

    @OneToMany(mappedBy = "borrower")
    private Set<Book> books;
}
