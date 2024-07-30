package com.amostras.api_amostras.service;

import com.amostras.api_amostras.dto.AmostrasDto;
import com.amostras.api_amostras.repository.AmostrasDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar as amostras.
 */
@Service
public class AmostrasDtoService {

    /**
     * Repositório de acesso a dados de amostras.
     */
    @Autowired
    private AmostrasDtoRepository amostrasDtoRepository;

    /**
     * Retorna uma lista de todas as amostras.
     *
     * @return Lista de amostras.
     */
    public List<AmostrasDto> listarTodos() {
        return amostrasDtoRepository.findAll();
    }

    /**
     * Busca uma amostra por ID.
     *
     * @param id ID da amostra.
     * @return Amostra encontrada ou Optional.empty() se não encontrada.
     */
    public Optional<AmostrasDto> encontrarPorId(int id) {
        return amostrasDtoRepository.findById(id);
    }

    /**
     * Busca amostras por cidade.
     *
     * @param cidade Nome da cidade.
     * @return Lista de amostras encontradas.
     */
    public List<AmostrasDto> findByCidade(String cidade) {
        return amostrasDtoRepository.findByCidade(cidade);
    }

    /**
     * Salva uma amostra.
     *
     * @param amostrasDto Amostra a ser salva.
     * @return Amostra salva.
     */
    public AmostrasDto salvar(AmostrasDto amostrasDto) {
        return amostrasDtoRepository.save(amostrasDto);
    }

    /**
     * Deleta uma amostra por ID.
     *
     * @param id ID da amostra a ser deletada.
     */
    public void deletar(int id) {
        amostrasDtoRepository.deleteById(id);
    }

    /**
     * Deleta amostras por cidade.
     *
     * @param cidade Nome da cidade.
     */
    @Transactional
    public void deleteByCidade(String cidade) {
        amostrasDtoRepository.deleteByCidade(cidade);
    }
}