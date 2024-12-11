package com.online.service.impl;

import com.online.domain.CustomerDetails;
import com.online.domain.TicketPool;
import com.online.domain.VendorDetail;
import com.online.repository.CustomerDao;
import com.online.repository.TicketPoolDao;
import com.online.repository.VendorDao;
import com.online.resource.TicketPoolResourse;
import com.online.service.TicketPoolService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Transactional
@Service
public class TicketPoolServiceImpl implements TicketPoolService {

    private final TicketPoolDao ticketPoolDao;
    private final ModelMapper modelMapper;
    private final VendorDao vendorDao;
    private final CustomerDao customerDao;

    @Autowired
    public TicketPoolServiceImpl(TicketPoolDao ticketPoolDao, VendorDao vendorDao, CustomerDao customerDao, ModelMapper modelMapper) {
        this.ticketPoolDao = ticketPoolDao;
        this.modelMapper = new ModelMapper();
        this.vendorDao = vendorDao;
        this.customerDao = customerDao;

    }


    @Override
    public TicketPoolResourse createEvent(TicketPoolResourse ticketPoolResourse) {
        TicketPool ticketPool = new TicketPool();

        // Validate and set event details
        ticketPool.setEventName(ticketPoolResourse.getEventName());
        ticketPool.setEventDescription(ticketPoolResourse.getEventDescription());
        ticketPool.setEventLocation(ticketPoolResourse.getEventLocation());
        ticketPool.setEventDate(ticketPoolResourse.getEventDate());
        ticketPool.setEventTime(ticketPoolResourse.getEventTime());
        ticketPool.setEventCategory(ticketPoolResourse.getEventCategory());
        ticketPool.setEventContact(ticketPoolResourse.getEventContact());

        // Validate and parse maxTicketCapacity
        try {
            ticketPool.setMaxTicketCapacity(Integer.parseInt(String.valueOf(ticketPoolResourse.getMaxTicketCapacity())));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid max ticket capacity format", e);
        }

        // Validate and parse totalTickets
        if (ticketPoolResourse.getVendor_get_tickets() == null) {
            throw new IllegalArgumentException("Total tickets cannot be null");
        }
        try {
            ticketPool.setAvailableTickets(Integer.parseInt(ticketPoolResourse.getVendor_get_tickets()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid total tickets format", e);
        }

        // Validate and parse ticketPrice
        try {
            ticketPool.setTicketPrice(Double.parseDouble(String.valueOf(ticketPoolResourse.getTicketPrice())));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ticket price format", e);
        }

        ticketPool.setStatusMessage("Scheduled");
        ticketPool.setCreatedBy("Admin"); // Include username
        ticketPool.setCreatedDate(LocalDateTime.now().toString());

        // Save ticket pool details
        TicketPool savedEvent = ticketPoolDao.save(ticketPool);

        // Return the mapped response
        return modelMapper.map(savedEvent, TicketPoolResourse.class);
    }

    @Override
    public TicketPoolResourse modifyEvent(TicketPoolResourse ticketPoolResourse, Long eventId) {
        // Fetch the existing event
        TicketPool ticketPool = ticketPoolDao.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event with ID " + eventId + " not found"));

        // Update event details
        ticketPool.setEventName(ticketPoolResourse.getEventName());
        ticketPool.setEventDescription(ticketPoolResourse.getEventDescription());
        ticketPool.setEventLocation(ticketPoolResourse.getEventLocation());
        ticketPool.setEventDate(ticketPoolResourse.getEventDate());
        ticketPool.setEventTime(ticketPoolResourse.getEventTime());
        ticketPool.setEventCategory(ticketPoolResourse.getEventCategory());
        ticketPool.setEventContact(ticketPoolResourse.getEventContact());
        ticketPool.setMaxTicketCapacity(Integer.parseInt(String.valueOf(ticketPoolResourse.getMaxTicketCapacity())));
        ticketPool.setTicketPrice(Double.parseDouble(String.valueOf(ticketPoolResourse.getTicketPrice())));
        ticketPool.setUpdatedBy("Admin");
        ticketPool.setUpdatedDate(LocalDateTime.now().toString());
        TicketPool modifyEvent = ticketPoolDao.save(ticketPool);

        // Return the updated event as a response
        return modelMapper.map(modifyEvent, TicketPoolResourse.class);
    }


    @Override
    public List<TicketPoolResourse> viewEvents() {
        List<TicketPool> events = ticketPoolDao.findAll();
        return events.stream()
                .map(event -> modelMapper.map(event, TicketPoolResourse.class))
                .collect(Collectors.toList());
    }


    @Override
    public boolean deleteEvent(Long id) {
        Optional<TicketPool> ticketPool = ticketPoolDao.findById(id);
        if (ticketPool.isPresent()) {
            ticketPoolDao.delete(ticketPool.get());
            return true;
        }
        return false;
    }

    @Override
    public TicketPoolResourse viewEvent(Long id) {
        Optional<TicketPool> ticketPool = ticketPoolDao.findById(id);
        return ticketPool.map(value -> modelMapper.map(value, TicketPoolResourse.class)).orElse(null);
    }

    @Override
    public TicketPoolResourse bookTickets(Long eventId, Long customerId, List<Integer> ticketNumbers) {
        // Fetch the customer and event
        CustomerDetails customer = customerDao.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + customerId + " not found"));
        TicketPool event = ticketPoolDao.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event with ID " + eventId + " not found"));

        // Check for already booked tickets
        List<Integer> alreadyBookedTickets = ticketNumbers.stream()
                .filter(event.getBookedTickets()::contains)
                .collect(Collectors.toList());

        if (!alreadyBookedTickets.isEmpty()) {
            throw new IllegalArgumentException("The following tickets are already booked: " + alreadyBookedTickets);
        }

        // Validate the ticket numbers
        if (ticketNumbers.size() > event.getAvailableTickets()) {
            throw new IllegalArgumentException("Not enough tickets available for booking");
        }

        // Update the event details
        event.setAvailableTickets(event.getAvailableTickets() - ticketNumbers.size());
        event.getBookedTickets().addAll(ticketNumbers);
        event.setUpdatedBy("Customer " + customerId);
        event.setUpdatedDate(LocalDateTime.now().toString());
        ticketPoolDao.save(event);

        // Update the customer details
        customer.setBookedTickets(customer.getBookedTickets() + ticketNumbers.size());
        customer.setUpdatedBy("Event " + eventId);
        customer.setUpdatedDate(LocalDateTime.now().toString());
        customerDao.save(customer);

        // Return the updated event details
        return modelMapper.map(event, TicketPoolResourse.class);
    }

