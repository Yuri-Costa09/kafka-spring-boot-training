package com.yuricosta.boletoapi.services;

import com.yuricosta.boletoapi.entities.Boleto;
import com.yuricosta.boletoapi.enums.SituacaoBoleto;
import com.yuricosta.boletoapi.exceptions.BoletoJaExistenteException;
import com.yuricosta.boletoapi.kafka.BoletoProducer;
import com.yuricosta.boletoapi.repositories.BoletoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoletoServiceTest {

    private static final String codigoBarras = "1234";

    @Mock
    private BoletoProducer boletoProducer;

    @Mock
    private BoletoRepository boletoRepository;

    @InjectMocks
    private BoletoService boletoService;

    @Test
    @DisplayName("Given valid input, should save and emit event.")
    void shouldCreateBoletoSuccessfullyGivenValidInput() {

        when(boletoRepository.findByCodigoBarras(codigoBarras))
                .thenReturn(Optional.empty());

        var newBoleto = boletoFactory();

        when(boletoRepository.save(any(Boleto.class)))
                .thenReturn(newBoleto);

        Boleto boleto = boletoService.salvar(codigoBarras);

        assertNotNull(boleto);
        assertEquals(codigoBarras, boleto.getCodigoBarras());
        assertNotNull(boleto.getSituacaoBoleto());
        assertNotNull(boleto.getCreatedAt());
        assertNotNull(boleto.getUpdatedAt());

        verify(boletoRepository).findByCodigoBarras(codigoBarras);
        verify(boletoRepository).save(any(Boleto.class));
        verify(boletoProducer).sendRecord(newBoleto);

    }

    @Test
    @DisplayName("Should throw exception if boleto already exists")
    void shouldThrowExceptionWhenAlreadyExists() {
        var existingBoleto = boletoFactory();

        when(boletoRepository.findByCodigoBarras(codigoBarras))
                .thenReturn(Optional.of(existingBoleto));

        assertThrows(BoletoJaExistenteException.class,
                () -> boletoService.salvar(codigoBarras));

        verify(boletoRepository, never()).save(any(Boleto.class));
        verify(boletoProducer, never()).sendRecord(any(Boleto.class));
    }

    private Boleto boletoFactory() {
        return Boleto.builder()
                .codigoBarras(codigoBarras)
                .situacaoBoleto(SituacaoBoleto.INICIALIZADO)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should not call producer if repository save fails.")
    void shouldNotCallProducerWhenSaveFails() {
        when(boletoRepository.findByCodigoBarras(codigoBarras))
                .thenReturn(Optional.empty());

        when(boletoRepository.save(any(Boleto.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> boletoService.salvar(codigoBarras));

        verify(boletoProducer, never()).sendRecord(any(Boleto.class));
    }
}