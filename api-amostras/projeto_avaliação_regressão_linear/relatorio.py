from matplotlib import pyplot as plt
import pandas as pd
import limpeza_db
import modelo
from statsmodels.tools.eval_measures import rmse
import seaborn as sns
import input
from statsmodels.stats.outliers_influence import variance_inflation_factor


def relatorios():
  model, X, Y = modelo.modelo()
  cidade = input.input()[0]
  df = limpeza_db.limpeza_de_dados()

  previsoes = model.predict(X)
  rmse_value = rmse(Y, previsoes)
  print(f'Erro Médio Quadrático (RMSE): {rmse_value:.2f}')

  # Resíduos
  residuos = Y - previsoes

  # Plotagem dos resíduos
  plt.figure(figsize=(12, 6))

  # Gráfico de dispersão resíduos vs previsões
  plt.subplot(1, 2, 1)
  plt.scatter(previsoes, residuos)
  plt.axhline(y=0, color='r', linestyle='--')
  plt.xlabel('Previsões')
  plt.ylabel('Resíduos')
  plt.title('Resíduos vs Previsões')

  # Histograma dos resíduos
  plt.subplot(1, 2, 2)
  sns.histplot(residuos, kde=True)
  plt.xlabel('Resíduos')
  plt.title('Distribuição dos Resíduos')
  plt.show()

  # Relatório Detalhado
  def gerar_relatorio(model, X, Y, cidade):
      r_squared = model.rsquared
      adj_r_squared = model.rsquared_adj
      rmse_value = rmse(Y, model.predict(X))

      print("\n--- Relatório Detalhado ---")
      print(f"Fundamentação:")
      print(f"Método Utilizado: Regressão linear múltipla")
      print(f"Variáveis Consideradas: Área, número de quartos, banheiros, vagas de garagem e bairro")
      print(f"Dados Coletados: Amostra de {len(df)} imóveis em {cidade}")

      print("\nPrecisão:")
      print(f"R²: {r_squared:.4f}")
      print(f"R² Ajustado: {adj_r_squared:.4f}")
      print(f"RMSE: {rmse_value:.2f}")

      # Análise de Resíduos
      residuos = Y - model.predict(X)
      print("\nAnálise de Resíduos:")
      print(f"Média dos Resíduos: {residuos.mean():.4f}")
      print(f"Desvio Padrão dos Resíduos: {residuos.std():.4f}")

      return r_squared, adj_r_squared, rmse_value

  # Gerar Relatório
  gerar_relatorio(model, X, Y, cidade)

  # Avaliação de Fundamentação e Precisão
  def avaliar_modelo(model):
      r_squared = model.rsquared
      adj_r_squared = model.rsquared_adj
      rmse_value = rmse(Y, model.predict(X))
      
      print(f"\n--- Avaliação do Modelo ---")
      print(f"R²: {r_squared:.4f}")
      print(f"R² Ajustado: {adj_r_squared:.4f}")
      print(f"RMSE: {rmse_value:.2f}")

  avaliar_modelo(model)

  # Verificação da Matriz de Correlação
  correlation_matrix = X.corr()
  print("\nMatriz de Correlação:")
  print(correlation_matrix)

  # Verificação do VIF detalhado
  vif = pd.DataFrame()
  vif['Feature'] = X.columns
  vif['VIF'] = [variance_inflation_factor(X.values, i) for i in range(X.shape[1])]
  print("\nÍndice de Inflação da Variância (VIF):")
  print(vif)

  # Geração do Relatório
  def gera_relatorio(model, X, Y, cidade):
      r_squared = model.rsquared
      adj_r_squared = model.rsquared_adj
      rmse_value = rmse(Y, model.predict(X))

      print("--- Relatório Detalhado ---\n")
      print(f"Fundamentação:\n")
      print(f"Método Utilizado: Regressão linear múltipla\n")
      print(f"Variáveis Consideradas: Área, número de quartos, banheiros, vagas de garagem e bairro\n")
      print(f"Dados Coletados: Amostra de {len(df)} imóveis em {cidade}\n")

      print(f"\nPrecisão:\n")
      print(f"R²: {r_squared:.4f}\n")
      print(f"R² Ajustado: {adj_r_squared:.4f}\n")
      print(f"RMSE: {rmse_value:.2f}\n")

      residuos = Y - model.predict(X)
      print(f"\nAnálise de Resíduos:\n")
      print(f"Média dos Resíduos: {residuos.mean():.4f}\n")
      print(f"Desvio Padrão dos Resíduos: {residuos.std():.4f}\n")

      print(f"\nÍndice de Inflação da Variância (VIF):\n")
      for index, row in vif.iterrows():
          print(f"{row['Feature']}: {row['VIF']:.2f}\n")
      

  # Salvar o Relatório em Arquivo
  gera_relatorio(model, X, Y, cidade)
