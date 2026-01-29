package com.sporty.sporty_betting_settlement_service.service;

import com.sporty.sporty_betting_settlement_service.entity.EventOutcomeEntity;
import com.sporty.sporty_betting_settlement_service.entity.BetEntity;
import com.sporty.sporty_betting_settlement_service.entity.BetStatus;
import com.sporty.sporty_betting_settlement_service.repositories.BetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventOutcomeConsumer {
    private static final Logger log = LoggerFactory.getLogger(EventOutcomeConsumer.class);

    @Autowired private BetSettlementProducer betSettlementProducer;
    @Autowired private BetRepository betRepository;

    @Transactional // CRITICAL: Ensures DB save and RocketMQ call are handled together
    @KafkaListener(topics = "event-outcomes", groupId = "sporty-group")
    public void consume(EventOutcomeEntity eventOutcome) {
        log.info("Processing event outcome for ID: {}", eventOutcome.getEventId());

        List<BetEntity> betsToSettle = betRepository.findByEventId(eventOutcome.getEventId());

        for (BetEntity bet : betsToSettle) {
            // 1. IDEMPOTENCY GUARD: Check if already processed
            if (bet.getStatus() == BetStatus.SETTLED) {
                log.warn("Bet {} is already settled. Skipping to prevent double-payout.", bet.getBetId());
                continue;
            }

            // 2. DOMAIN LOGIC: Update the bet state
            bet.setEventWinnerId(eventOutcome.getEventWinnerId());
            bet.setStatus(BetStatus.SETTLED); // Move to final state
            
            // 3. PERSISTENCE
            betRepository.save(bet);

            // 4. HANDOFF: Forward to RocketMQ mock
            betSettlementProducer.send(bet);
        }
    }


    
    
    //Getter for Thymeleaf UI
    public List<BetEntity> getBets() {
        return betRepository.findAll(); // all bets, including settled ones
    }

   
}
