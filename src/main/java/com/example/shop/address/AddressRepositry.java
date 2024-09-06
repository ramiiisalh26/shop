package com.example.shop.address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.shop.customer.Customer;

@Repository
public interface AddressRepositry extends JpaRepository<Address, UUID>{

    @Query("SELECT a FROM Address a WHERE a.customer.id = ?1")
    List<Address> findByCustomerId(UUID customer_id);

    // @Modifying
    // @Transactional
    // @Query("UPDATE Address a SET a.customer = :customer WHERE a.id = :id")
    // void setCustomer(@Param("customer") Customer customer_id, @Param("id") UUID id);

    @Query("SELECT a FROM Address a WHERE a.customer = :customer AND a.street_address = :street_address AND a.city = :city AND a.state = :state AND a.zip = :zip AND a.country = :country")
    Optional<Address> findByCustomerAndDetails(
        @Param("customer") Customer customer,
        @Param("street_address") String streetAddress,
        @Param("city") String city,
        @Param("state") String state,
        @Param("zip") String zip,
        @Param("country") String country
    );



}
