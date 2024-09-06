package com.example.shop.security.token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface TokenRepository extends JpaRepository<Token, UUID> {

  @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(UUID id);

  @Transactional
  @Modifying
  @Query("UPDATE Token c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
  public int updateConfirmedAt(String token, LocalDateTime confirmedAt);

  
  Optional<Token> findByToken(String token);

  // List<Token> saveAll(List<Token> token);
}
