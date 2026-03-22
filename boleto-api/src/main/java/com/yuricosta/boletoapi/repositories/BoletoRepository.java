package com.yuricosta.boletoapi.repositories;

import com.yuricosta.boletoapi.entities.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Long> {
    Optional<Boleto> findByCodigoBarras(String codigoBarras);
}
