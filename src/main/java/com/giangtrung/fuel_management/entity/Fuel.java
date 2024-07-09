package com.giangtrung.fuel_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "fuel")
@Getter
@Setter
public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "price")
    private Double Price;
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;
}
