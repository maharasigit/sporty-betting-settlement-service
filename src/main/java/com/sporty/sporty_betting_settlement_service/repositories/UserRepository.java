package com.sporty.sporty_betting_settlement_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sporty.sporty_betting_settlement_service.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    
      // <-- Add this method
    List<AppUser> findByRole(String role);
}
