package com.tavolaquad.porteiro.controllers;

import org.springframework.web.bind.annotation.*;

import com.tavolaquad.porteiro.Utilitarios.JwtUtil;


@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class MainController {
    @GetMapping("/protected/rota")
    public String protectedRoute() {
        return "Rota protegida";
    }


    @GetMapping("/public/rota")
    public String publicRoute() {
        return JwtUtil.generateToken("jão","jão@email" );
    }
}
