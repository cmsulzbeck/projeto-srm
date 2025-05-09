package com.srm.service;

import com.srm.dto.MoedaDTO;
import com.srm.exception.MoedaNaoEncontradaException;
import com.srm.mapper.MoedaMapper;
import com.srm.entity.Moeda;
import com.srm.repository.MoedaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Slf4j
@Service
public class MoedaService {

    @Autowired
    private MoedaRepository moedaRepository;

    @Autowired
    private MoedaMapper moedaMapper;

    @Transactional
    public MoedaDTO criarMoeda(MoedaDTO moedaDTO) {
        log.info("Criando nova moeda: {}", moedaDTO);
        
        Moeda moeda = moedaMapper.toEntity(moedaDTO);
        moeda = moedaRepository.save(moeda);
        
        log.info("Moeda criada com sucesso: {}", moeda);
        return moedaMapper.toDTO(moeda);
    }

    @Transactional
    public MoedaDTO atualizarTaxaCambio(String codigo, BigDecimal novaTaxa) {
        log.info("Atualizando taxa de câmbio para moeda {}: nova taxa = {}", codigo, novaTaxa);
        
        Moeda moeda = moedaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new MoedaNaoEncontradaException(codigo));
        
        moeda.setTaxaCambio(novaTaxa);
        moeda = moedaRepository.save(moeda);
        log.info("Taxa de câmbio atualizada com sucesso: {}", moeda);
        return moedaMapper.toDTO(moeda);
    }

    public Optional<MoedaDTO> buscarPorCodigo(String codigo) {
        log.info("Buscando moeda por código: {}", codigo);
        return moedaRepository.findByCodigo(codigo)
                .map(moedaMapper::toDTO);
    }

    public Moeda buscarEntidadePorCodigo(String codigo) {
        return moedaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new MoedaNaoEncontradaException(codigo));
    }

    public List<MoedaDTO> listarTodas() {
        log.info("Listando todas as moedas");
        
        List<MoedaDTO> moedas = moedaRepository.findAll().stream()
                .map(moedaMapper::toDTO)
                .collect(Collectors.toList());
        
        log.info("Total de moedas encontradas: {}", moedas.size());
        return moedas;
    }
} 