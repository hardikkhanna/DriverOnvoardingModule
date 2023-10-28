package com.module.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Driver")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "licenseNumber")
    private String licenseNumber;

    @Column(name = "vehicleModel")
    private String vehicleModel;

    @Enumerated(EnumType.STRING)
    private NotificationMethod notificationMethod = NotificationMethod.SMS;

    @OneToMany(mappedBy = "driver")
    private List<Document> documents;
}

