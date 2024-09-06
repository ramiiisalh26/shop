package com.example.shop.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepositry extends JpaRepository<User, UUID>{
    
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User a " + "SET a.enabled = TRUE, a.locked = FALSE WHERE a.username = ?1")
    public int enableUser(String username);

    @Query("SELECT u.enabled FROM User u WHERE u.username  = ?1")
    public boolean getEnbleUser(String username);
    

}
