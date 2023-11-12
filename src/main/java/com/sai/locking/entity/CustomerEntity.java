package com.sai.locking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Table(name="Customer")
@Builder
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="version")
    @Version
    private Integer version;

}
