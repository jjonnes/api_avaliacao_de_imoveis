from statsmodels.stats.outliers_influence import variance_inflation_factor
import pandas as pd
import limpeza_db
import statsmodels.api as sm

# Passo 4: Ajuste do Modelo de Regressão Linear Múltipla
def modelo():
# Variáveis independentes (inclui área, que deve ser mantida)
  df = limpeza_db.limpeza_de_dados()
  X = df[['area', 'quartos', 'banheiros', 'vagas'] + [col for col in df.columns if col.startswith('bairro_')]]

  # Adicionar a constante (intercepto)
  X = sm.add_constant(X, has_constant='add')

  # Variável dependente
  Y = df['preco']

  # Função para calcular VIF e remover multicolinearidade
  def calcular_vif(X):
      vif = pd.DataFrame()
      vif['Feature'] = X.columns
      vif['VIF'] = [variance_inflation_factor(X.values, i) for i in range(X.shape[1])]
      return vif

  # Função para remover variáveis com alta colinearidade
  def remover_multicolinearidade(X, threshold=5):
      features = X.columns.tolist()
      while True:
          vif = calcular_vif(X[features])
          max_vif = vif['VIF'].max()
          max_vif_feature = vif.loc[vif['VIF'].idxmax(), 'Feature']
          if max_vif > threshold:
              if max_vif_feature != 'area':
                  print(f'Removendo variável com VIF mais alto: {max_vif_feature} ({max_vif})')
                  features.remove(max_vif_feature)
              else:
                  print(f'Não é possível remover a variável (area) devido ao alto VIF.')
                  break
          else:
              break
      return X[features]

  # Verificação e Remoção de Colinearidade
  def verificar_remover_colinearidade(X, var_a_verificar, outras_vars):
      if var_a_verificar in X.columns:
          X_temp = X[[var_a_verificar] + [v for v in outras_vars if v in X.columns]]
          vif = calcular_vif(X_temp)
          if vif[vif['Feature'] != 'const']['VIF'].max() > 5:
              max_vif_feature = vif.loc[vif['VIF'].idxmax(), 'Feature']
              if max_vif_feature != var_a_verificar:
                  print(f'Removendo variável com alta colinearidade com {var_a_verificar}: {max_vif_feature}')
                  X = X.drop(columns=[max_vif_feature])
      return X

  # Colinearidade da variável 'área'
  outras_vars_area = ['quartos', 'banheiros', 'vagas'] + [col for col in X.columns if col.startswith('bairro_')]
  X = verificar_remover_colinearidade(X, 'area', outras_vars_area)

  X = remover_multicolinearidade(X)

  # Ajuste do modelo de regressão linear múltipla
  model = sm.OLS(Y, X).fit()

  return model, X, Y