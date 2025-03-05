package com.example.ticketSystem.inventoryservice.response;

import com.example.ticketSystem.inventoryservice.entity.Venue;
import lombok.*;
//import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EventInventoryResponse {
    private Long eventId;
    private String event;
    private Long capacity;
    private Venue venue;
    private BigDecimal ticketPrice;

    // Custom constructor for easier object creation
    public EventInventoryResponse(String event, Long capacity, Venue venue) {
        this.event = event;
        this.capacity = capacity;
        this.venue = venue;
    }
}