    @Override
    public List<Integer> getBookedTickets(Long eventId) {
        TicketPool event = ticketPoolDao.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event with ID " + eventId + " not found"));
        return event.getBookedTickets();
    }

    @Override
    public TicketPoolResourse addVendorTickets(Long eventId, Long vendorId, int ticketsToAdd) {
        // Fetch the vendor and event
        VendorDetail vendor = vendorDao.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor with ID " + vendorId + " not found"));
        TicketPool event = ticketPoolDao.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event with ID " + eventId + " not found"));

        // Validate the number of tickets
        if (ticketsToAdd > event.getAvailableTickets()) {
            throw new IllegalArgumentException("Not enough tickets available for adding");
        }

        // Update the event details
        event.setAvailableTickets(event.getAvailableTickets() - ticketsToAdd);
        event.setUpdatedBy("Vendor " + vendorId);
        event.setUpdatedDate(LocalDateTime.now().toString());
        ticketPoolDao.save(event);

        // Update the vendor details with event information
        vendor.setTotalTickets(vendor.getTotalTickets() + ticketsToAdd);
        vendor.setUpdatedBy("Event " + eventId);
        vendor.setUpdatedDate(LocalDateTime.now().toString());
        vendor.setEventName(event.getEventName());
        vendor.setEventDescription(event.getEventDescription());
        vendor.setEventLocation(event.getEventLocation());
        vendor.setEventDate(event.getEventDate());
        vendor.setEventTime(event.getEventTime());
        vendor.setEventCategory(event.getEventCategory());
        vendor.setEventContact(event.getEventContact());
        vendor.setMaxTicketCapacity(event.getMaxTicketCapacity());
        vendor.setAvailableTickets(event.getAvailableTickets());
        vendorDao.save(vendor);

        // Return the updated event details
        return modelMapper.map(event, TicketPoolResourse.class);
    }

    @Override
    public List<TicketPoolResourse> getVendorTicketHistory(Long vendorId) {
        List<TicketPool> events = ticketPoolDao.findAll();
        return events.stream()
                .filter(event -> event.getVendors().equals(vendorId))
                .map(event -> modelMapper.map(event, TicketPoolResourse.class))
                .collect(Collectors.toList());
    }
}
