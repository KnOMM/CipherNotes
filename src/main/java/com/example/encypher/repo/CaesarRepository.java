package com.example.encypher.repo;

import com.example.encypher.entity.Caesar;
import com.example.encypher.entity.Cypher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CaesarRepository extends JpaRepository<Caesar, UUID> {
    List<Cypher> findByUserId(UUID uuid);
}
