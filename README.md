Instructions to Run the Application
**1. Install Java 21 & Download Kafka**
Ensure Java 21 is installed on your machine. You can download it from:
Oracle JDK
Adoptium
Verify Java installation: java -version 21
Download Kafka (example version kafka_2.13-4.1.1) and extract it to a folder.
**2. Start Kafka Server First**
**a. Generate a UUID for Kafka storage (first-time only)**
On MacOS / Linux / Windows Git Bash:
uuidgen
Example output: f47ac10b-58cc-4372-a567-0e02b2c3d479
Use this UUID in the next step.
**b. Format Kafka storage (first-time only):**
bin/kafka-storage.sh format \
  -t <YOUR_UUID> \
  -c config/server.properties \
 --standalone
⚠️ Note: Replace <YOUR_UUID> with the UUID generated above.
**c. Start Kafka server:**
bin/kafka-server-start.sh config/server.properties
**d. Run Kafka consumer for event-outcomes topic:**
bin/kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic event-outcomes \
  --from-beginning

**3. Navigate to the JAR File Location**
Open Terminal (MacOS) or Command Prompt (Windows) and change directory to where your .jar file is located:
cd /path/to/downloaded/jar
**4. Run the Spring Boot Application**
java -jar sporty-betting-settlement-service-0.0.1-SNAPSHOT.jar
The application will start on port 8083.
**5. Access the Application**
Open your browser and navigate to:
http://localhost:8083/login
This will bring up the login page.
**6. Login Credentials**
Use the pre-created account:
Username: james.neilson@sporty.com
Password: ilikesporty@26
**7. Submit Event Outcomes**
Enter a unique Event ID, Event Name, and Event Winner ID in the UI.
Event outcomes will be sent to Kafka and settled bets updated via the mock RocketMQ consumer.




**Idempotency:** The EventOutcomeConsumer implements a "Check-then-Act" pattern using the BetStatus state machine. This prevents financial discrepancies in the event of Kafka message re-delivery.

**Atomicity:** By utilizing Spring's @Transactional boundary, the application guarantees that a bet is only marked as settled if the database persistence is successful, maintaining a consistent state between the event log and the system of record.
