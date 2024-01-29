package medequipsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import medequipsystem.rabbitmqcoordinates.SignalSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "http://localhost:4200/")
@Controller
public class WebSocketController {

    @Autowired
    private SignalSenderService signalSenderService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private double latitude = 45.2396;
    private double longitude = 19.8227;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // FOR CONTINUOUS MOVEMENT - every 5 seconds
    @MessageMapping("/send/message")
    public void broadcastNotification() {
        scheduler.scheduleAtFixedRate(this::sendPeriodicLocationUpdate, 0, 5, TimeUnit.SECONDS);
    }


    private void sendPeriodicLocationUpdate() {
        String newLocation = generateNewLocation();
        Map<String, String> messageConverted = parseMessage(newLocation);
        simpMessagingTemplate.convertAndSend("/socket-publisher", messageConverted);
    }

    private String generateNewLocation() {
        double increment = 0.001;
        latitude += increment;
        longitude += increment;
        return "{\"latitude\":" + latitude  + ",\"longitude\":" + longitude + "}";
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    ///////// FOR ONE MOVEMENT
    @MessageMapping("/send/message")
    public void broadcastNotification(String message) {
        String location = "{\"id\":1,\"latitude\":45.2396,\"longitude\":19.85}";
        Map<String, String> messageConverted = parseMessage(location);
        this.simpMessagingTemplate.convertAndSend("/socket-publisher", messageConverted);
    }
   */

    @SuppressWarnings("unchecked")
    private Map<String, String> parseMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> retVal;

        try {
            retVal = mapper.readValue(message, Map.class); // parsiranje JSON stringa
        } catch (IOException e) {
            retVal = null;
        }

        return retVal;
    }



}
