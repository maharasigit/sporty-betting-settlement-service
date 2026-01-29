package com.sporty.sporty_betting_settlement_service.entity;

public enum BetStatus {
    OPEN,      // Bet is active and waiting for game result
    SETTLED    // Bet has been processed and paid out/closed
}