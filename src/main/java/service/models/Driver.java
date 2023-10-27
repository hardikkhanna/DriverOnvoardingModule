package service.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Data
@Builder
@Entity
@Table(name = "Driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    private User user;

    @Column(name = "licenseNumber")
    private String licenseNumber;

    @Column(name = "vehicleModel")
    private String vehicleModel;

    // Add a one-to-many mapping to the Document entity
    @OneToMany(mappedBy = "driver")
    private List<Document> documents;

    // Constructors, getters, and setters
}

