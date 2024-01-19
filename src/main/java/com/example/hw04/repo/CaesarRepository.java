package com.example.hw04.repo;

import com.example.hw04.entity.Caesar;
import com.example.hw04.entity.Cypher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CaesarRepository extends JpaRepository<Caesar, UUID> {
    List<Cypher> findByUserId(UUID uuid);
}
