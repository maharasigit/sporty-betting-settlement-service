package com.sporty.sporty_betting_settlement_service.config;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sporty.sporty_betting_settlement_service.entity.AppUser;
import com.sporty.sporty_betting_settlement_service.entity.BetEntity;
import com.sporty.sporty_betting_settlement_service.repositories.BetRepository;
import com.sporty.sporty_betting_settlement_service.repositories.UserRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BetRepository betRepository;

    @Override
    public void run(String... args) throws Exception {
        // --- Initialize admin ---
        String adminEmail = "james.neilson@sporty.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            AppUser admin = new AppUser();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("ilikesporty@26")); // default password
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
            System.out.println("Admin user created: " + adminEmail);
        }

        // --- Initialize bets ---
        if (betRepository.count() == 0) { // avoid duplicates on restart
            betRepository.saveAll(List.of(
                new BetEntity("Betid_1", "user1", "Event_1", "Event1_Market1", null, 100.0),
                new BetEntity("Betid_2", "user2", "Event_2", "Event2_Market2", null, 150.0),
                new BetEntity("Betid_3", "user3", "Event_3", "Event3_Market3", null, 200.0)
            ));
            System.out.println("Initial bets added to H2 database");
        }
        String host = "192.168.1.34";  // your NameServer or broker IP
        InetAddress inet=null;
		try {
			inet = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        boolean reachable=false;
		try {
			reachable = inet.isReachable(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // timeout in ms

        System.out.println(host + " reachable? " + reachable);
    }
}
