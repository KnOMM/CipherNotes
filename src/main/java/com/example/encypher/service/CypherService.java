package com.example.encypher.service;

import com.example.encypher.entity.Cypher;

import java.util.List;
import java.util.UUID;

public interface CypherService {
    List<Cypher> findAllCyphersByUser(UUID id);
    void deleteCypher(UUID id);
    void addCypher(Cypher cypher);
    Cypher findCypher(UUID id);
}
