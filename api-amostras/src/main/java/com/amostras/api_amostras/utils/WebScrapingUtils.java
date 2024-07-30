package com.amostras.api_amostras.utils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Classe de utilidades para web scraping.
 */
public class WebScrapingUtils {

  // Variáveis estáticas para o WebDriver e WebDriverWait
  private static WebDriver driver;
  private static WebDriverWait wait;

  /**
   * Abre o navegador Chrome em modo headless.
   */
  public static void abrirBrowser() {
    System.setProperty("webdriver.chrome.driver", "C:/Program Files/Google/chromedriver-win64/chromedriver.exe");

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--incognito");

    driver = new ChromeDriver(options);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  /**
   * Fecha o navegador Chrome.
   */
  public static void fecharBrowser() {
    driver.quit();
  }

  // Método para obter URLs das páginas de resultados
  public static void obterUrlsResultados(String bairro, int numeroPaginas) {
    String urlBase = "https://www.wimoveis.com.br/venda/imoveis/df/" + bairro + "/pagina-";
    String urlPagina = urlBase + numeroPaginas;
    driver.get(urlPagina);
  }

  /**
   * Retorna o número de resultados encontrados em uma página.
   * 
   * @param url a URL da página a ser analisada
   * @return o número de resultados encontrados
   * @throws Exception se ocorrer um erro durante a execução
   */
  public static int numeroResultados(String url) throws Exception {
    try {
      driver.get(url);

      WebElement numeroResultadosElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div.MainLayoutContainer-sc-ps0squ-0.guYSSN > div > div > div.Content-sc-185xmk8-1.guMLgg > div.ListPostingsContainer-sc-185xmk8-3.eUbYAT > div.ThisPostingsTopSection-sc-5z85om-0.euoGfj > div > div.TopLeftSection-sc-5z85om-4.bnElr > h1")));
      String numeroResultadosTexto = numeroResultadosElement.getText();
      return Utils.extrairNumero(numeroResultadosTexto);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Erro ao conectar ao site", e);
    }
  }

  // Métodos para extrair informações de uma página

  /**
   * Extrai o endereço de um imóvel.
   * 
   * @param i o índice do imóvel na página
   * @return o endereço do imóvel
   * @throws TimeoutException se o elemento não for encontrado dentro do tempo de espera
   */
  public static String extrairEndereco(int i) throws TimeoutException{
    String divPai = "#root > div.MainLayoutContainer-sc-ps0squ-0.guYSSN > div > div > div.Content-sc-185xmk8-1.guMLgg > div.ListPostingsContainer-sc-185xmk8-3.eUbYAT > div.postings-container > div:nth-child("+ i +")";
    try {
        String divChild = "div > div > div.PostingContainer-sc-i1odl-2.iQlPeD > div.PostingTop-sc-i1odl-3.hCsHEP > div:nth-child(1) > div.PostingCardRow-sc-i1odl-5.gJKAik > div > div";
        WebElement enderecoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(divPai +" > "+ divChild)));
        return enderecoElement.getText();
    } catch (TimeoutException e) {
        return "n/a";
    }
  }

  /**
   * Extrai o bairro de um imóvel.
   * 
   * @param i o índice do imóvel na página
   * @return o bairro do imóvel
   * @throws TimeoutException se o elemento não for encontrado dentro do tempo de espera
   */
  public static String extrairBairro(int i) throws TimeoutException {
    String divPai = "#root > div.MainLayoutContainer-sc-ps0squ-0.guYSSN > div > div > div.Content-sc-185xmk8-1.guMLgg > div.ListPostingsContainer-sc-185xmk8-3.eUbYAT > div.postings-container > div:nth-child(" + i + ")";
    try {
      String divChild = "div > div > div.PostingContainer-sc-i1odl-2.iQlPeD > div.PostingTop-sc-i1odl-3.hCsHEP > div:nth-child(1) > div.PostingCardRow-sc-i1odl-5.gJKAik > div > h2";
      WebElement bairroElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(divPai + " > " + divChild)));
      return Utils.extraiPrimeiroTexto(bairroElement.getText());
    } catch (TimeoutException e) {
      return "n/a";
    }
  }

  /**
   * Extrai a área de um imóvel.
   * 
   * @param i o índice do imóvel na página
   * @return a área do imóvel
   * @throws TimeoutException se o elemento não for encontrado dentro do tempo de espera
   */
  public static String extrairArea(int i) throws TimeoutException {
    String divPai = "#root > div.MainLayoutContainer-sc-ps0squ-0.guYSSN > div > div > div.Content-sc-185xmk8-1.guMLgg > div.ListPostingsContainer-sc-185xmk8-3.eUbYAT > div.postings-container > div:nth-child(" + i + ")";
    try {
      String divChild = "div > div > div.PostingContainer-sc-i1odl-2.iQlPeD > div.PostingTop-sc-i1odl-3.hCsHEP > div:nth-child(1) > div.PostingCardRow-sc-i1odl-5.fPulTZ > h3 > span:nth-child(1)";
      WebElement areaElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(divPai + " > " + divChild)));
      return areaElement.getText().replaceAll("[^0-9,\\. ]+", "");
    } catch (TimeoutException e) {
      return "0";
    }
  }

  /**
   * Extrai o preço de um imóvel.
   * 
   * @param i o índice do imóvel na página
   * @return o preço do imóvel
   * @throws TimeoutException se o elemento não for encontrado dentro do tempo de espera
   */
  public static String extrairPreco(int i) throws TimeoutException {
    String divPai = "#root > div.MainLayoutContainer-sc-ps0squ-0.guYSSN > div > div > div.Content-sc-185xmk8-1.guMLgg > div.ListPostingsContainer-sc-185xmk8-3.eUbYAT > div.postings-container > div:nth-child(" + i + ")";
    try {
      String divChild = "div > div > div.PostingContainer-sc-i1odl-2.iQlPeD > div.PostingTop-sc-i1odl-3.hCsHEP > div:nth-child(1) > div.PostingCardRow-sc-i1odl-5.kgqRDY > div > div > div.PriceContainer-sc-12dh9kl-2.ePWLec > div";
      WebElement precoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(divPai + " > " + divChild)));
      return precoElement.getText().replaceAll("[^0-9,\\. ]+", "");
    } catch (TimeoutException e) {
      return "0";
    }
  }

  /**
   * Extrai o número de quartos de um imóvel.
   * 
   * @param i o índice do imóvel na página
   * @return o número de quartos do imóvel
   */
  public static String extrairQuartos(int i) {
    String textoSpanDois = extrairSpan(i, "span:nth-child(2)");
    String textoSpanTres = extrairSpan(i, "span:nth-child(3)");
    String textoSpanQuatro = extrairSpan(i, "span:nth-child(4)");
    String spanDois = Utils.extraiTexto(textoSpanDois);
    String spanTres = Utils.extraiTexto(textoSpanTres);
    String spanQuatro = Utils.extraiTexto(textoSpanQuatro);
    List<String> lista = Arrays.asList("quarto", "quartos");
    return Utils.defineItem(lista, spanDois, spanTres, spanQuatro, textoSpanDois, textoSpanTres, textoSpanQuatro);
  }

  /**
   * Extrai o número de banheiros de um imóvel.
   * 
   * @param i o índice do imóvel na página
   * @return o número de banheiros do imóvel
   */
  public static String extrairBanheiros(int i) {
    String textoSpanDois = extrairSpan(i, "span:nth-child(2)");
    String textoSpanTres = extrairSpan(i, "span:nth-child(3)");
    String textoSpanQuatro = extrairSpan(i, "span:nth-child(4)");
    String spanDois = Utils.extraiTexto(textoSpanDois);
    String spanTres = Utils.extraiTexto(textoSpanTres);
    String spanQuatro = Utils.extraiTexto(textoSpanQuatro);
    List<String> lista = Arrays.asList("ban", "banheiro");
    return Utils.defineItem(lista, spanDois, spanTres, spanQuatro, textoSpanDois, textoSpanTres, textoSpanQuatro);
  }

  /**
   * Extrai o número de vagas de um imóvel.
   * 
   * @param i o índice do imóvel na página
   * @return o número de vagas do imóvel
   */
  public static String extrairVagas(int i) {
    String textoSpanDois = extrairSpan(i, "span:nth-child(2)");
    String textoSpanTres = extrairSpan(i, "span:nth-child(3)");
    String textoSpanQuatro = extrairSpan(i, "span:nth-child(4)");
    String spanDois = Utils.extraiTexto(textoSpanDois);
    String spanTres = Utils.extraiTexto(textoSpanTres);
    String spanQuatro = Utils.extraiTexto(textoSpanQuatro);
    List<String> lista = Arrays.asList("vaga", "vagas");
    return Utils.defineItem(lista, spanDois, spanTres, spanQuatro, textoSpanDois, textoSpanTres, textoSpanQuatro);
  }

  /**
   * Extrai a URL da amostra de um imóvel.
   * 
   * @param i o índice do imóvel na página
   * @return a URL da amostra do imóvel
   * @throws TimeoutException se o elemento não for encontrado dentro do tempo de espera
   */
  public static String extrairUrlAmostra(int i) throws TimeoutException {
    String divPai = "#root > div.MainLayoutContainer-sc-ps0squ-0.guYSSN > div > div > div.Content-sc-185xmk8-1.guMLgg > div.ListPostingsContainer-sc-185xmk8-3.eUbYAT > div.postings-container > div:nth-child(" + i + ")";
    try {
      WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(divPai)));
      WebElement divInsideElement = elemento.findElement(By.cssSelector("div:first-child"));
      String amostra = divInsideElement.getAttribute("data-to-posting");
      return "https://www.wimoveis.com.br" + amostra;
    } catch (TimeoutException e) {
      return "0";
    }
  }

  /**
   * Extrai o texto de um span.
   * 
   * @param i o índice do imóvel na página
   * @param childSelector o seletor do span
   * @return o texto do span
   * @throws NoSuchElementException se o elemento não for encontrado
   */
  private static String extrairSpan(int i, String childSelector) throws NoSuchElementException {
    String divPai = "#root > div.MainLayoutContainer-sc-ps0squ-0.guYSSN > div > div > div.Content-sc-185xmk8-1.guMLgg > div.ListPostingsContainer-sc-185xmk8-3.eUbYAT > div.postings-container > div:nth-child(" + i + ")";
    try {
      String divChild = "div > div > div.PostingContainer-sc-i1odl-2.iQlPeD > div.PostingTop-sc-i1odl-3.hCsHEP > div:nth-child(1) > div.PostingCardRow-sc-i1odl-5.fPulTZ > h3";
      WebElement spanElement = driver.findElement(By.cssSelector(divPai + " > " + divChild + " > " + childSelector));
      return spanElement.getText();
    } catch (NoSuchElementException e) {
      return "0";
    }
  }
}
