package com.example.hw04.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    //    @ManyToMany(mappedBy = "roles")
//    private List<AppUser> users;
}
