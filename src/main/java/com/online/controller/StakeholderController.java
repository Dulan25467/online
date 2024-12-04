package com.online.controller;

import com.online.domain.StakeholderDetails;
import com.online.exeption.ApiResponse;
import com.online.exeption.CommonExeption;
import com.online.repository.StakeholderDao;
import com.online.resource.StakeholderResourse;
import com.online.service.StakeholderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/stakeholder")
public class StakeholderController {
    private final StakeholderService stakeholderService;
    private final ModelMapper modelMapper;
    private final StakeholderDao stakeholderDao;

    @Autowired
    public StakeholderController(StakeholderService stakeholderService, ModelMapper modelMapper, StakeholderDao stakeholderDao) {
        this.stakeholderService = stakeholderService;
        this.modelMapper = modelMapper;
        this.stakeholderDao = stakeholderDao;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody StakeholderResourse loginRequest) {
        try {
            // Fetch stakeholder details based on the username
            StakeholderDetails stakeholderDetails = stakeholderDao.findByUsername(loginRequest.getUsername());

            // Check if the stakeholder exists
            if (stakeholderDetails == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse("Username not found", false));
            }

            // Validate the password
            if (!stakeholderDetails.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse("Incorrect password", false));
            }

            // Check stakeholder type
            String stakeholderType = stakeholderDetails.getStakeholderType();
            if (!"Vendor".equals(stakeholderType) && !"Customer".equals(stakeholderType)) {
                throw new RuntimeException("Invalid stakeholder type.");
            }

            // If valid, return success with stakeholder type
            return ResponseEntity.ok(new ApiResponse("Login successful", true, stakeholderType));

        } catch (RuntimeException ex) {
            // Handle unexpected runtime exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An error occurred: " + ex.getMessage(), false));
        }
    }







    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody StakeholderResourse stakeholderResourse) {
        CommonExeption exception = new CommonExeption();

        // Validate username
        Optional<StakeholderDetails> existingUser = Optional.ofNullable(stakeholderDao.findByUsername(stakeholderResourse.getUsername()));
        if (existingUser.isPresent()) {
            exception.addError("username", "Username already exists");
            exception.setMessage("Username already exists");
            exception.setTimestamp(LocalDateTime.now().toString());
            return ResponseEntity.badRequest().body(new ApiResponse(exception.getMessage(), false));
        }

        // Validate fields
        if (stakeholderResourse.getUsername() == null || stakeholderResourse.getUsername().isEmpty()) {
            exception.addError("username", "Username is required");
        }
        // Additional validations...

        if (!exception.getErrors().isEmpty()) {
            exception.setMessage("Validation failed for registration");
            exception.setTimestamp(LocalDateTime.now().toString());
            return ResponseEntity.badRequest().body(new ApiResponse(exception.getMessage(), false));
        }

        // If no errors, proceed with registration
        StakeholderResourse savedStakeholder = stakeholderService.register(stakeholderResourse);

        // Send a successful response
        return ResponseEntity.ok(new ApiResponse("Registration successful!", true, savedStakeholder));
    }



    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody StakeholderResourse stakeholderResourse) {
        CommonExeption exception = new CommonExeption();

        // Check for missing fields
        if (stakeholderResourse.getId() == 0) {
            exception.addError("id", "Id is required");
        }
        if (stakeholderResourse.getUsername() == null || stakeholderResourse.getUsername().isEmpty()) {
            exception.addError("username", "Username is required");
        }
        if (stakeholderResourse.getEmail() == null || stakeholderResourse.getEmail().isEmpty()) {
            exception.addError("email", "Email is required");
        }
        if (stakeholderResourse.getAddress() == null || stakeholderResourse.getAddress().isEmpty()) {
            exception.addError("address", "Address is required");
        }
        if (stakeholderResourse.getPhone() == null || stakeholderResourse.getPhone().isEmpty()) {
            exception.addError("phone", "Phone is required");
        }

        // If any validation errors are present, return a bad request response
        if (!exception.getErrors().isEmpty()) {
            exception.setMessage("Validation failed for update");
            exception.setTimestamp(LocalDateTime.now().toString());
            return ResponseEntity.badRequest().body(exception);
        }

        // Proceed to update the stakeholder if validation passes
        return ResponseEntity.ok(stakeholderService.update(stakeholderResourse));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (id <= 0) {
            CommonExeption exception = new CommonExeption("Invalid id provided");
            exception.setTimestamp(LocalDateTime.now().toString());
            exception.setErrorCode("400");
            return ResponseEntity.badRequest().body(exception);
        }

        // Delete the stakeholder if the id is valid
        stakeholderService.delete(id);
        return ResponseEntity.ok().build();
    }
}
