package com.online.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stakeholder_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class StakeholderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // For auto-increment fields
    private Long id;

    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;
    private String stakeholderType;

    private String createdBy;
    private LocalDateTime createdDate;  // Using LocalDateTime for timestamp
    private String updatedBy;
    private LocalDateTime updatedDate;
    private String deletedBy;
    private LocalDateTime deletedDate;


    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
    private List<TicketPool> events; // A stakeholder can manage multiple events


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();  // Set createdDate to the current timestamp
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();  // Set updatedDate to the current timestamp
    }
}
