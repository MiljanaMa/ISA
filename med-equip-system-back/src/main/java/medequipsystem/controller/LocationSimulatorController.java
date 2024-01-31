package medequipsystem.controller;

import medequipsystem.rabbitmqcoordinates.SignalSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = "http://localhost:4200/")
@Controller
public class LocationSimulatorController {

    @Autowired
    private SignalSenderService signalSenderService;
    
   @MessageMapping("/send/message")
    public ResponseEntity<String> startSendingCoordinates() {
        signalSenderService.startSendingLocations();
        return ResponseEntity.ok("Signal sent to start sending coordinates.");
    }

}
