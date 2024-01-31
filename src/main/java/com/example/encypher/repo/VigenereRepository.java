package com.example.encypher.repo;

import com.example.encypher.entity.Cypher;
import com.example.encypher.entity.Vigenere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VigenereRepository extends JpaRepository<Vigenere, UUID> {
    List<Cypher> findByUserId(UUID uuid);
}
