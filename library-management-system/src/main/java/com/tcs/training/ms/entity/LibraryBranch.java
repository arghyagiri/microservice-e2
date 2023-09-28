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
        name = "LIBRARY_BRANCH",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UC_LIBRARY_BRANCH",
                        columnNames = {"libraryBranchName"})
        })
public class LibraryBranch implements Serializable {
    @Id
    private Long libraryBranchId;
    private String libraryBranchName;
    private String address;

    @OneToMany(mappedBy = "libraryBranch")
    private Set<Book> books;
}
