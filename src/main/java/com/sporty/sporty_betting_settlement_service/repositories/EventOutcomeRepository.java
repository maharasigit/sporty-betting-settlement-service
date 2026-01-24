package com.sporty.sporty_betting_settlement_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sporty.sporty_betting_settlement_service.entity.AppUser;
import com.sporty.sporty_betting_settlement_service.entity.EventOutcomeEntity;

import java.util.List;
import java.util.Optional;

public interface EventOutcomeRepository extends JpaRepository<EventOutcomeEntity, Long> {
   
}
