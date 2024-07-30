package com.amostras.api_amostras.service;

import org.springframework.stereotype.Service;
import com.amostras.api_amostras.dto.AmostrasDto;
import com.amostras.api_amostras.utils.Utils;
import com.amostras.api_amostras.utils.WebScrapingUtils;

/**
 * Serviço responsável por realizar web scraping de dados de imóveis.
 */
@Service
public class WebScrapingService {

    /**
     * Extrai o número de resultados de uma busca por bairro.
     *
     * @param bairro Nome do bairro.
     * @return Número de resultados.
     * @throws Exception Se ocorrer um erro durante o web scraping.
     */
    public int extrairNumeroResultados(String bairro) throws Exception {
        String url = "https://www.wimoveis.com.br/venda/imoveis/df/" + bairro;
        return WebScrapingUtils.numeroResultados(url);
    }

    /**
     * Extrai os dados de um imóvel a partir de sua posição na lista de resultados.
     *
     * @param i Posição do imóvel na lista de resultados.
     * @return Dados do imóvel.
     * @throws Exception Se ocorrer um erro durante o web scraping.
     */
    public AmostrasDto.DadosMercado extrairDados(int i) throws Exception {
        try {
            // Extrai o endereço do imóvel
            String endereco = WebScrapingUtils.extrairEndereco(i);
            // Extrai o bairro do imóvel
            String bairro = WebScrapingUtils.extrairBairro(i);
            // Extrai a área do imóvel
            String area = WebScrapingUtils.extrairArea(i);
            // Extrai o preço do imóvel
            String preco = WebScrapingUtils.extrairPreco(i);
            // Extrai o número de quartos do imóvel
            String quartos = WebScrapingUtils.extrairQuartos(i);
            // Extrai o número de banheiros do imóvel
            String ban = WebScrapingUtils.extrairBanheiros(i);
            // Extrai o número de vagas do imóvel
            String vaga = WebScrapingUtils.extrairVagas(i);
            // Extrai a URL da página do imóvel
            String urlAmostra = WebScrapingUtils.extrairUrlAmostra(i);

            // Cria um objeto DadosMercado com os dados extraídos
            return new AmostrasDto.DadosMercado(endereco, bairro, Utils.extrairDouble(area), 
                Utils.extrairDouble(preco), Utils.extrairNumero(quartos), Utils.extrairNumero(ban), 
                Utils.extrairNumero(vaga), urlAmostra);
        } catch (Exception e) {
            // Se ocorrer um erro, retorna um objeto DadosMercado com valores padrão
            return new AmostrasDto.DadosMercado("0", "0", 0, 0, 0, 0, 0, "0");

        }
    }
}