package com.sporty.sporty_betting_settlement_service.controller;

import com.sporty.sporty_betting_settlement_service.entity.BetEntity;
import com.sporty.sporty_betting_settlement_service.entity.EventOutcomeEntity;
import com.sporty.sporty_betting_settlement_service.repositories.BetRepository;
import com.sporty.sporty_betting_settlement_service.repositories.EventOutcomeRepository;
import com.sporty.sporty_betting_settlement_service.service.EventOutcomeConsumer;
import com.sporty.sporty_betting_settlement_service.service.EventOutcomeProducer;
import com.sporty.sporty_betting_settlement_service.model.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EventOutcomeController {
	
	private static final Logger log =
            LoggerFactory.getLogger(EventOutcomeController.class);
	
	@Autowired
	public EventOutcomeRepository eventOutcomeRepository;
	
	@Autowired
	public EventOutcomeProducer kafkaProducer;
	@Autowired
	public EventOutcomeConsumer eventOutcomeConsumer;
	
	@Autowired
	public BetRepository betRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/event-outcome";
    }

    @GetMapping("/event-outcome")
    public String showEventOutcomeForm(Model model) {
    	 model.addAttribute("eventOutcome", new EventOutcomeEntity());
         model.addAttribute("allEventOutcomes", eventOutcomeRepository.findAll());
         model.addAttribute("settledBets", eventOutcomeConsumer.getBets()); // display settled bets
         // Log the settled bets
         log.info("Displaying Event Outcome Form. Current settled bets: {}", eventOutcomeConsumer.getBets());
         
         return "event-outcome-form";
    }

    @PostMapping("/event-outcome")
    public String submitEventOutcome(
            @ModelAttribute EventOutcome eventOutcome,
            Model model
    ) {
        //Map to entity and persist
        EventOutcomeEntity entity = new EventOutcomeEntity();
        entity.setEventId(eventOutcome.getEventId());
        entity.setEventName(eventOutcome.getEventName());
        entity.setEventWinnerId(eventOutcome.getEventWinnerId());

        log.info("Submitting Event Outcome: {}", entity);
        eventOutcomeRepository.save(entity);

        // Publish to Kafka for async settlement
        kafkaProducer.send(entity);
        log.info("Published Event Outcome to Kafka: {}", entity);

        // Prepare bets for Thymeleaf UI
        List<BetEntity> displayBets = betRepository.findByEventId(eventOutcome.getEventId());

        // Update winner ID for display only (do NOT persist again)
        displayBets.forEach(bet -> bet.setEventWinnerId(eventOutcome.getEventWinnerId()));

        // Add attributes for UI rendering
        model.addAttribute("submitted", true);
        model.addAttribute("eventOutcome", new EventOutcomeEntity());
        model.addAttribute("allEventOutcomes", eventOutcomeRepository.findAll());
        model.addAttribute("settledBets", displayBets);

        return "event-outcome-form";
    }

}
