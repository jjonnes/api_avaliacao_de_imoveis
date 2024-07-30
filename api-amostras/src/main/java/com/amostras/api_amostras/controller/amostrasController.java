package com.amostras.api_amostras.controller;

import com.amostras.api_amostras.dto.AmostrasDto;
import com.amostras.api_amostras.repository.AmostrasDtoRepository;
import com.amostras.api_amostras.service.AmostrasDtoService;
import com.amostras.api_amostras.service.WebScrapingService;
import com.amostras.api_amostras.utils.WebScrapingUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador responsável por gerenciar as amostras.
 */
@RestController
@RequestMapping("/amostras")
public class amostrasController {

    /**
     * Serviço de amostras.
     */
    @Autowired
    private AmostrasDtoService amostrasDtoService;

    /**
     * Serviço de web scraping.
     */
    @Autowired
    private WebScrapingService webScrapingService;

    /**
     * Repositório de amostras.
     */
    @Autowired
    private AmostrasDtoRepository amostrasDtoRepository;

    /**
     * Cria uma nova amostra.
     *
     * @param cidade Nome da cidade.
     * @return Lista de amostras criadas.
     */
    @PostMapping
    public ResponseEntity<List<AmostrasDto>> save(@RequestParam("cidade") String cidade) {
        try {
            // Chama o serviço de scraping para obter o número de resultados
            WebScrapingUtils.abrirBrowser();
            int numeroResultados = webScrapingService.extrairNumeroResultados(cidade);
            // Lista para armazenar os objetos AmostrasDto salvos
            List<AmostrasDto> savedAmostrasDtos = new ArrayList<>();

            // Itera sobre os resultados e faz o scraping para cada um
            for (int i = 1; i <= numeroResultados/20; i++) {

                    for (int j = 1; j <= 20; j++) {
                        // Chama o serviço de scraping para extrair os dados do mercado para este resultado
                        AmostrasDto.DadosMercado dadosMercado = webScrapingService.extrairDados(j);
                        // Cria um novo objeto AmostrasDto com os dados extraídos
                        AmostrasDto amostrasDto = new AmostrasDto(cidade, dadosMercado);
                        // Salva no banco de dados
                        AmostrasDto savedAmostrasDto = amostrasDtoService.salvar(amostrasDto);
                        // Adiciona o objeto salvo à lista de retorno
                        savedAmostrasDtos.add(savedAmostrasDto);
                        System.out.println(i);
                        System.out.println(j);
                    }
                    int j = 1;
                    WebScrapingUtils.fecharBrowser();
                    WebScrapingUtils.abrirBrowser();
                    WebScrapingUtils.obterUrlsResultados(cidade, i + j);
            }

            WebScrapingUtils.fecharBrowser();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedAmostrasDtos);
        } catch (Exception e) {
            // Tratar possíveis erros, como URL inválida, XPath não encontrado, etc.
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * Busca uma amostra por ID.
     *
     * @param id ID da amostra.
     * @return Amostra encontrada ou ResponseEntity.notFound().build() se não encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AmostrasDto> findById(@PathVariable("id") final int id) {
        Optional<AmostrasDto> amostrasDto = amostrasDtoService.encontrarPorId(id);
        return amostrasDto.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca amostras por cidade.
     *
     * @param cidade Nome da cidade.
     * @return Lista de amostras encontradas.
     */
    @GetMapping("/amostras-cidade")
    public ResponseEntity<List<AmostrasDto>> findByCidade(@RequestParam("cidade") final String cidade) {
        List<AmostrasDto> amostrasDtoList = amostrasDtoService.findByCidade(cidade);
        return ResponseEntity.ok(amostrasDtoList);
    }

    /**
     * Busca todas as amostras.
     *
     * @return Lista de amostras.
     */
    @GetMapping
    public ResponseEntity<List<AmostrasDto>> getAll() {
        List<AmostrasDto> ctgDtos = amostrasDtoService.listarTodos();
        return ResponseEntity.ok(ctgDtos);
    }

    // UPDATE PUT
    /**
     * Atualiza uma amostra existente.
     *
     * @param id ID da amostra a ser atualizada.
     * @param request Objeto AmostrasDto com os dados atualizados.
     * @return Amostra atualizada ou ResponseEntity.notFound().build() se não encontrada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AmostrasDto> update(@PathVariable("id") final int id,
                                            @RequestBody final AmostrasDto request) {
        Optional<AmostrasDto> existingAmostrasDto = amostrasDtoService.encontrarPorId(id);
        if (existingAmostrasDto.isPresent()) {
            AmostrasDto amostrasDtoToUpdate = existingAmostrasDto.get();
            amostrasDtoToUpdate.setCidade(request.getCidade());
            amostrasDtoToUpdate.setDados(request.getDados());
            AmostrasDto updatedCtgDto = amostrasDtoService.salvar(amostrasDtoToUpdate);
            return ResponseEntity.ok(updatedCtgDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE DELETE
    /**
     * Exclui uma amostra por ID.
     *
     * @param id ID da amostra a ser excluída.
     * @return ResponseEntity.noContent().build() se excluída com sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final int id) {
        amostrasDtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE by city
    /**
     * Exclui todas as amostras de uma cidade.
     *
     * @param cidade Nome da cidade.
     * @return ResponseEntity.noContent().build() se excluídas com sucesso.
     */
    @DeleteMapping("/deletar-cidade")
    public ResponseEntity<Void> deleteByCidade(@RequestParam("cidade") final String cidade) {
        amostrasDtoService.deleteByCidade(cidade);
        return ResponseEntity.noContent().build();
    }

    // DELETE DELETE
    /**
     * Exclui todas as amostras.
     *
     * @return ResponseEntity.noContent().build() se excluídas com sucesso.
     */
    @DeleteMapping
    public ResponseEntity<Void> delete() {
        List<AmostrasDto> amostrasDtos = amostrasDtoRepository.findAll();
        amostrasDtos.forEach(amostrasDto -> amostrasDtoRepository.delete(amostrasDto));
        return ResponseEntity.noContent().build();
    }

    /**
     * Retorna uma mensagem de boas-vindas.
     *
     * @return "hello word".
     */
    @GetMapping("/hello")
    public String helloWord() {
        return "hello word";
    }
}