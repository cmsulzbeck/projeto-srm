package com.srm.repository;

import com.srm.entity.Reino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReinoRepository extends JpaRepository<Reino, Long> {
} 