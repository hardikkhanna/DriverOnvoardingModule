package service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "verified")
    private boolean verified;

    // Add a many-to-one mapping to the Driver entity
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
