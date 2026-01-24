package com.sporty.sporty_betting_settlement_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sporty.sporty_betting_settlement_service.entity.BetEntity;
import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<BetEntity, String> {

    // Fetch bets by eventId
    List<BetEntity> findByEventId(String eventId);
}
