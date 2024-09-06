package com.example.shop.customer;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.example.shop.address.Address;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(
    name = "_customer"
)
public class Customer{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(
        columnDefinition = "TEXT"
    )
    private String first_name;

    @Column(
        columnDefinition = "TEXT"
    )
    private String last_name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_of_birth;

    @Column(
        columnDefinition = "TEXT"
    )
    private String gender;

    @Column(
        columnDefinition = "TEXT"
    )
    private String phone_number;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private List<Address> address;
    
    @Column(
        columnDefinition = "TEXT"
    )
    private String username;
    
    // @Column(
    //     columnDefinition = "TEXT"
    // )
    // private String password;

    @Enumerated(EnumType.STRING)
    @Column(
        length = 10
    )
    private AccountStatus account_status;

    private Date account_creation_date;
    
    // @OneToMany(mappedBy = "_order")
    // private List<Order> order;
}
