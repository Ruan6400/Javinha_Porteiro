package com.tavolaquad.porteiro.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    @GetMapping("/protected/rota")
    public String protectedRoute() {
        return "Rota protegida";
    }

    
    @GetMapping("/public/rota")
    public String publicRoute() {
        return "Rota pública";
    }
}
