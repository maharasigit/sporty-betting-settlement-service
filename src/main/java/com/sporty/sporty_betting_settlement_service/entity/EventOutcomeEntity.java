package com.sporty.sporty_betting_settlement_service.entity;





import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "event_outcomes")
public class EventOutcomeEntity {

    @Id
    private String eventId;
    
   
    private String eventName;

   
    private String eventWinnerId;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventWinnerId() {
		return eventWinnerId;
	}

	public void setEventWinnerId(String eventWinnerId) {
		this.eventWinnerId = eventWinnerId;
	}
    
	@Override
	public String toString() {
	    return "EventOutcomeEntity{" +
	            "eventId='" + eventId + '\'' +
	            ", eventName='" + eventName + '\'' +
	            ", eventWinnerId='" + eventWinnerId + '\'' +
	            '}';
	}
    
}
