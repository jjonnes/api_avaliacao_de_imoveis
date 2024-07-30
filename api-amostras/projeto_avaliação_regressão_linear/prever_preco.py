import pandas as pd
import input
import modelo
import limpeza_db

# Função para Prever Preço do Imóvel
def valor_provavel():  
  cidade, bairro, area, quartos, banheiros, vagas = input.input()
  model, X, Y = modelo.modelo()
  df = limpeza_db.limpeza_de_dados()

  def prever_preco(area, quartos, banheiros, vagas, bairro, df, model):
      # Criação do DataFrame com os valores fornecidos
      new_data = pd.DataFrame({
          'const': [1],
          'area': [area],
          'quartos': [quartos],
          'banheiros': [banheiros],
          'vagas': [vagas]
      })

      # Adicionar variáveis dummy do bairro
      for col in df.columns:
          if col.startswith('bairro_'):
              new_data[col] = 0
      if f'bairro_{bairro}' in new_data.columns:
          new_data[f'bairro_{bairro}'] = 1

      # Adicionar colunas faltantes no new_data para corresponder ao X do modelo
      for col in X.columns:
          if col not in new_data.columns:
              new_data[col] = 0

      # Ordenar as colunas para garantir que new_data corresponda ao X do modelo
      new_data = new_data[X.columns]

      # Previsão do preço usando o modelo ajustado
      preco_previsto = model.predict(new_data)[0]
      return preco_previsto

  preco_estimado = prever_preco(area, quartos, banheiros, vagas, bairro, df, model)
  print(f'O valor estimado da casa é: R${preco_estimado:.2f} mil reais')