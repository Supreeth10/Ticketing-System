package com.example.ticketSystem.inventoryservice.controller;

import com.example.ticketSystem.inventoryservice.response.EventInventoryResponse;
import com.example.ticketSystem.inventoryservice.response.VenueInventoryResponse;
import com.example.ticketSystem.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {
    private InventoryService inventoryService;

    @Autowired
    public InventoryController(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory/events")
    public @ResponseBody List<EventInventoryResponse> inventoryGetAllEvents() {
        return inventoryService.getAllEvents();
    }

    @GetMapping("/inventory/venue/{venueId}")
    public @ResponseBody VenueInventoryResponse inventoryByVenueId(@PathVariable("venueId") Long venueId) {
        return inventoryService.getVenueInformation(venueId);
    }

    @GetMapping("inventory/event/{eventId}")
    public @ResponseBody EventInventoryResponse inventoryByEventId(@PathVariable("eventId") Long eventId) {
        return inventoryService.getEventInventory(eventId);
    }
}
