package com.pickpaysimplified.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pickpaysimplified.domain.user.User;
import com.pickpaysimplified.dtos.NotificationDTO;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();

        NotificationDTO notification = new NotificationDTO(email, message);

        ResponseEntity<String> response = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify",
                notification,
                String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Failed to send notification: " + response.getStatusCode());
        }
    }
}
