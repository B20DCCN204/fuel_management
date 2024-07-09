package com.giangtrung.fuel_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type")
@Getter
@Setter
public class Type {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "type")
    private List<Fuel> fuels = new ArrayList<>();
}
