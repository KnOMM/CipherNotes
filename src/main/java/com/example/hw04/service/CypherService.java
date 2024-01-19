package com.example.hw04.service;

import com.example.hw04.entity.Cypher;

import java.util.List;
import java.util.UUID;

public interface CypherService {
    List<Cypher> findAllCyphersByUser(UUID id);
    void deleteCypher(UUID id);
    void addCypher(Cypher cypher);
    Cypher findCypher(UUID id);
}
