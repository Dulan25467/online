package com.online.service.impl;

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
    private final CustomerDao  customerDao;

    @Autowired
    public TicketPoolServiceImpl(TicketPoolDao ticketPoolDao , VendorDao vendorDao, CustomerDao customerDao, ModelMapper modelMapper) {
        this.ticketPoolDao = ticketPoolDao;
        this.modelMapper = new ModelMapper();
        this.vendorDao = vendorDao;
        this.customerDao = customerDao;

    }

    @Override
    public TicketPoolResourse addEvent(TicketPoolResourse ticketPoolResourse, Long vendorId, String username) {
        // Fetch the vendor using vendorId
        Optional<VendorDetail> optionalVendor = vendorDao.findById(vendorId);
        if (optionalVendor.isEmpty()) {
            throw new IllegalArgumentException("Vendor with ID " + vendorId + " not found");
        }
        VendorDetail vendor = optionalVendor.get();

        // Map TicketPoolResource to TicketPool
        TicketPool ticketPool = new TicketPool();
        ticketPool.setEventName(ticketPoolResourse.getEventName());
        ticketPool.setEventDescription(ticketPoolResourse.getEventDescription());
        ticketPool.setEventLocation(ticketPoolResourse.getEventLocation());
        ticketPool.setEventDate(ticketPoolResourse.getEventDate());
        ticketPool.setEventTime(ticketPoolResourse.getEventTime());
        ticketPool.setEventCategory(ticketPoolResourse.getEventCategory());
        ticketPool.setEventContact(ticketPoolResourse.getEventContact());
        ticketPool.setTotalTickets(Integer.parseInt(ticketPoolResourse.getTotalTickets()));
        ticketPool.setAvailableTickets(Integer.parseInt(ticketPoolResourse.getTotalTickets()));
        ticketPool.setStatusMessage("Scheduled");
        ticketPool.setCreatedBy("Vendor " + vendorId + " - " + username); // Include username
        ticketPool.setCreatedDate(LocalDateTime.now().toString());

        // Save ticket pool details
        TicketPool savedEvent = ticketPoolDao.save(ticketPool);

        // Update vendor with event details
        vendor.setEventname(ticketPoolResourse.getEventName());
        vendor.setEventDescription(ticketPoolResourse.getEventDescription());
        vendor.setEventLocation(ticketPoolResourse.getEventLocation());
        vendor.setEventDate(ticketPoolResourse.getEventDate());
        vendor.setEventTime(ticketPoolResourse.getEventTime());
        vendor.setEventCategory(ticketPoolResourse.getEventCategory());
        vendor.setEventContact(ticketPoolResourse.getEventContact());
        vendor.setTotalTickets(Integer.parseInt(ticketPoolResourse.getTotalTickets()));
        vendor.setAvailableTickets(Integer.parseInt(ticketPoolResourse.getTotalTickets()));
        vendor.setUpdatedBy(username); // Set username as the updater
        vendor.setUpdatedDate(LocalDateTime.now().toString());

        // Save updated vendor details
        vendorDao.save(vendor);

        // Return the mapped response
        return modelMapper.map(savedEvent, TicketPoolResourse.class);
    }


    @Override
    public List<TicketPoolResourse> viewEvents() {
        List<TicketPool> events = ticketPoolDao.findAll();
        return events.stream()
                .map(event -> modelMapper.map(event, TicketPoolResourse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TicketPoolResourse updateEvent(TicketPoolResourse ticketPoolResourse, Long vendorId, String username) {
        // Fetch the existing event
        TicketPool ticketPool = ticketPoolDao.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Event with ID " + vendorId + " not found"));

        // Update event details
        ticketPool.setEventName(ticketPoolResourse.getEventName());
        ticketPool.setEventDescription(ticketPoolResourse.getEventDescription());
        ticketPool.setEventLocation(ticketPoolResourse.getEventLocation());
        ticketPool.setEventDate(ticketPoolResourse.getEventDate());
        ticketPool.setEventTime(ticketPoolResourse.getEventTime());
        ticketPool.setEventCategory(ticketPoolResourse.getEventCategory());
        ticketPool.setEventContact(ticketPoolResourse.getEventContact());
        ticketPool.setTotalTickets(Integer.parseInt(ticketPoolResourse.getTotalTickets()));
        ticketPool.setAvailableTickets(Integer.parseInt(ticketPoolResourse.getTotalTickets())); // Assuming all tickets are available after update
        ticketPool.setUpdatedBy(username);
        ticketPool.setUpdatedDate(LocalDateTime.now().toString());
        TicketPool updatedEvent = ticketPoolDao.save(ticketPool);

        // Fetch and update the vendor
        VendorDetail vendor = vendorDao.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor with ID " + vendorId + " not found"));

        vendor.setEventname(ticketPoolResourse.getEventName());
        vendor.setEventDescription(ticketPoolResourse.getEventDescription());
        vendor.setEventLocation(ticketPoolResourse.getEventLocation());
        vendor.setEventDate(ticketPoolResourse.getEventDate());
        vendor.setEventTime(ticketPoolResourse.getEventTime());
        vendor.setEventCategory(ticketPoolResourse.getEventCategory());
        vendor.setEventContact(ticketPoolResourse.getEventContact());
        vendor.setTotalTickets(Integer.parseInt(ticketPoolResourse.getTotalTickets()));
        vendor.setAvailableTickets(Integer.parseInt(ticketPoolResourse.getTotalTickets()));
        vendor.setUpdatedBy(username);
        vendor.setUpdatedDate(LocalDateTime.now().toString());
        vendorDao.save(vendor);

        // Return the updated event as a response
        return modelMapper.map(updatedEvent, TicketPoolResourse.class);
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

}
