package com.example.shop.address;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.example.shop.customer.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@ToString(exclude = {"customer"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(
    name = "_address"
)
public class Address {

    @Id
    @GeneratedValue( generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    
    @Column(
        columnDefinition = "TEXT"
    )
    private String street_address;
    
    @Column(
        columnDefinition = "TEXT"
    )
    private String city;
    
    @Column(
        columnDefinition = "TEXT"
    )
    private String state;
    
    @Column(
        columnDefinition = "TEXT"
    )
    private String zip;
    
    @Column(
        columnDefinition = "TEXT"
    )
    private String country;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @Column(
        length = 15
    )
    @Enumerated(EnumType.STRING)
    private AddressFor addressFor;

    
}
