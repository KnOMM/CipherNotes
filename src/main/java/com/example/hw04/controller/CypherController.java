package com.example.hw04.controller;

import com.example.hw04.dto.CaesarDto;
import com.example.hw04.dto.VigenereDto;
import com.example.hw04.entity.AppUser;
import com.example.hw04.entity.Caesar;
import com.example.hw04.entity.Cypher;
import com.example.hw04.entity.Vigenere;
import com.example.hw04.service.CaesarServiceImpl;
import com.example.hw04.service.CypherService;
import com.example.hw04.service.VigenereServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CypherController {
    private final CaesarServiceImpl caesarService;
    private final VigenereServiceImpl vigenereService;

    @GetMapping("/caesar")
    public String getCaesars(@AuthenticationPrincipal AppUser user, Model model) {
        if (user == null) {
            return "caesar";
        }
        List<Cypher> caesars = caesarService.findAllCyphersByUser(user.getId());
        model.addAttribute("caesars", caesars);
        return "caesar/caesar";
    }

    @GetMapping("/vigenere")
    public String getVigenere(@AuthenticationPrincipal AppUser user, Model model) {
        if (user == null) {
            return "vigenere/vigenere";
        }
        List<Cypher> vigeneres = vigenereService.findAllCyphersByUser(user.getId());
        model.addAttribute("vigeneres", vigeneres);
        return "vigenere/vigenere";
    }

    @GetMapping("/caesar/encode")
    public String encodeCaesar(Model model) {
        CaesarDto caesarDto = new CaesarDto();
        model.addAttribute("caesar", caesarDto);
        return "caesar/encode";
    }

    @GetMapping("/vigenere/encode")
    public String encodeVigenere(Model model) {
        VigenereDto vigenereDto = new VigenereDto();
        model.addAttribute("vigenere", vigenereDto);
        return "vigenere/encode";
    }

    @PostMapping("/caesar/encode/save")
    public String saveCaesar(@Valid @ModelAttribute("caesar") CaesarDto caesarDto,
                             BindingResult result,
                             @AuthenticationPrincipal AppUser user,
                             Model model) {


        if (result.hasErrors()) {
            model.addAttribute("caesar", caesarDto);
            return "caesar/encode";
        }

        Caesar caesar = new Caesar();
        caesar.setText(Caesar.encrypt(caesarDto.getText(), Integer.valueOf(caesarDto.getPass())));
        caesar.setPass(Integer.valueOf(caesarDto.getPass()));
        caesar.setUser(user);

        caesarService.addCypher(caesar);

        model.addAttribute("encoded", caesar.getText());
        return "redirect:/caesar/encode?success";
    }

    @PostMapping("/vigenere/encode/save")
    public String saveVigenere(@Valid @ModelAttribute("vigenere") VigenereDto vigenereDto,
                               BindingResult result,
                               @AuthenticationPrincipal AppUser user,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("vigenere", vigenereDto);
            return "vigenere/encode";
        }

        Vigenere vigenere = new Vigenere();
        vigenere.setText(Vigenere.encrypt(vigenereDto.getText(), vigenereDto.getPass()));
        vigenere.setPass(vigenereDto.getPass());
        vigenere.setUser(user);

        vigenereService.addCypher(vigenere);

        model.addAttribute("encoded", vigenere.getText());
        return "redirect:/vigenere/encode?success";
    }

    @GetMapping("caesar/{id}")
    public String showDecryptC(@PathVariable String id,
                              Model model) {
        Cypher optionalCaesar = caesarService.findCypher(UUID.fromString(id));
        if (optionalCaesar != null) {
            Caesar caesar = (Caesar) optionalCaesar;
            model.addAttribute("decode", Caesar.decrypt(caesar.getText(), caesar.getPass()));
            return "caesar/decrypt";
        }
        return "redirect:/caesar";
    }

    @GetMapping("vigenere/{id}")
    public String showDecryptV(@PathVariable String id,
                              Model model) {
        Cypher optionalVigenere = vigenereService.findCypher(UUID.fromString(id));
        if (optionalVigenere != null) {
            Vigenere vigenere = (Vigenere) optionalVigenere;
            model.addAttribute("decode", Vigenere.decrypt(vigenere.getText(), vigenere.getPass()));
            return "vigenere/decrypt";
        }
        return "redirect:/vigenere";
    }
}
