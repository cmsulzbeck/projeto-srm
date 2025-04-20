package com.srm.service;

import com.srm.dto.ReinoDTO;
import com.srm.entity.Reino;
import com.srm.exception.ReinoNaoEncontradoException;
import com.srm.mapper.ReinoMapper;
import com.srm.repository.ReinoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReinoService {

    private final ReinoRepository reinoRepository;
    private final ReinoMapper reinoMapper;

    @Transactional
    public ReinoDTO criarReino(ReinoDTO reinoDTO) {
        log.info("Criando novo reino: {}", reinoDTO);
        Reino reino = reinoMapper.toEntity(reinoDTO);
        Reino reinoSalvo = reinoRepository.save(reino);
        log.info("Reino criado com sucesso: {}", reinoSalvo);
        return reinoMapper.toDTO(reinoSalvo);
    }

    @Transactional(readOnly = true)
    public ReinoDTO buscarPorId(Long id) {
        log.info("Buscando reino com id: {}", id);
        return reinoRepository.findById(id)
                .map(reinoMapper::toDTO)
                .orElseThrow(() -> new ReinoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public List<ReinoDTO> listarTodos() {
        log.info("Listando todos os reinos");
        List<Reino> reinos = reinoRepository.findAll();
        log.info("Total de reinos encontrados: {}", reinos.size());
        return reinos.stream()
                .map(reinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReinoDTO atualizarReino(Long id, ReinoDTO reinoDTO) {
        log.info("Atualizando reino com id {}: {}", id, reinoDTO);
        Reino reinoExistente = reinoRepository.findById(id)
                .orElseThrow(() -> new ReinoNaoEncontradoException(id));
        
        reinoMapper.updateEntity(reinoDTO, reinoExistente);
        Reino reinoAtualizado = reinoRepository.save(reinoExistente);
        log.info("Reino atualizado com sucesso: {}", reinoAtualizado);
        return reinoMapper.toDTO(reinoAtualizado);
    }

    @Transactional
    public void deletarReino(Long id) {
        log.info("Deletando reino com id: {}", id);
        if (!reinoRepository.existsById(id)) {
            throw new ReinoNaoEncontradoException(id);
        }
        reinoRepository.deleteById(id);
        log.info("Reino deletado com sucesso");
    }

    public Reino buscarEntidadePorId(Long id) {
        log.info("Buscando entidade reino por ID: {}", id);
        return reinoRepository.findById(id)
                .orElseThrow(() -> new ReinoNaoEncontradoException(id));
    }
} 