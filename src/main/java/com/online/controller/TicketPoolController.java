package com.online.controller;


import com.online.domain.TicketPool;
import com.online.exeption.ApiResponse;
import com.online.exeption.CommonExeption;
import com.online.resource.TicketPoolResourse;
import com.online.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/ticketingSystem")
public class TicketPoolController {

    private final TicketPoolService ticketPoolService;

    @Autowired
    public TicketPoolController(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }


    @PostMapping("/createEvent")
    public ResponseEntity<ApiResponse> createEvent(
            @RequestBody TicketPoolResourse ticketPoolResourse
            ) { // Accept username
        CommonExeption exception = new CommonExeption();

        // Validate ticket pool details
        if (ticketPoolResourse.getEventName() == null || ticketPoolResourse.getEventName().isEmpty()) {
            exception.addError("eventName", "Event name is required");
        }
        if (ticketPoolResourse.getEventLocation() == null || ticketPoolResourse.getEventLocation().isEmpty()) {
            exception.addError("eventLocation", "Event location is required");
        }
        if (!exception.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid ticket pool details", false, exception.getErrors()));
        }

        // Save the ticket pool details
        TicketPoolResourse savedTicketPool = ticketPoolService.createEvent(ticketPoolResourse);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Event added successfully", true, savedTicketPool));
    }

    @PutMapping("/modifyEvent/{id}")
    public ResponseEntity<ApiResponse> modifyEvent(
            @RequestBody TicketPoolResourse ticketPoolResourse,
            @PathVariable Long id) {

        CommonExeption exception = new CommonExeption();

        // Validate the ticket pool details
        if (ticketPoolResourse.getEventName() == null || ticketPoolResourse.getEventName().isEmpty()) {
            exception.addError("eventName", "Event name is required");
        }
        if (ticketPoolResourse.getEventLocation() == null || ticketPoolResourse.getEventLocation().isEmpty()) {
            exception.addError("eventLocation", "Event location is required");
        }
        if (ticketPoolResourse.getEventDate() == null || ticketPoolResourse.getEventDate().isEmpty()) {
            exception.addError("eventDate", "Event date is required");
        }
        if (ticketPoolResourse.getEventTime() == null || ticketPoolResourse.getEventTime().isEmpty()) {
            exception.addError("eventTime", "Event time is required");
        }
        if (!exception.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid ticket pool details", false, exception.getErrors()));
        }

        // Call the service method to update the event
        try {
            TicketPoolResourse savedTicketPool = ticketPoolService.modifyEvent(ticketPoolResourse, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Event updated successfully", true, savedTicketPool));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(ex.getMessage(), false, null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An unexpected error occurred while updating the event", false, null));
        }
    }






    @GetMapping("/viewEvents")
    public ResponseEntity<ApiResponse> viewEvents() {
        try {
            // Fetch all events
            List<TicketPoolResourse> events = ticketPoolService.viewEvents();
            return ResponseEntity.ok(new ApiResponse("Events fetched successfully", true, events));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to fetch events", false, null));
        }
    }



    @DeleteMapping("/deleteEvent/{id}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable Long id) {

        boolean isDeleted = ticketPoolService.deleteEvent(id);

        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse("Event deleted successfully", true, null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Event not found", false, null));
        }
    }

    @GetMapping("/viewEvent/{id}")
    public ResponseEntity<ApiResponse> viewEvent(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid event ID", false, null));
        }

        TicketPoolResourse event = ticketPoolService.viewEvent(id);
        if (event != null) {
            return ResponseEntity.ok(new ApiResponse("Event fetched successfully", true, event));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Event not found", false, null));
        }
    }

    @PostMapping("/bookTickets/{eventId}/{customerId}")
    public ResponseEntity<ApiResponse> bookTickets(
            @PathVariable Long eventId,
            @PathVariable Long customerId,
            @RequestBody Map<String, List<Integer>> request) {

        List<Integer> ticketNumbers = request.get("ticketNumbers");

        try {
            ticketPoolService.bookTickets(eventId, customerId, ticketNumbers);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Tickets booked successfully", true, null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(ex.getMessage(), false, null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An unexpected error occurred while booking tickets", false, null));
        }
    }

    @GetMapping("/bookedTickets/{eventId}")
    public ResponseEntity<ApiResponse> getBookedTickets(@PathVariable Long eventId) {
        try {
            List<Integer> bookedTickets = ticketPoolService.getBookedTickets(eventId);
            return ResponseEntity.ok(new ApiResponse("Booked tickets fetched successfully", true, bookedTickets));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to fetch booked tickets", false, null));
        }
    }

    @PostMapping("/addVendorTickets/{eventId}/{vendorId}")
    public ResponseEntity<ApiResponse> addVendorTickets(
            @PathVariable Long eventId,
            @PathVariable Long vendorId,
            @RequestBody Map<String, Integer> request) {

        int ticketsToAdd = request.get("ticketsToAdd");

        // Validate the number of tickets
        if (ticketsToAdd <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Please enter a valid number of tickets.", false, null));
        }

        try {
            TicketPoolResourse updatedEvent = ticketPoolService.addVendorTickets(eventId, vendorId, ticketsToAdd);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Tickets added successfully", true, updatedEvent));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(ex.getMessage(), false, null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An unexpected error occurred while adding tickets", false, null));
        }
    }

    @GetMapping("/vendorTicketHistory/{vendorId}")
    public ResponseEntity<ApiResponse> getVendorTicketHistory(@PathVariable Long vendorId) {
        try {
            List<TicketPoolResourse> ticketHistory = ticketPoolService.getVendorTicketHistory(vendorId);
            return ResponseEntity.ok(new ApiResponse("Vendor ticket history fetched successfully", true, ticketHistory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to fetch vendor ticket history", false, null));
        }
    }


}