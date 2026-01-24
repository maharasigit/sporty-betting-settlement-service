package com.sporty.sporty_betting_settlement_service.service;

import com.sporty.sporty_betting_settlement_service.entity.EventOutcomeEntity;
import com.sporty.sporty_betting_settlement_service.entity.BetEntity;
import com.sporty.sporty_betting_settlement_service.repositories.BetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventOutcomeConsumer {
	
	private static final Logger log =
	            LoggerFactory.getLogger(EventOutcomeConsumer.class);

    @Autowired
    private BetSettlementProducer betSettlementProducer;
    
    @Autowired
    private BetRepository betRepository;

  

    @KafkaListener(topics = "event-outcomes", groupId = "sporty-group",
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume(EventOutcomeEntity eventOutcome) {
        log.info("Kafka Consumer received: " + eventOutcome);
        List<BetEntity> betsToSettle = betRepository.findByEventId(eventOutcome.getEventId());
        // Match bets with this event
        for (BetEntity bet : betsToSettle) {
            bet.setEventWinnerId(eventOutcome.getEventWinnerId());
            betRepository.save(bet);  // persist the updated bet

                // Send to mock RocketMQ producer
                betSettlementProducer.send(bet);
            }
        }
    
    
    //Getter for Thymeleaf UI
    public List<BetEntity> getBets() {
        return betRepository.findAll(); // all bets, including settled ones
    }

   
}
