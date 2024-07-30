package com.amostras.api_amostras.dto;

import jakarta.persistence.*;

/**
 * Classe que representa um objeto de transferência de dados (DTO) para as amostras.
 */
@Entity
@Table(name = "amostras")
public class AmostrasDto {

    /**
     * Identificador único da amostra.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Cidade onde a amostra foi coletada.
     */
    @Column(name = "cidade")
    private String cidade;

    /**
     * Dados do mercado associados à amostra.
     */
    @Embedded
    private DadosMercado dados;

    /**
     * Construtor padrão.
     */
    public AmostrasDto() {
    }

    /**
     * Construtor que inicializa a cidade e os dados do mercado.
     * 
     * @param cidade Cidade onde a amostra foi coletada.
     * @param dados  Dados do mercado associados à amostra.
     */
    public AmostrasDto(String cidade, DadosMercado dados) {
        this.cidade = cidade;
        this.dados = dados;
    }

    /**
     * Retorna o identificador único da amostra.
     * 
     * @return Identificador único da amostra.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador único da amostra.
     * 
     * @param id Identificador único da amostra.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a cidade onde a amostra foi coletada.
     * 
     * @return Cidade onde a amostra foi coletada.
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Define a cidade onde a amostra foi coletada.
     * 
     * @param cidade Cidade onde a amostra foi coletada.
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * Retorna os dados do mercado associados à amostra.
     * 
     * @return Dados do mercado associados à amostra.
     */
    public DadosMercado getDados() {
        return dados;
    }

    /**
     * Define os dados do mercado associados à amostra.
     * 
     * @param dados Dados do mercado associados à amostra.
     */
    public void setDados(DadosMercado dados) {
        this.dados = dados;
    }

    /**
     * Classe que representa os dados do mercado associados à amostra.
     * 
     * @author [Seu nome]
     */
    @Embeddable
    public static class DadosMercado {
        /**
         * Endereço do mercado.
         */
        @Column(name = "endereco")
        private String endereco;

        /**
         * Bairro do mercado.
         */
        @Column(name = "bairro")
        private String bairro;

        /**
         * Área do mercado.
         */
        @Column(name = "area")
        private double area;

        /**
         * Preço do mercado.
         */
        @Column(name = "preco")
        private double preco;

        /**
         * Número de quartos do mercado.
         */
        @Column(name = "quartos")
        private int quartos;

        /**
         * Número de banheiros do mercado.
         */
        @Column(name = "banheiros")
        private int ban;

        /**
         * Número de vagas do mercado.
         */
        @Column(name = "vagas")
        private int vaga;

        /**
         * URL do mercado.
         */
        @Column(name = "URL")
        private String url;

        /**
         * Construtor padrão.
         */
        public DadosMercado() {
        }

        /**
         * Construtor que inicializa os dados do mercado.
         * 
         * @param endereco Endereço do mercado.
         * @param bairro   Bairro do mercado.
         * @param area     Área do mercado.
         * @param preco    Preço do mercado.
         * @param quartos  Número de quartos do mercado.
         * @param ban      Número de banheiros do mercado.
         * @param vaga     Número de vagas do mercado.
         * @param url      URL do mercado.
         */
        public DadosMercado(String endereco, String bairro, double area, double preco, int quartos, int ban, int vaga, String url) {
            this.endereco = endereco;
            this.bairro = bairro;
            this.area = area;
            this.preco = preco;
            this.quartos = quartos;
            this.ban = ban;
            this.vaga = vaga;
            this.url = url;
        }
    
        /**
         * Retorna o endereço do mercado.
         * 
         * @return Endereço do mercado.
         */
        public String getEndereco() {
            return endereco;
        }
    
        /**
         * Define o endereço do mercado.
         * 
         * @param endereco Endereço do mercado.
         */
        public void setEndereco(String endereco) {
            this.endereco = endereco;
        }
    
        /**
         * Retorna o bairro do mercado.
         * 
         * @return Bairro do mercado.
         */
        public String getBairro() {
            return bairro;
        }
    
        /**
         * Define o bairro do mercado.
         * 
         * @param bairro Bairro do mercado.
         */
        public void setBairro(String bairro) {
            this.bairro = bairro;
        }
    
        /**
         * Retorna a área do mercado.
         * 
         * @return Área do mercado.
         */
        public double getArea() {
            return area;
        }
    
        /**
         * Define a área do mercado.
         * 
         * @param area Área do mercado.
         */
        public void setArea(double area) {
            this.area = area;
        }
    
        /**
         * Retorna o preço do mercado.
         * 
         * @return Preço do mercado.
         */
        public double getPreco() {
            return preco;
        }
    
        /**
         * Define o preço do mercado.
         * 
         * @param preco Preço do mercado.
         */
        public void setPreco(double preco) {
            this.preco = preco;
        }
    
        /**
         * Retorna o número de quartos do mercado.
         * 
         * @return Número de quartos do mercado.
         */
        public int getQuartos() {
            return quartos;
        }
    
        /**
         * Define o número de quartos do mercado.
         * 
         * @param quartos Número de quartos do mercado.
         */
        public void setQuartos(int quartos) {
            this.quartos = quartos;
        }

        /**
         * Retorna o número de banheiros do mercado.
         * 
         * @return Número de banheiros do mercado.
         */
        public int getBan() {
            return ban;
        }

        /**
         * Define o número de banheiros do mercado.
         * 
         * @param ban Número de banheiros do mercado.
         */
        public void setBan(int ban) {
            this.ban = ban;
        }

        /**
         * Retorna o número de vagas do mercado.
         * 
         * @return Número de vagas do mercado.
         */
        public int getVaga() {
            return vaga;
        }

        /**
         * Define o número de vagas do mercado.
         * 
         * @param vaga Número de vagas do mercado.
         */
        public void setVaga(int vaga) {
            this.vaga = vaga;
        }

        /**
         * Retorna a URL do mercado.
         * 
         * @return URL do mercado.
         */
        public String getUrl() {
            return url;
        }

        /**
         * Define a URL do mercado.
         * 
         * @param url URL do mercado.
         */
        public void setUrl(String url) {
            this.url = url;
        }
    }
}