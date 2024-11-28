package com.online.service.impl;

import com.online.domain.StakeholderDetails;
import com.online.repository.StakeholderDao;
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
    private final ModelMapper modelMapper;

    @Autowired
    public StakeholderServiceImpl(StakeholderDao stakeholderDao, ModelMapper modelMapper) {
        this.stakeholderDao = stakeholderDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public StakeholderResourse login(String username, String password) {
        StakeholderDetails stakeholder = stakeholderDao.findByUsername(username);
        if (stakeholder != null) {
            // Password check can be enhanced with encryption
            if (password.equals(stakeholder.getDeletedBy())) { // Assuming password is stored as `deletedBy` temporarily
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
        stakeholder.setCreatedDate(LocalDateTime.parse(LocalDateTime.now().toString()));
        stakeholder.setCreatedBy("system"); // Replace "system" with actual logged-in user if applicable

        // Save the entity
        stakeholderDao.save(stakeholder);

        // Map back to StakeholderResourse and return
        return modelMapper.map(stakeholder, StakeholderResourse.class);
    }


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
