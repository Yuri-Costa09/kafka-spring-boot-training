package com.yuricosta.boletoapi.controllers;

import com.yuricosta.boletoapi.dtos.BoletoRequestDto;
import com.yuricosta.boletoapi.entities.Boleto;
import com.yuricosta.boletoapi.exceptions.BoletoJaExistenteException;
import com.yuricosta.boletoapi.services.BoletoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boleto")
public class BoletoController {


    private final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @PostMapping
    public ResponseEntity<Boleto> createBoleto(@RequestBody BoletoRequestDto request) {
        var boleto = boletoService.salvar(request.codigoBarras());

        return new ResponseEntity<>(boleto, HttpStatus.CREATED);
    }
}
