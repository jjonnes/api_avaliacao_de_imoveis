import pandas as pd
import conexao
import db
import input
 
#Limpeza de Dados
def limpeza_de_dados():
# Filtrar dados pela cidade
  cidade = input.input()[0]
  df = db.extrair_dados(conexao.conectar_banco())
  df = df[df['cidade'] == cidade]

  # Remover entradas com valores zero
  df = df[(df['area'] > 0) & (df['preco'] > 0) & (df['quartos'] > 0) & (df['banheiros'] > 0) & (df['bairro'] != 0)]

  # Remover URLs duplicados
  df = df.drop_duplicates(subset='url')

  # Função para remover outliers usando o método do IQR
  def remover_outliers(df, columns):
      df_out = df.copy()
      for column in columns:
          while True:
              Q1 = df_out[column].quantile(0.25)
              Q3 = df_out[column].quantile(0.75)
              IQR = Q3 - Q1
              lower_bound = Q1 - 1.5 * IQR
              upper_bound = Q3 + 1.5 * IQR
              df_out_before = df_out.copy()
              df_out = df_out[(df_out[column] >= lower_bound) & (df_out[column] <= upper_bound)]
              if df_out.shape == df_out_before.shape:
                  break
      return df_out

  # Remover outliers nas colunas especificadas
  df = remover_outliers(df, ['area', 'preco', 'quartos', 'banheiros', 'vagas'])
  
  #Transformar a variável 'bairro' em variáveis dummy
  df = pd.get_dummies(df, columns=['bairro'], drop_first=True)
  df.loc[:, df.dtypes == 'bool'] = df.loc[:, df.dtypes == 'bool'].astype(int)

  # Verificar e converter os tipos de dados
  df = df.apply(pd.to_numeric, errors='ignore')

  # Substituir valores ausentes por 0 ou remover linhas com valores ausentes
  df = df.dropna()

  return df
