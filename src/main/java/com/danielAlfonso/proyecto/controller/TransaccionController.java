package com.danielAlfonso.proyecto.controller;

import com.danielAlfonso.proyecto.dto.TransaccionRequest;
import com.danielAlfonso.proyecto.service.TransaccionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    TransaccionServices transaccionServices;

    @PostMapping("/crearTransaccion")
    public Map<String, Object> crearTransaccion(@RequestBody TransaccionRequest request) {

    return transaccionServices.crearTransaccion(request);
    }

    @DeleteMapping("/anular")
    public Map<String, Object> anularTransaccion(@RequestBody TransaccionRequest request) {

        return transaccionServices.anularTransaccion(request);
    }

    @GetMapping
    public String mostrarTransacciones(Model model) {
        model.addAttribute("transacciones",transaccionServices.mostrarTransacciones());
        return "transacciones";
    }

}
