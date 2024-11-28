package com.online.controller;

import com.online.domain.StakeholderDetails;
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
        // Find stakeholder by username
        Optional<StakeholderDetails> stakeholderDetails = Optional.ofNullable(stakeholderDao.findByUsername(loginRequest.getUsername()));

        // Check if username exists
        if (stakeholderDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CommonExeption.createError("Username not found", "USER_NOT_FOUND", "/login"));        }

        // Check if the password matches
        if (!stakeholderDetails.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CommonExeption.createError("Username not found", "USER_NOT_FOUND", "/login"));        }

        // If login is successful
        return ResponseEntity.ok("Login successful!");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody StakeholderResourse stakeholderResourse) {
        CommonExeption exception = new CommonExeption();

        // Check if the username already exists
        if (stakeholderDao.findByUsername(stakeholderResourse.getUsername()) != null) {
            exception.addError("username", "Username already exists");
            exception.setMessage("Username already exists");
            exception.setTimestamp(LocalDateTime.now().toString());
            return ResponseEntity.badRequest().body(exception);
        }

        // Perform additional field-level validations
        validateStakeholderResource(stakeholderResourse, exception);

        // If validation errors exist, return them
        if (!exception.getErrors().isEmpty()) {
            exception.setMessage("Validation failed for registration");
            exception.setTimestamp(LocalDateTime.now().toString());
            return ResponseEntity.badRequest().body(exception);
        }

        // Register the user
        StakeholderResourse registeredStakeholder = stakeholderService.register(stakeholderResourse);
        return ResponseEntity.ok(registeredStakeholder);
    }

    // Helper method for validation
    private void validateStakeholderResource(StakeholderResourse stakeholderResourse, CommonExeption exception) {
        if (stakeholderResourse.getUsername() == null || stakeholderResourse.getUsername().isEmpty()) {
            exception.addError("username", "Username is required");
        }
        if (stakeholderResourse.getPassword() == null || stakeholderResourse.getPassword().isEmpty()) {
            exception.addError("password", "Password is required");
        }
        if (stakeholderResourse.getEmail() == null || stakeholderResourse.getEmail().isEmpty()) {
            exception.addError("email", "Email is required");
        } else if (!stakeholderResourse.getEmail().contains("@")) { // Basic email validation
            exception.addError("email", "Email is invalid");
        }
        if (stakeholderResourse.getAddress() == null || stakeholderResourse.getAddress().isEmpty()) {
            exception.addError("address", "Address is required");
        }
        if (stakeholderResourse.getPhone() == null || stakeholderResourse.getPhone().isEmpty()) {
            exception.addError("phone", "Phone is required");
        } else if (!stakeholderResourse.getPhone().matches("\\d{8,12}")) { // Check phone number length (8-12 digits)
            exception.addError("phone", "Phone must be 8 to 12 digits");
        }
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
