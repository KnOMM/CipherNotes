package com.example.encypher.service;

import com.example.encypher.entity.Caesar;
import com.example.encypher.entity.Cypher;
import com.example.encypher.repo.CaesarRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Builder
public class CaesarServiceImpl implements CypherService {

    private final CaesarRepository caesarRepository;

    @Override
    public List<Cypher> findAllCyphersByUser(UUID id) {
        return caesarRepository.findByUserId(id);
    }

    @Override
    public void deleteCypher(UUID id) {
        Optional<Caesar> optionalCaesar = caesarRepository.findById(id);
        if (optionalCaesar.isPresent()) {
            caesarRepository.deleteById(id);
        }
    }

    @Override
    public void addCypher(Cypher cypher) {
        Caesar caesar = (Caesar) cypher;
        caesarRepository.save(caesar);
    }

    @Override
    public Cypher findCypher(UUID id) {
        Optional<Caesar> optionalCaesar = caesarRepository.findById(id);
        if (optionalCaesar.isPresent()){
            return caesarRepository.findById(id).get();
        }
        return null;
    }
}
