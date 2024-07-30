package com.amostras.api_amostras.utils;

import java.util.List;

/**
 * Classe de utilidades para manipulação de strings e extração de informações.
 */
public class Utils {

  /**
   * Extrai um valor double de uma string, removendo todos os caracteres não numéricos.
   * 
   * @param texto a string a ser processada
   * @return o valor double extraído
   * @throws NumberFormatException se não for possível converter a string para double
   */
  public static double extrairDouble(String texto) throws NumberFormatException {
    texto = texto.replaceAll("[^\\d]+", "");

    try {
      return Double.parseDouble(texto);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Não foi possível converter a string para double: " + texto);
    }
  }

  /**
   * Extrai apenas os caracteres alfabéticos de uma string.
   * 
   * @param texto a string a ser processada
   * @return a string com apenas caracteres alfabéticos
   */
  public static String extraiTexto(String texto) {
    texto = texto.replaceAll("[^a-zA-Z]", "");
    return texto;
  }

  /**
   * Extrai o primeiro texto antes da primeira vírgula de uma string.
   * 
   * @param texto a string a ser processada
   * @return o texto antes da primeira vírgula
   */
  public static String extraiPrimeiroTexto(String texto) {
    int indiceVirgula = texto.indexOf(",");
    if (indiceVirgula != -1) {
      return texto.substring(0, indiceVirgula);
    } else {
      return texto;
    }
  }

  /**
   * Extrai apenas os números de uma string.
   * 
   * @param texto a string a ser processada
   * @return o número extraído
   */
  public static int extrairNumero(String texto) {
    texto = texto.replaceAll("[^0-9]", "");
    return Integer.parseInt(texto);
  }

  /**
   * Define um item com base na presença de certos textos em uma lista.
   * 
   * @param arg1 a lista de textos a ser verificada
   * @param arg2 o texto a ser procurado primeiro
   * @param arg3 o texto a ser procurado em segundo lugar
   * @param arg4 o texto a ser procurado em terceiro lugar
   * @param arg5 o valor a ser retornado se o primeiro texto for encontrado
   * @param arg6 o valor a ser retornado se o segundo texto for encontrado
   * @param arg7 o valor a ser retornado se o terceiro texto for encontrado
   * @return o valor correspondente ao texto encontrado ou "0" se nenhum for encontrado
   */
  public static String defineItem(List<String> arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
    if (arg1.contains(arg2)) {
      // Retorna o valor correspondente ao primeiro texto se ele for encontrado
      return arg5;
    } else if (arg1.contains(arg3)) {
      // Retorna o valor correspondente ao segundo texto se ele for encontrado
      return arg6;
    } else if (arg1.contains(arg4)) {
      // Retorna o valor correspondente ao terceiro texto se ele for encontrado
      return arg7;
    } else {
      // Retorna "0" se nenhum texto for encontrado
      return "0";
    }
  }
}