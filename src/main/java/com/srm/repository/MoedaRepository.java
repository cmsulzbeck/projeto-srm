package com.srm.repository;

import com.srm.entity.Moeda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoedaRepository extends JpaRepository<Moeda, Long> {
    Optional<Moeda> findByCodigo(String codigo);
}