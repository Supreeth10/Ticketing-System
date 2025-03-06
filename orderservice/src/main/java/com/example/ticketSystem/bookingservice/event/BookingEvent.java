package com.example.ticketSystem.bookingservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
//this class needs to have the same path as the package thats creating this topic
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEvent {

    private Long userId;
    private Long eventId;
    private Long ticketCount;
    private BigDecimal totalPrice;
}
