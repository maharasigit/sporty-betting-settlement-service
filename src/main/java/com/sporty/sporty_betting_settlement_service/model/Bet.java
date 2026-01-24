package com.sporty.sporty_betting_settlement_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;


public class Bet {
    private String betId;
    private String userId;
    private String eventId;
    private String eventMarketId;
    private String eventWinnerId; // null until settled
    private double betAmount;
    
	public Bet(String betId, String userId, String eventId, String eventMarketId, String eventWinnerId,
			double betAmount) {
		super();
		this.betId = betId;
		this.userId = userId;
		this.eventId = eventId;
		this.eventMarketId = eventMarketId;
		this.eventWinnerId = eventWinnerId;
		this.betAmount = betAmount;
	}
	public String getBetId() {
		return betId;
	}
	public void setBetId(String betId) {
		this.betId = betId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventMarketId() {
		return eventMarketId;
	}
	public void setEventMarketId(String eventMarketId) {
		this.eventMarketId = eventMarketId;
	}
	public String getEventWinnerId() {
		return eventWinnerId;
	}
	public void setEventWinnerId(String eventWinnerId) {
		this.eventWinnerId = eventWinnerId;
	}
	public double getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(double betAmount) {
		this.betAmount = betAmount;
	}
	@Override
	public String toString() {
	    return "Bet{" +
	            "betId='" + betId + '\'' +
	            ", userId='" + userId + '\'' +
	            ", eventId='" + eventId + '\'' +
	            ", eventMarketId='" + eventMarketId + '\'' +
	            ", eventWinnerId='" + eventWinnerId + '\'' +
	            ", betAmount=" + betAmount +
	            '}';
	}

    
}
