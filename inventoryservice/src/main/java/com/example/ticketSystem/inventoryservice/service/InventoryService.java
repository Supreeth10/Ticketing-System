package com.example.ticketSystem.inventoryservice.service;

import com.example.ticketSystem.inventoryservice.entity.Event;
import com.example.ticketSystem.inventoryservice.entity.Venue;
import com.example.ticketSystem.inventoryservice.repository.EventRepository;
import com.example.ticketSystem.inventoryservice.repository.VenueRepository;
import com.example.ticketSystem.inventoryservice.response.EventInventoryResponse;
import com.example.ticketSystem.inventoryservice.response.VenueInventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    @Autowired
    public InventoryService(final EventRepository eventRepository, final VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public List<EventInventoryResponse> getAllEvents() {
        final List<Event> events = eventRepository.findAll();

        return events.stream().map(event -> EventInventoryResponse.builder()
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .build()).collect(Collectors.toList());
    }

    public VenueInventoryResponse getVenueInformation(final Long venueId) {
        final Venue venue = venueRepository.findById(venueId).orElse(null);

        return VenueInventoryResponse.builder()
                .venueId(venue.getId())
                .venueName(venue.getName())
                .totalCapacity(venue.getTotalCapacity())
                .build();
    }

    public EventRepository getEventRepository() {
        return this.eventRepository;
    }

    public VenueRepository getVenueRepository() {
        return this.venueRepository;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof InventoryService)) return false;
        final InventoryService other = (InventoryService) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$eventRepository = this.getEventRepository();
        final Object other$eventRepository = other.getEventRepository();
        if (this$eventRepository == null ? other$eventRepository != null : !this$eventRepository.equals(other$eventRepository))
            return false;
        final Object this$venueRepository = this.getVenueRepository();
        final Object other$venueRepository = other.getVenueRepository();
        if (this$venueRepository == null ? other$venueRepository != null : !this$venueRepository.equals(other$venueRepository))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof InventoryService;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $eventRepository = this.getEventRepository();
        result = result * PRIME + ($eventRepository == null ? 43 : $eventRepository.hashCode());
        final Object $venueRepository = this.getVenueRepository();
        result = result * PRIME + ($venueRepository == null ? 43 : $venueRepository.hashCode());
        return result;
    }

    public String toString() {
        return "InventoryService(eventRepository=" + this.getEventRepository() + ", venueRepository=" + this.getVenueRepository() + ")";
    }
}
