package com.module.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Document",
        uniqueConstraints = @UniqueConstraint(columnNames = {"document_number", "driver_id"}))
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "verified")
    private boolean verified;

    // Add a many-to-one mapping to the Driver entity
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

}
