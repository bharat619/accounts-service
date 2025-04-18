package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Accounts extends BaseEntity{
    @Column(name = "customer_id") // optional since its the same as column in DB
    private Long customerId;
    @Id
    private Long accountNumber;

    private String accountType;
    private String branchAddress;
}
