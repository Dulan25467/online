package com.online.service.impl;

import com.online.domain.CustomerDetails;
import com.online.domain.StakeholderDetails;
import com.online.domain.VendorDetail;
import com.online.repository.CustomerDao;
import com.online.repository.StakeholderDao;
import com.online.repository.VendorDao;
import com.online.resource.StakeholderResourse;
import com.online.service.StakeholderService;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Data
@Transactional
@Service
public class StakeholderServiceImpl implements StakeholderService {
    private final StakeholderDao stakeholderDao;
    private final VendorDao vendorDao;
    private final CustomerDao customerDao;
    private final ModelMapper modelMapper;

    @Autowired
    public StakeholderServiceImpl(StakeholderDao stakeholderDao,VendorDao vendorDao,CustomerDao customerDao, ModelMapper modelMapper) {
        this.stakeholderDao = stakeholderDao;
        this.vendorDao = vendorDao;
        this.customerDao = customerDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public StakeholderResourse login(String username, String password) {
        StakeholderDetails stakeholder = stakeholderDao.findByUsername(username);
        if (stakeholder != null) {
            if (password.equals(stakeholder.getPassword())) {
                return modelMapper.map(stakeholder, StakeholderResourse.class);
            } else {
                throw new RuntimeException("Invalid password.");
            }
        }
        throw new RuntimeException("User not found.");
    }


    @Override
    public StakeholderResourse register(StakeholderResourse stakeholderResourse) {
        // Map StakeholderResourse to StakeholderDetails entity
        StakeholderDetails stakeholder = modelMapper.map(stakeholderResourse, StakeholderDetails.class);

        // Populate additional fields
        stakeholder.setCreatedDate(LocalDateTime.now());
        stakeholder.setCreatedBy("system");

        // Save the StakeholderDetails entity
        StakeholderDetails savedStakeholder = stakeholderDao.save(stakeholder);

        // Add data to VendorDetail or CustomerDetails based on stakeholderType
        if ("Vendor".equalsIgnoreCase(stakeholderResourse.getStakeholderType())) {
            VendorDetail vendor = new VendorDetail();
            vendor.setId(savedStakeholder.getId());
            vendor.setName(savedStakeholder.getUsername());
            vendor.setEmail(savedStakeholder.getEmail());
            vendor.setAddress(savedStakeholder.getAddress());
            vendor.setPhone(savedStakeholder.getPhone());
            vendor.setCreatedBy(savedStakeholder.getCreatedBy());
            vendor.setCreatedDate(savedStakeholder.getCreatedDate().toString());
            VendorDetail savedVendor = vendorDao.save(vendor); // Save to VendorDetail

            stakeholderResourse.setVendorId((int) savedVendor.getId()); // Return Vendor ID
        } else if ("Customer".equalsIgnoreCase(stakeholderResourse.getStakeholderType())) {
            CustomerDetails customer = new CustomerDetails();
            customer.setId(savedStakeholder.getId());
            customer.setName(savedStakeholder.getUsername());
            customer.setEmail(savedStakeholder.getEmail());
            customer.setAddress(savedStakeholder.getAddress());
            customer.setPhone(savedStakeholder.getPhone());
            customer.setCreatedBy(savedStakeholder.getCreatedBy());
            customer.setCreatedDate(savedStakeholder.getCreatedDate().toString());
            CustomerDetails savedCustomer = customerDao.save(customer); // Save to CustomerDetails

            stakeholderResourse.setCustomerId((int) savedCustomer.getId()); // Return Customer ID
        }

        // Map back to StakeholderResourse and return
        return modelMapper.map(savedStakeholder, StakeholderResourse.class);
    }


//    @Override
//    public StakeholderResourse register(StakeholderResourse stakeholderResourse) {
//        // Map StakeholderResourse to StakeholderDetails entity
//        StakeholderDetails stakeholder = modelMapper.map(stakeholderResourse, StakeholderDetails.class);
//
//        // Populate additional fields
//        stakeholder.setCreatedDate(LocalDateTime.now());
//        stakeholder.setCreatedBy("system"); // Replace "system" with actual logged-in user if applicable
//
//        // Save the StakeholderDetails entity
//        StakeholderDetails savedStakeholder = stakeholderDao.save(stakeholder);
//
//        // Add data to VendorDetail or CustomerDetails based on stakeholderType
//        if ("Vendor".equalsIgnoreCase(stakeholderResourse.getStakeholderType())) {
//            VendorDetail vendor = new VendorDetail();
//            vendor.setId(savedStakeholder.getId());
//            vendor.setName(savedStakeholder.getUsername());
//            vendor.setEmail(savedStakeholder.getEmail());
//            vendor.setAddress(savedStakeholder.getAddress());
//            vendor.setPhone(savedStakeholder.getPhone());
//            vendor.setCreatedBy(savedStakeholder.getCreatedBy());
//            vendor.setCreatedDate(savedStakeholder.getCreatedDate().toString());
//            vendorDao.save(vendor); // Save to the VendorDetail table
//        } else if ("Customer".equalsIgnoreCase(stakeholderResourse.getStakeholderType())) {
//            CustomerDetails customer = new CustomerDetails();
//            customer.setId(savedStakeholder.getId());
//            customer.setName(savedStakeholder.getUsername());
//            customer.setEmail(savedStakeholder.getEmail());
//            customer.setAddress(savedStakeholder.getAddress());
//            customer.setPhone(savedStakeholder.getPhone());
//            customer.setCreatedBy(savedStakeholder.getCreatedBy());
//            customer.setCreatedDate(savedStakeholder.getCreatedDate().toString());
//            customerDao.save(customer); // Save to the CustomerDetails table
//        }
//
//        // Map back to StakeholderResourse and return
//        return modelMapper.map(savedStakeholder, StakeholderResourse.class);
//    }



    @Override
    public StakeholderResourse delete(int id) {
        StakeholderDetails stakeholder = stakeholderDao.findById(id).orElseThrow(() -> new RuntimeException("Stakeholder not found."));
        stakeholderDao.delete(stakeholder);
        return modelMapper.map(stakeholder, StakeholderResourse.class);
    }

    @Override
    public StakeholderResourse update(StakeholderResourse stakeholderResourse) {
        StakeholderDetails stakeholder = stakeholderDao.findById(stakeholderResourse.getId())
                .orElseThrow(() -> new RuntimeException("Stakeholder not found."));

        stakeholder.setUsername(stakeholderResourse.getUsername());
        stakeholder.setEmail(stakeholderResourse.getEmail());
        stakeholder.setAddress(stakeholderResourse.getAddress());
        stakeholder.setPhone(stakeholderResourse.getPhone());
        stakeholder.setUpdatedBy("System");
        stakeholder.setUpdatedDate(LocalDateTime.parse(LocalDateTime.now().toString()));

        stakeholderDao.save(stakeholder);
        return modelMapper.map(stakeholder, StakeholderResourse.class);
    }
}
