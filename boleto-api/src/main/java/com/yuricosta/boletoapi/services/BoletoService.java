package com.yuricosta.boletoapi.services;

import com.yuricosta.boletoapi.entities.Boleto;
import com.yuricosta.boletoapi.enums.SituacaoBoleto;
import com.yuricosta.boletoapi.exceptions.BoletoJaExistenteException;
import com.yuricosta.boletoapi.kafka.BoletoProducer;
import com.yuricosta.boletoapi.repositories.BoletoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BoletoService {
    private final BoletoRepository boletoRepository;
    private final BoletoProducer boletoProducer;

    public BoletoService(BoletoRepository boletoRepository, BoletoProducer boletoProducer) {
        this.boletoRepository = boletoRepository;
        this.boletoProducer = boletoProducer;
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

        var savedBoleto = boletoRepository.save(newBoleto);

        boletoProducer.sendRecord(savedBoleto);

        return savedBoleto;
    }
}
