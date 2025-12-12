package com.example.mipixel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class WordleController {

    private static final List<String> PALABRAS_1989 = Arrays.asList(
            "STYLE", "BLANK", "SHAKE", "CLEAN", "DREAM"
    );

    private String palabraActual;

    public WordleController() {
        Random random = new Random();
        palabraActual = PALABRAS_1989.get(random.nextInt(PALABRAS_1989.size()));
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("mensaje", "Adivina la palabra de 5 letras inspirada en 1989");
        return "wordle";
    }

    @PostMapping("/verificar")
    @ResponseBody
    public Map<String, Object> verificarPalabra(@RequestParam String palabra) {
        Map<String, Object> respuesta = new HashMap<>();
        palabra = palabra.toUpperCase().trim();

        if (palabra.length() != 5) {
            respuesta.put("error", "La palabra debe tener 5 letras");
            return respuesta;
        }

        List<Map<String, String>> resultado = getMaps(palabra);

        respuesta.put("resultado", resultado);
        respuesta.put("ganaste", palabra.equals(palabraActual));

        if (palabra.equals(palabraActual)) {
            respuesta.put("mensaje", "¡Correcto! Ahora puedes ver tu sorpresa especial 💕");
        }

        return respuesta;
    }

    private List<Map<String, String>> getMaps(String palabra) {
        List<Map<String, String>> resultado = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Map<String, String> letraInfo = new HashMap<>();
            char letra = palabra.charAt(i);
            letraInfo.put("letra", String.valueOf(letra));

            if (palabraActual.charAt(i) == letra) {
                letraInfo.put("estado", "correcto");
            } else if (palabraActual.contains(String.valueOf(letra))) {
                letraInfo.put("estado", "presente");
            } else {
                letraInfo.put("estado", "incorrecto");
            }

            resultado.add(letraInfo);
        }
        return resultado;
    }

    @GetMapping("/amor")
    public String paginaAmor(Model model) {
        model.addAttribute("titulo", "Para mi amor");
        return "amor";
    }

    @PostMapping("/reiniciar")
    @ResponseBody
    public Map<String, String> reiniciar() {
        Random random = new Random();
        palabraActual = PALABRAS_1989.get(random.nextInt(PALABRAS_1989.size()));

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Juego reiniciado");
        return respuesta;
    }
}