package com.example.ticketSystem.bookingservice.service;

import com.example.ticketSystem.bookingservice.request.BookingRequest;
import com.example.ticketSystem.bookingservice.response.BookingResponse;
import org.springframework.stereotype.Service;

@Service
public class BookingService {


    public BookingResponse createBooking(final BookingRequest request) {
        // check if user exists
        // check if there is enough inventory
        // get events info to also get venue info
        // create booking
        // send booking to order service on a kafka topic

        return BookingResponse.builder().build();
    }
}
