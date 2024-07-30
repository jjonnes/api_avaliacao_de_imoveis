package com.amostras.api_amostras.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;

/**
 * Controlador responsável por executar o script Python de avaliação de imóveis.
 */
@RestController
public class PythonController {

    /**
     * Executa o script Python de avaliação de imóveis com os parâmetros fornecidos.
     *
     * @param cidade Nome da cidade.
     * @param bairro Nome do bairro.
     * @param area Área do imóvel.
     * @param quartos Número de quartos.
     * @param banheiros Número de banheiros.
     * @param vagas Número de vagas.
     * @return Resultado da avaliação do imóvel.
     */
    @GetMapping("/avaliacao")
    public String avaliarImovel(
        @RequestParam String cidade,
        @RequestParam String bairro,
        @RequestParam int area,
        @RequestParam int quartos,
        @RequestParam int banheiros,
        @RequestParam int vagas) {

        // Cria um StringBuilder para armazenar o resultado da avaliação
        StringBuilder result = new StringBuilder();

        try {
            // Caminho relativo do script Python
            String scriptPath = "api-amostras/projeto_avaliação_regressão_linear/regressao.py";

            // Verifique o diretório de trabalho
            System.out.println("Diretório de trabalho atual: " + new File(".").getAbsolutePath());

            // Verifique se o arquivo do script existe
            File scriptFile = new File(scriptPath);
            if (!scriptFile.exists()) {
                System.out.println("Arquivo não encontrado: " + scriptPath);
                return "Erro: Arquivo não encontrado";
            }

            // Cria um ProcessBuilder para executar o script Python
            ProcessBuilder pb = new ProcessBuilder(
                "python",
                scriptPath,
                cidade,
                bairro,
                String.valueOf(area),
                String.valueOf(quartos),
                String.valueOf(banheiros),
                String.valueOf(vagas)
            );

            // Redireciona o erro para o input stream
            pb.redirectErrorStream(true);

            // Inicia o processo
            Process p = pb.start();

            // Cria um BufferedReader para ler a saída do processo
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            // Lê a saída do processo linha por linha
            String line;
            while ((line = in.readLine()) != null) {
                // Adiciona a linha ao resultado
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            // Trata o erro de execução do script Python
            e.printStackTrace();
            return "Erro ao executar o script Python: " + e.getMessage();
        }

        // Retorna o resultado da avaliação
        return result.toString();
    }
}
