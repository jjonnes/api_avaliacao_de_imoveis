package com.amostras.api_amostras.repository;

import com.amostras.api_amostras.dto.AmostrasDto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório para a entidade AmostrasDto.
 */
@Repository
public interface AmostrasDtoRepository extends JpaRepository<AmostrasDto, Integer> {

  /**
   * Busca todas as amostras por cidade.
   * 
   * @param cidade
   *            Nome da cidade
   * @return Lista de amostras
   */
  List<AmostrasDto> findByCidade(String cidade);

  /**
   * Exclui todas as amostras por cidade.
   * 
   * @param cidade
   *            Nome da cidade
   */
  void deleteByCidade(String cidade);
  
}