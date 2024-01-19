package com.example.hw04.repo;

import com.example.hw04.entity.Caesar;
import com.example.hw04.entity.Cypher;
import com.example.hw04.entity.Vigenere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VigenereRepository extends JpaRepository<Vigenere, UUID> {
    List<Cypher> findByUserId(UUID uuid);
}
