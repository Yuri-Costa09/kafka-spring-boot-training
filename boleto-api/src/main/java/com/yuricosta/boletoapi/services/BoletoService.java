package com.yuricosta.boletoapi.services;

import com.yuricosta.boletoapi.entities.Boleto;
import com.yuricosta.boletoapi.enums.SituacaoBoleto;
import com.yuricosta.boletoapi.exceptions.BoletoJaExistenteException;
import com.yuricosta.boletoapi.repositories.BoletoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoletoService {
    private final BoletoRepository boletoRepository;

    public BoletoService(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

    public Boleto salvar(String codigoBarras) {
        if (boletoRepository.findByCodigoBarras(codigoBarras).isPresent())
            throw new BoletoJaExistenteException("Boleto já existe no sistema.");

        var newBoleto = Boleto.builder()
                .codigoBarras(codigoBarras)
                .situacaoBoleto(SituacaoBoleto.INICIALIZADO)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return boletoRepository.save(newBoleto);
    }
}
