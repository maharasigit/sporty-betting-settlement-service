package com.sporty.sporty_betting_settlement_service.service;

import com.sporty.sporty_betting_settlement_service.entity.BetEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BetSettlementConsumer {
	private static final Logger log =
	            LoggerFactory.getLogger(BetSettlementConsumer.class);
	public void consumeSettlement(BetEntity bet) {
		 log.info("RocketMQ Consumer received bet settlement: {}", bet);
	     log.info("Bet settled successfully for betId={}", bet.getBetId());
    }
}
