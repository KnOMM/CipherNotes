package com.example.encypher.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vigenere extends Cypher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String pass;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;

    public static String encrypt(String text, String shift) {
        byte[] textBytes = text.getBytes();
        byte[] key = shift.getBytes(); // secret form user

        byte[] secret = new byte[textBytes.length];
        for (int i = 0; i < secret.length; i++) { // creates a secret
            secret[i] = key[(i % key.length)];
        }
        for (int i = 0; i < textBytes.length; i++) {
            textBytes[i] += secret[i];
        }
        return Base64.getEncoder().encodeToString(textBytes);
    }

    public static String decrypt(String text, String shift) {
        var textBytes = text.getBytes(); // input form user

        try {
            var decodeBase64 = Base64.getDecoder().decode(textBytes);
            var key = shift.getBytes(); // secret form user
            var secret = new byte[textBytes.length]; // allocates space for secret

            for (int i = 0; i < secret.length; i++) {
                secret[i] = key[(i % key.length)]; // creates secret
            }
            for (int i = 0; i < decodeBase64.length; i++) {
                decodeBase64[i] -= secret[i];
            }
            return new String(decodeBase64, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            System.out.println("Bad base64 text");
        }
        return null;
    }
}
