package com.danielAlfonso.proyecto.controller;

import com.danielAlfonso.proyecto.dto.EnrolarTarjetaRequest;
import com.danielAlfonso.proyecto.dto.TarjetaRequest;
import com.danielAlfonso.proyecto.service.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/tarjetas")
public class TarjetaController {


    @Autowired
    private TarjetaService service;

    @PostMapping("/crearTarjeta")
    public Map<String, Object> crearTarjeta(@RequestBody TarjetaRequest request) {

        return service.crearTarjeta(request);
    }

    @PostMapping("/enrolarTarjeta")
    public Map<String, Object> enrolarTarjeta(@RequestBody EnrolarTarjetaRequest request) {

        return service.EnrolarTarjetaRequest(request);
    }

    @GetMapping("/consultarTarjeta")
    public Map<String, Object> consultarTarjeta(@RequestParam("pan") String pan) {

        return service.consultarTarjeta(pan);
    }


    @DeleteMapping("/eliminarTarjeta")
    public Map<String, Object> eliminarTarjeta(
            @RequestParam("pan") String pan,
            @RequestParam("numeroValidacion") int numeroValidacion
    ) {
        return service.eliminarTarjeta(pan, numeroValidacion);
    }

    @GetMapping
    public String mostrarTarjetas(Model model) {
        model.addAttribute("tarjetas",service.mostrarTarjetas());
        return "tarjetas";
    }
}
