package com.sporty.sporty_betting_settlement_service.service;



import com.sporty.sporty_betting_settlement_service.entity.BetEntity;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetSettlementProducer {

    private static final Logger log =
            LoggerFactory.getLogger(BetSettlementProducer.class);
    
    //@Autowired
   // private RocketMQTemplate rocketMQTemplate;

    // Topic name in RocketMQ
    //private static final String TOPIC = "bet-settlement-topic";
    
  private final BetSettlementConsumer consumer;

    @Autowired
    public BetSettlementProducer(BetSettlementConsumer consumer) {
        this.consumer = consumer;
    }

    // Mock RocketMQ producer
    public void send(BetEntity bet) {
        log.info("Sending bet settlement to RocketMQ (mock): {}", bet);
       
    //    rocketMQTemplate.convertAndSend(TOPIC, bet);
    
        consumer.consumeSettlement(bet); //Calling Mock RocketMQ Consumer Explicitly
    }
}
