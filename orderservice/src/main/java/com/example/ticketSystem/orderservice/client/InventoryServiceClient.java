package com.example.ticketSystem.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryServiceClient {

    @Value("${inventory.service.url}")
    private String InventoryServiceUrl;

    public ResponseEntity<Void> updateInventory(final Long eventId,
                                                final Long ticketCount){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(InventoryServiceUrl +"/event/" +eventId+"/capacity/"+ticketCount,null);
        return ResponseEntity.ok().build();
    }
}
