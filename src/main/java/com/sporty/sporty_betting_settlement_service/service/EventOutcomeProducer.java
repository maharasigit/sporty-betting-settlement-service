package com.sporty.sporty_betting_settlement_service.service;

import com.sporty.sporty_betting_settlement_service.entity.EventOutcomeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventOutcomeProducer {

	

	    @Autowired
	    private KafkaTemplate<String, EventOutcomeEntity> kafkaTemplate; 

	    private static final String TOPIC = "event-outcomes";

	    public void send(EventOutcomeEntity eventOutcome) {
	        kafkaTemplate.send(TOPIC, eventOutcome.getEventId(), eventOutcome);
	    }
}