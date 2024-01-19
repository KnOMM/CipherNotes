package com.example.hw04.service;

import com.example.hw04.entity.Caesar;
import com.example.hw04.entity.Cypher;
import com.example.hw04.entity.Vigenere;
import com.example.hw04.repo.VigenereRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Builder
public class VigenereServiceImpl implements CypherService {

    private final VigenereRepository vigenereRepository;

    @Override
    public List<Cypher> findAllCyphersByUser(UUID id) {
        return vigenereRepository.findByUserId(id);
    }

    @Override
    public void deleteCypher(UUID id) {
        Optional<Vigenere> optionalCaesar = vigenereRepository.findById(id);
        if (optionalCaesar.isPresent()) {
            vigenereRepository.deleteById(id);
        }
    }

    @Override
    public void addCypher(Cypher cypher) {
        Vigenere vigenere = (Vigenere) cypher;
        vigenereRepository.save(vigenere);
    }

    @Override
    public Cypher findCypher(UUID id) {
        Optional<Vigenere> optionalCaesar = vigenereRepository.findById(id);
        if (optionalCaesar.isPresent()){
            return vigenereRepository.findById(id).get();
        }
        return null;
    }
}
