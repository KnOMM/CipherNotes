package com.example.hw04.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Caesar extends Cypher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer pass;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;

    public static String encrypt(String text, Integer shift){
        int length = text.getBytes().length;
        byte[] textBytes = new byte[length];

        for (int i = 0; i < textBytes.length; i++) { // shifts bytes
            textBytes[i] = (byte) (text.getBytes()[i] + shift);
        }
        return Base64.getEncoder().encodeToString(textBytes);
    }

    public static String decrypt(String text, Integer shift){

        try {
            Base64.getDecoder().decode(text.getBytes().clone()); // try to decode the string into arr
            var textBytes = Base64.getDecoder().decode(text.getBytes());

            for (int i = 0; i < textBytes.length; i++) { // shifts bytes
                textBytes[i] = (byte) (textBytes[i] - shift);
            }
            return new String(textBytes, StandardCharsets.UTF_8);

        } catch (IllegalArgumentException e) {
            System.out.println("Bad base64 text");
        }
        return null;
    }
}
