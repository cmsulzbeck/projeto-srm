package com.srm.repository;

import com.srm.entity.FormulasConversao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormulasConversaoRepository extends JpaRepository<FormulasConversao, Long> {
    void deleteByProdutoId(Long produtoId);
} 