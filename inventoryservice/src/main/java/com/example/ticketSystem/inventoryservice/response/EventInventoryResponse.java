package com.example.ticketSystem.inventoryservice.response;

import com.example.ticketSystem.inventoryservice.entity.Venue;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventInventoryResponse {
    private Long eventId;
    private String event;
    private Long capacity;
    private Venue venue;
//    private BigDecimal ticketPrice;
}
